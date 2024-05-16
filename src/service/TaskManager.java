package service;

import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

import java.util.HashMap;
import java.util.ArrayList;

public class TaskManager {
    private final HashMap<Integer, Task> tasksMap = new HashMap<>();
    private final HashMap<Integer, Epic> epicMap = new HashMap<>();
    private final HashMap<Integer, Subtask> subTaskMap = new HashMap<>();

    public void createTask(Task task) {
        this.tasksMap.put(task.getID(), task);
        ID.TaskId++;
    }

    public void createEpic(Epic epic) {
        this.epicMap.put(epic.getID(), epic);
        ID.EpicId++;
    }

    public void createSubtask(Subtask subtask) {
        subtask.getEpic().addSubTask(subtask);
        subtask.getEpic().setStatus(Status.IN_PROGRESS);//Попытка оптимезировать (меньшн денйствий по идее) видемо не оч получается :(
        this.subTaskMap.put(subtask.getID(), subtask);
        ID.SubTaskId++;
    }

    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasksMap.values());
    }

    public ArrayList<Epic> getAllEpic() {
        return new ArrayList<>(epicMap.values());
    }

    public ArrayList<Subtask> getAllSubtask() {
        return new ArrayList<>(subTaskMap.values());
    }

    public void deleteAllTasks() {
        tasksMap.clear();
        ID.TaskId = 0;
    }

    public void deleteAllEpics() {
        epicMap.clear();
        subTaskMap.clear();
        ID.EpicId = 0;
        ID.SubTaskId = 0;
    }

    public void deleteAllSubtasks() {
        for (Epic epic : epicMap.values()) {
            epic.setStatus(Status.NEW);
        }
        subTaskMap.clear();
        ID.SubTaskId = 0;
    }

    public void deleteTask(Task task) {
        tasksMap.remove(task.getID());
    }

    public void deleteEpic(Epic epic) {
        epic.getSubtasks().clear();
        epicMap.remove(epic.getID());
    }

    public void deleteSubtask(Subtask subtask) {
        updateEpicStatus(subtask.getEpic());
        subTaskMap.remove(subtask.getID());
    }

    public Task getTaskById(int taskId) {
        return tasksMap.get(taskId);
    }

    public Epic getEpicById(int taskId) {
        return epicMap.get(taskId);
    }

    public Subtask getSubtaskById(int taskId) {
        return subTaskMap.get(taskId);
    }

    public void updateTask(Task updatedTask) {
        tasksMap.replace(updatedTask.getID(), updatedTask);
    }

    public void updateEpic(Epic updatedEpic) {
        epicMap.replace(updatedEpic.getID(), updatedEpic);
    }

    public void updateSubtask(Subtask updatedSubtask) {
        subTaskMap.replace(updatedSubtask.getID(), updatedSubtask);
        updateEpicStatus(updatedSubtask.getEpic());
    }

    public ArrayList<Subtask> getAllSubtasksForEpic(int id) {
        for (Epic epic : epicMap.values()) {
            if (epic.getID() == id) {
                return new ArrayList<>(subTaskMap.values());
            }
        }
        return null;
    }

    public void setSubTaskStatus(Subtask subtask, Status status) {
        subtask.setStatus(status);
    }

    public void updateEpicStatus(Epic epic) {
        if (epic.subtasksNull()) {
            epic.setStatus(Status.NEW);
        } else if (epic.allSubtasksDone()) {
            epic.setStatus(Status.DONE);
        } else {
            epic.setStatus(Status.IN_PROGRESS);
        }
    }
}
