public class Main {

//    public static void main(String[] args) {
//        service.TaskManager taskManager = new service.TaskManager();
//
//        model.Task task1 = new model.Task(model.Status.IN_PROGRESS, "Первый таск", "Description 1");
//        model.Task task2 = new model.Task(model.Status.NEW, "Второй Таск", "Description 2");
//        taskManager.createTask(task1);
//        taskManager.createTask(task2);
//
//        model.Epic epic1 = new model.Epic(model.Status.DONE, "Первый Эпик", "model.Epic Description 1");
//        model.Epic epic2 = new model.Epic(model.Status.NEW, "Второй Эпик", "model.Epic Description 2");
//        taskManager.createEpic(epic1);
//        taskManager.createEpic(epic2);
//
//        taskManager.getAllTasks();
//        taskManager.getAllEpics();
//
//        System.out.println("Задача с идентификатором 1: " + taskManager.getTaskById(1));
//
//        model.Task updatedTask = new model.Task(model.Status.DONE, "Updated model.Task 1", "Updated Description 1");
//        taskManager.updateTask(updatedTask);
//        System.out.println("Обновленная задача 1: " + taskManager.getTaskById(1));
//
//        taskManager.deleteTaskById(2);
//        System.out.println("Все задачи после удаления: ");
//        taskManager.getAllTasks();
//
//        model.Subtask subtask1 = new model.Subtask(model.Status.IN_PROGRESS, "model.Subtask 1", "Description 1");
//        model.Subtask subtask2 = new model.Subtask(model.Status.DONE, "model.Subtask 2", "Description 2");
//        epic1.addSubTask(subtask1);
//        epic1.addSubTask(subtask2);
//        System.out.println("Подзадачи для model.Epic 1: " + taskManager.getAllSubtasksForEpic("model.Epic 1"));
//
//        taskManager.manageStatus(task1, model.Status.IN_PROGRESS);
//        System.out.println("Обновленный статус выполнения задачи 1: " + task1.getStatus());
//    }
}
