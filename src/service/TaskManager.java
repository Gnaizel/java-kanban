package service;

import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

import java.util.List;
import java.util.TreeSet;

public interface TaskManager {

    void clearAll();

    void createTask(Task task);

    void createEpic(Epic epic);

    void createSubtask(Subtask subtask);

    List<Task> getAllTasks();

    List<Epic> getAllEpic();

    List<Subtask> getAllSubtask();

    void deleteAllTasks();

    void deleteAllEpics();

    void deleteAllSubtasks();

    void deleteTask(Task task);

    void deleteEpic(Epic epic);

    void deleteSubtask(Subtask subtask);

    Task getTaskById(int taskId);

    Epic getEpicById(int taskId);

    Subtask getSubtaskById(int taskId);

    void updateTask(int id, Task updatedTask);

    void updateEpic(int id, Epic updatedEpic);

    void updateSubtask(int id, Subtask updatedSubtask);

    void setSubTaskStatus(Subtask subtask, Status status);

    void updateEpicStatus(Epic epic);

    List<Task> getHistory();

    TreeSet<Task> getPrioritizedTasks();

    boolean isValidTimeTask(Task task);
}
