import model.*;
import service.*;

public class Mine {

    public static void main(String[] args) {
        TaskManager manager = new InMemoryTaskManager();

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

            for (Task subtask : manager.getSubtasksById(epic.getID())) {
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
