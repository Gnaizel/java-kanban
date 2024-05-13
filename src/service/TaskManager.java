package service;

import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

import java.util.HashMap;
import java.util.ArrayList;
//Слимшком много методов Которыя я думаю даже хз зачем нужны если нада их удалить
// или можно както расортировать напишите в коментарии пожалйста
public class TaskManager {
    private final HashMap<Integer, Task> tasksMap = new HashMap<>();
    private final HashMap<Integer, Epic> epicMap = new HashMap<>();

    public void createTask(Task task) {
        tasksMap.put(task.getTaskId(), task);
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

    public Task getTaskById(int taskId) {
        return tasksMap.get(taskId);
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
        tasksMap.replace(updatedTask.getTaskId(), updatedTask);
    }

    public void deleteTaskById(int taskId) {
        tasksMap.remove(taskId);
    }


    public void createEpic(Epic epic) {
        epicMap.put(epic.getTaskId(), epic);
    }

    public void deleteEpicById(int epicId) {
        epicMap.remove(epicId);
    }

    public Epic getEpicById(int epicId) {
        return epicMap.get(epicId);
    }

    public void updateEpic(Epic updatedEpic) {
        epicMap.replace(updatedEpic.getTaskId(), updatedEpic);
    }

    public ArrayList<Subtask> getAllSubtasksForEpic(String epicName) {
        for (Epic epic : epicMap.values()) {
            if (epic.getEpicName().equals(epicName)) {
                return epic.getSubtasks();
            } else {
                return null;
            }
        }
        return new ArrayList<>();
    }

    public void EpicStatus(Epic epic) {
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

    public void SetSubtaskStatus(Subtask subtask, Status status) {
        subtask.setStatus(status);
    }
}
