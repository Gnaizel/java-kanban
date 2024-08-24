package service;

import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

import java.time.LocalDateTime;
import java.util.*;

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
        TreeSet<Task> prioritizedTasks = new TreeSet<>(Comparator.comparing(Task::getStartTime)
                .thenComparing(Task::getID));
        prioritizedTasks.addAll(tasksMap.values().stream()
                .filter(task -> task.getStartTime() != null)
                .toList());
        prioritizedTasks.addAll(epicMap.values().stream()
                .filter(epic -> epic.getStartTime() != null)
                .toList());
        prioritizedTasks.addAll(subTaskMap.values().stream()
                .filter(subtask -> subtask.getStartTime() != null)
                .toList());

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

    @Override
    public List<Epic> getAllEpic() {
        if (epicMap.isEmpty()) {
            return null;
        }
        return new ArrayList<>(epicMap.values());
    }

    @Override
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

    public List<Epic> getEpics() {
        return new ArrayList<>(epicMap.values());
    }

    public List<Subtask> getSubtasks() {
        return new ArrayList<>(subTaskMap.values());
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
    public void updateTask(int id, Task updatedTask) {
        tasksMap.replace(id, updatedTask);
    }

    @Override
    public void updateEpic(int id, Epic updatedEpic) {
        epicMap.replace(id, updatedEpic);
    }

    @Override
    public void updateSubtask(int id, Subtask updatedSubtask) {
        subTaskMap.replace(id, updatedSubtask);
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

    private boolean timeOverlap(LocalDateTime start1, LocalDateTime end1, LocalDateTime start2, LocalDateTime end2) {
        return (start1.isBefore(end2) || start1.isEqual(end2)) && (end1.isAfter(start2) || end1.isEqual(start2));
    } // Накладывается ли задача

    @Override
    public boolean isValidTimeTask(Task task) {
        if (task instanceof Epic epic) {
            return epicMap.values().stream()
                    .filter(otherTask -> otherTask != task)
                    .noneMatch(otherTask -> true);
        } else if (task instanceof Subtask subtask) {
            return subTaskMap.values().stream()
                    .filter(otherTask -> otherTask != task)
                    .noneMatch(otherTask -> timeOverlap(task.getStartTime(), task.getEndTime(),
                            otherTask.getStartTime(), otherTask.getEndTime()));
        } else if (task != null) {
            return tasksMap.values().stream()
                    .filter(otherTask -> otherTask != task)
                    .noneMatch(otherTask -> timeOverlap(task.getStartTime(), task.getEndTime(),
                            otherTask.getStartTime(), otherTask.getEndTime()));
        }
        return false;
    }

}
