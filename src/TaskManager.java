import java.util.HashMap;
import java.util.ArrayList;

public class TaskManager {
    private final HashMap<Integer, Task> tasksMap = new HashMap<>();
    private final HashMap<Integer, Epic> epicMap = new HashMap<>();

    public void createTask(Task task) {
        tasksMap.put(task.getTaskId(), task);
    }

    public void getAllTasks() {
        System.out.println("All Task:");
        for (Task task : tasksMap.values()) {
            System.out.println(task.getTaskId() + "\n" + task.getTaskName() + "\n" + task.getTaskDescription() + "\n" + task.getStatus());
        }
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

    public void updateTask(Task updatedTask) {
        tasksMap.replace(updatedTask.getTaskId(), updatedTask);
    }

    public void deleteTaskById(int taskId) {
        tasksMap.remove(taskId);
    }

    public void deleteTaskByName(String name) {
        for (Task task : tasksMap.values()) {
            if (task.getTaskName().equals(name)) {
                tasksMap.remove(task.getTaskId());
            }
        }
    }

    public void createEpic(Epic epic) {
        epicMap.put(epic.getTaskId(), epic);
    }

    public void getAllEpics() {
        System.out.println("All epic:");
        for (Epic epic : epicMap.values()) {
            System.out.println(epic.getTaskId() + "\n" + epic.getTaskName() + "\n" + epic.getTaskDescription() + "\n" + epic.getStatus());
        }
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
            }
        }
        return new ArrayList<>();
    }

    public void manageStatus(Task task, Status status) {
        task.manageStatus(status);
    }
}
