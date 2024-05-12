public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        Task task1 = new Task(Status.IN_PROGRESS, "Первый таск", "Description 1");
        Task task2 = new Task(Status.NEW, "Второй Таск", "Description 2");
        taskManager.createTask(task1);
        taskManager.createTask(task2);

        Epic epic1 = new Epic(Status.DONE, "Первый Эпик", "Epic Description 1");
        Epic epic2 = new Epic(Status.NEW, "Второй Эпик", "Epic Description 2");
        taskManager.createEpic(epic1);
        taskManager.createEpic(epic2);

        taskManager.getAllTasks();
        taskManager.getAllEpics();

        System.out.println("Задача с идентификатором 1: " + taskManager.getTaskById(1));

        Task updatedTask = new Task(Status.DONE, "Updated Task 1", "Updated Description 1");
        taskManager.updateTask(updatedTask);
        System.out.println("Обновленная задача 1: " + taskManager.getTaskById(1));

        taskManager.deleteTaskById(2);
        System.out.println("Все задачи после удаления: ");
        taskManager.getAllTasks();

        Subtask subtask1 = new Subtask(Status.IN_PROGRESS, "Subtask 1", "Description 1");
        Subtask subtask2 = new Subtask(Status.DONE, "Subtask 2", "Description 2");
        epic1.addSubTask(subtask1);
        epic1.addSubTask(subtask2);
        System.out.println("Подзадачи для Epic 1: " + taskManager.getAllSubtasksForEpic("Epic 1"));

        taskManager.manageStatus(task1, Status.IN_PROGRESS);
        System.out.println("Обновленный статус выполнения задачи 1: " + task1.getStatus());
    }
}
