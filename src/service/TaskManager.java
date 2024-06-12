package service;

import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

import java.util.ArrayList;
import java.util.List;

 public interface TaskManager {

    void createTask(Task task);

    void createEpic(Epic epic);

     void createSubtask(Subtask subtask);

     List<Task> getAllTasks();

     ArrayList<Epic> getAllEpic();

     ArrayList<Subtask> getAllSubtask();

     void deleteAllTasks();

     void deleteAllEpics();

     void deleteAllSubtasks();

     void deleteTask(Task task);

     void deleteEpic(Epic epic);

     void deleteSubtask(Subtask subtask);

     Task getTaskById(int taskId);

     Epic getEpicById(int taskId);

     Subtask getSubtaskById(int taskId);

     void updateTask(Task updatedTask);

     void updateEpic(Epic updatedEpic);

     void updateSubtask(Subtask updatedSubtask);

     ArrayList<Subtask> getAllSubtasksForEpic(int id);

     void setSubTaskStatus(Subtask subtask, Status status);

     void updateEpicStatus(Epic epic);

     List<Task> getHistory();
}
