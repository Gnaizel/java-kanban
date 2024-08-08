package service;

import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class InMemoryTaskManager implements TaskManager {
    protected final HashMap<Integer, Task> tasksMap = new HashMap<>();
    protected final HashMap<Integer, Epic> epicMap = new HashMap<>();
    protected final HashMap<Integer, Subtask> subTaskMap = new HashMap<>();
    protected final HistoryManager historyTask = new InMemoryHistoryManager();

    public InMemoryTaskManager() {
        clearAll();
    }

    @Override
    public void clearAll() {
        deleteAllTasks();
        deleteAllEpics();
        deleteAllSubtasks();
    }

    @Override
    public TreeSet<Task> getPrioritizedTasks() {
        TreeSet<Task> prioritizedTasks = new TreeSet<>();
        prioritizedTasks.addAll(tasksMap.values());
        prioritizedTasks.addAll(epicMap.values());
        prioritizedTasks.addAll(subTaskMap.values());
        prioritizedTasks = prioritizedTasks.stream()
                .filter(task -> task.getStartTime() != null)
                .collect(Collectors.toCollection(TreeSet::new));
        for (Task task : prioritizedTasks) System.out.println(task.toString());
        return prioritizedTasks;
    }

    @Override
    public void createTask(Task task) {
        if (isValidTimeTask(task)) {
            tasksMap.put(task.getID(), task);
        } else System.out.println("Invalid taskStartTime");
    }

    @Override
    public void createEpic(Epic epic) {
        if (isValidTimeTask(epic)) {
            epicMap.put(epic.getID(), epic);
        } else System.out.println("Invalid epicStartTime");
    }

    @Override
    public void createSubtask(Subtask subtask) throws NullPointerException {
        try {
            if (isValidTimeTask(subtask)) {
                updateEpicStatus(getEpicById(subtask.getEpicId()));
                subTaskMap.put(subtask.getID(), subtask);
            } else System.out.println("Invalid subtaskStartTime");
        } catch (NullPointerException ignored) {
        }
    }

    @Override
    public List<Task> getAllTasks() {
        if (tasksMap.isEmpty()) {
            return null;
        }
        return new ArrayList<>(tasksMap.values());
    }

    public List<Epic> getAllEpic() {
        if (epicMap.isEmpty()) {
            return null;
        }
        return new ArrayList<>(epicMap.values());
    }

    public List<Subtask> getAllSubtask() {
        if (subTaskMap.isEmpty()) {
            return null;
        }
        return new ArrayList<>(subTaskMap.values());
    }

    public void deleteAllTasks() {
        tasksMap.clear();
        ID.TaskId = 0;
    }

    @Override
    public void deleteAllEpics() {
        epicMap.clear();
        subTaskMap.clear();
        ID.EpicId = 0;
        ID.SubTaskId = 0;
    }

    @Override
    public void deleteAllSubtasks() {
        for (Epic epic : epicMap.values()) {
            epic.setStatus(Status.NEW);
        }
        subTaskMap.clear();
        ID.SubTaskId = 0;
    }

    @Override
    public void deleteTask(Task task) {
        tasksMap.remove(task.getID());
    }

    @Override
    public void deleteEpic(Epic epic) {
        epic.getSubTasks().clear();
        epicMap.remove(epic.getID());
    }

    @Override
    public void deleteSubtask(Subtask subtask) {
        updateEpicStatus(getEpicById(subtask.getEpicId()));
        getEpicById(subtask.getEpicId()).removeSubTask(subtask);
        subTaskMap.remove(subtask.getID());
    }

    @Override
    public Task getTaskById(int id) {
        if (tasksMap.containsKey(id)) {
            historyTask.add(tasksMap.get(id));
            return tasksMap.get(id);
        }
        return null;
    }

    @Override
    public Subtask getSubtaskById(int id) {
        if (subTaskMap.containsKey(id)) {
            historyTask.add(subTaskMap.get(id));
            return subTaskMap.get(id);
        }
        return null;
    }

    @Override
    public Epic getEpicById(int id) {
        if (epicMap.containsKey(id)) {
            historyTask.add(epicMap.get(id));
            return epicMap.get(id);
        }
        return null;
    }

    @Override
    public void updateTask(Task updatedTask) {
        tasksMap.replace(updatedTask.getID(), updatedTask);
    }

    @Override
    public void updateEpic(Epic updatedEpic) {
        epicMap.replace(updatedEpic.getID(), updatedEpic);
    }

    @Override
    public void updateSubtask(Subtask updatedSubtask) {
        subTaskMap.replace(updatedSubtask.getID(), updatedSubtask);
        updateEpicStatus(getEpicById(updatedSubtask.getEpicId()));
    }

    public List<Subtask> getAllSubtasksForEpic(int id) {
        for (Epic epic : epicMap.values()) {
            if (epic.getID() == id) {
                return new ArrayList<>(subTaskMap.values());
            }
        }
        return null;
    }

    @Override
    public void setSubTaskStatus(Subtask subtask, Status status) {
        subtask.setStatus(status);
    }

    @Override
    public void updateEpicStatus(Epic epic) throws NullPointerException {
        try {
            epic.updateTime();
            if (epic.hasSubtasksIsEmpty()) {
                epic.setStatus(Status.NEW);
            } else if (epic.allSubtasksDone()) {
                epic.setStatus(Status.DONE);
            } else {
                epic.setStatus(Status.IN_PROGRESS);
            }
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Task> getHistory() {
        return historyTask.getHistory();
    }

    @Override
    public boolean isValidTimeTask(Task task) {
        Set<LocalDateTime> tasksStarted = new HashSet<>();

        tasksStarted.addAll(tasksMap.values().stream()
                .map(task1 -> task1.getStartTime().plusMinutes(task1.getDuration()))
                .collect(Collectors.toSet()));

        tasksStarted.addAll(epicMap.values().stream()
                .map(task1 -> task1.getStartTime().plusMinutes(task1.getDuration()))
                .collect(Collectors.toSet()));

        tasksStarted.addAll(subTaskMap.values().stream()
                .map(task1 -> task1.getStartTime().plusMinutes(task1.getDuration()))
                .collect(Collectors.toSet()));

        return !tasksStarted.contains(task.getStartTime());
    }

}
