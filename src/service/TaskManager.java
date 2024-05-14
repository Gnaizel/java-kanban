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
    private final HashMap<Integer, Subtask> subtaskMap = new HashMap<>();

    public void createTask(Task task) {
        this.tasksMap.put(task.getID(), task);
    }

    public void createEpic(Epic epic) {
        this.epicMap.put(epic.getID(), epic);
    }

    public void createSubtask(Subtask subtask, Epic epic) {
        this.subtaskMap.put(subtask.getID(), subtask);
    }

    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasksMap.values());
    }

    public ArrayList<Epic> getAllEpic() {
        return new ArrayList<>(epicMap.values());
    }

    public ArrayList<Subtask> getAllSubtask() {
        ArrayList<Subtask> subtasks = new ArrayList<>();
        for (Epic epic : epicMap.values()) {
            subtasks.addAll(epic.getSubtasks());
        }
        return subtasks;
    }

    public void deleteAllTasks() {
        tasksMap.clear();
    }

    public void deleteAllEpics() {
        epicMap.clear();
    }

    public void deleteAllSubtasks() {
        for (Epic epic : epicMap.values()) {
            epic.clearSubTasks();
        }
    }

    public void deleteTask(Task task) {
        tasksMap.remove(task.getID());
    }

    public void deleteEpic(Epic epic) {
        epicMap.remove(epic.getID());
    }

    public void deleteSubtask(Subtask subtask, Epic epic) {
        epic.deleteSubTask(subtask);
    }

    public Task getTaskById(int taskId) {
        return tasksMap.get(taskId);
    }

    public Epic getEpicById(int taskId) {
        return epicMap.get(taskId);
    }

    public Task getTaskByName(String name) {
        for (Task task : tasksMap.values()) {
            if (task.getTaskName().equals(name)) {
                return task;
            }
        }
        return null;
    }

    public Epic getEpicByName(String name) {
        for (Epic epic : epicMap.values()) {
            if (epic.getTaskName().equals(name)) {
                return epic;
            }
        }
        return null;
    }

    public void updateTask(Task updatedTask) {
        tasksMap.replace(updatedTask.getID(), updatedTask);
    }

    public void updateEpic(Epic updatedEpic) {
        epicMap.replace(updatedEpic.getID(), updatedEpic);
    }

    public ArrayList<Subtask> getAllSubtasksForEpic(String epicName) {
        for (Epic epic : epicMap.values()) {
            if (epic.getTaskName().equals(epicName)) {
                return epic.getSubtasks();
            }
        }
        return null;
    }

    public void epicStatus(Epic epic) {
        if (epic.subtasksNull()) epic.setStatus(Status.NEW);
        boolean allSubtasksDone = true;
        for (Task subtask : epic.getSubtasks()) {
            if (subtask.getStatus() != Status.DONE) {
                allSubtasksDone = false;
                break;
            }
        }
        if (allSubtasksDone) {
            epic.setStatus(Status.DONE);
        } else {
            epic.setStatus(Status.IN_PROGRESS);
        }
    }

    public void setSubTaskStatus(Subtask subtask, Status status) {
        subtask.setStatus(status);
    }
}
