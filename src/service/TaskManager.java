package service;

import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {

    public void createTask(Task task);

    public void createEpic(Epic epic);

    public void createSubtask(Subtask subtask);

    public ArrayList<Task> getAllTasks();

    public ArrayList<Epic> getAllEpic();

    public ArrayList<Subtask> getAllSubtask();

    public void deleteAllTasks();

    public void deleteAllEpics();

    public void deleteAllSubtasks();

    public void deleteTask(Task task);

    public void deleteEpic(Epic epic);

    public void deleteSubtask(Subtask subtask);

    public Task getTaskById(int taskId);

    public Epic getEpicById(int taskId);

    public Subtask getSubtaskById(int taskId);

    public void updateTask(Task updatedTask);

    public void updateEpic(Epic updatedEpic);

    public void updateSubtask(Subtask updatedSubtask);

    public ArrayList<Subtask> getAllSubtasksForEpic(int id);

    public void setSubTaskStatus(Subtask subtask, Status status);

    public void updateEpicStatus(Epic epic);

    public List<Task> getHistory();

    public void removeThis();
}
