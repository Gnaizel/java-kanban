package service;

import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {
    private final HashMap<Integer, Task> tasksMap = new HashMap<>();
    private final HashMap<Integer, Epic> epicMap = new HashMap<>();
    private final HashMap<Integer, Subtask> subTaskMap = new HashMap<>();
    private final HistoryManager historyTask = new InMemoryHistoryManager();

    @Override
    public void createTask(Task task) {
        this.tasksMap.put(task.getID(), task);
        ID.TaskId++;
    }

    @Override
    public void createEpic(Epic epic) {
        this.epicMap.put(epic.getID(), epic);
        ID.EpicId++;
    }

    @Override
    public void createSubtask(Subtask subtask) {
        subtask.getEpic().addSubTask(subtask);
        updateEpicStatus(subtask.getEpic());
        this.subTaskMap.put(subtask.getID(), subtask);
        ID.SubTaskId++;
    }

    @Override
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasksMap.values());
    }

    @Override
    public ArrayList<Epic> getAllEpic() {
        return new ArrayList<>(epicMap.values());
    }

    @Override
    public ArrayList<Subtask> getAllSubtask() {
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
        epic.getSubtasks().clear();
        epicMap.remove(epic.getID());
    }

    @Override
    public void deleteSubtask(Subtask subtask) {
        updateEpicStatus(subtask.getEpic());
        subTaskMap.remove(subtask.getID());
    }

    @Override
    public Task getTaskById(int taskId) {
        historyTask.add(subTaskMap.get(taskId));
        return tasksMap.get(taskId);
    }

    @Override
    public Epic getEpicById(int taskId) {
        historyTask.add(subTaskMap.get(taskId));
        return epicMap.get(taskId);
    }

    @Override
    public Subtask getSubtaskById(int taskId) {
        historyTask.add(subTaskMap.get(taskId));
        return subTaskMap.get(taskId);
    }

    @Override
    public ArrayList<Subtask> getSubtasksById(int taskId) {
        for (Subtask subtask : subTaskMap.values()) { subtask.getEpic().getSubtasks().add(subtask); }
        return new ArrayList<Subtask>();
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
        updateEpicStatus(updatedSubtask.getEpic());
    }

    @Override
    public ArrayList<Subtask> getAllSubtasksForEpic(int id) {
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
    public void updateEpicStatus(Epic epic) {
        if (epic.subtasksNull()) {
            epic.setStatus(Status.NEW);
        } else if (epic.allSubtasksDone()) {
            epic.setStatus(Status.DONE);
        } else {
            epic.setStatus(Status.IN_PROGRESS);
        }
    }

    @Override
    public List<Task> getHistory() {
        return (List<Task>) historyTask.getHistory();
    }
}
