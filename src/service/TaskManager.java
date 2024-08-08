package service;

import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

import java.util.List;

public interface TaskManager {

    void clear();

    void add(Task task);

    void add(Subtask subtask);

    void add(Epic epic);

    List<Task> getAllTasks();

    List<Epic> getAllEpics();

    List<Subtask> getAllSubtasks();

    List<Subtask> getSubtasksForEpic(int epicId);

    void deleteTask(int id);

    void deleteEpic(int id);

    void deleteSubtask(int id);

    Task getTask(int id);

    Epic getEpic(int id);

    Subtask getSubtask(int id);

    void update(Task task);

    void update(Epic epic);

    void update(Subtask subtask);

    List<Subtask> getAllSubtasksForEpic(int id);

    void setSubTaskStatus(Subtask subtask, Status status);

    void updateEpicStatus(Epic epic);

    List<Task> getHistory();

    List<Task> getPrioritizedTasks();
}
