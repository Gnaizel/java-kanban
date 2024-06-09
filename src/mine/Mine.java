package mine;

import model.*;
import service.*;

public class Mine {

    public static void main(String[] args) {
        TaskManager manager = new InMemoryTaskManager();

        Epic epic = new Epic("epic1", "Description");
        manager.createEpic(epic);
        System.out.println("Epic created");

        Subtask subtask = new Subtask(Status.IN_PROGRESS, "SubTaskNameByEpic1", "Description", epic);
        manager.createSubtask(subtask);
        System.out.println("Subtask created");

        Task task = new Task(Status.IN_PROGRESS, "Task-Default", "Description");
        manager.createTask(task);
        System.out.println("Task created");

        manager.getSubtaskById(subtask.getID());
        System.out.println("Subtask get subtask by ID");

        printAllTasks(manager);
    }
    private static void printAllTasks(TaskManager manager) {
        System.out.println("Задачи:");
        for (Task task : manager.getAllTasks()) {
            System.out.println(task);
        }
        System.out.println("Эпики:");
        for (Task epic : manager.getAllEpic()) {
            System.out.println(epic);

            for (Task subtask : manager.getAllSubtasksForEpic(epic.getID())) {
                System.out.println("--> " + subtask);
            }
        }
        System.out.println("Подзадачи:");
        for (Task subtask : manager.getAllSubtask()) {
            System.out.println(subtask);
        }

        System.out.println("История:");
        for (Task task : manager.getHistory()) {
            System.out.println(task);
        }
    }
}
