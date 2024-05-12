import java.util.HashMap;
import java.util.ArrayList;

public class TaskManager {
    private final HashMap<Integer, Task> tasksMap = new HashMap<>();
    private final HashMap<Integer, Epic> epicMap = new HashMap<>();

    public void createTask(Task task) {
        tasksMap.put(task.getTaskId(), task);
    }

    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasksMap.values());
    }

    public void deleteAllTasks() {
        tasksMap.clear();
    }

    public Task getTaskById(int taskId) {
        return tasksMap.get(taskId);
    }

    public Task getTaskByName(String name) {
        for (Task task : tasksMap.values()) {
            if (task.taskName.equals(name)) {
                return tasksMap.get(task.taskId);
            }
        }
        return null;
    }

    public void updateTask(Task updatedTask) {
        tasksMap.replace(updatedTask.getTaskId(), updatedTask);
        tasksMap.put(updatedTask.getTaskId(), updatedTask);
    } // доработать

    public void deleteTaskById(int taskId) {
        tasksMap.remove(taskId);
    }

    public void deleteTaskByName(String name) {
        for (Task task : tasksMap.values()) {
            if (task.taskName.equals(name)) {
                tasksMap.remove(task.getTaskId());
            }
        }
    }

    // Epic
    public ArrayList<Subtask> getAllSubtasksForEpic(String epicName) {
        ArrayList<Subtask> subtasks = new ArrayList<>();
        for (Epic epic : epicMap.values()) {
            if (epic.epicName.equals(epicName)) {
                subtasks = epic.subtasks;
            }
        }
        return subtasks;
    }

//    public void manageStatus(Task task, Status status) {
//        if (task instanceof Epic) {
//            Epic epic = (Epic) task;
//            boolean allSubtasksDone = epic.getSubtasks().stream().allMatch(subtask -> subtask.getStatus() == Status.DONE);
//            if (epic.getSubtasks().isEmpty() || allSubtasksDone) {
//                epic.setStatus(Status.NEW);
//            } else {
//                epic.setStatus(status);
//            }
//        } else {
//            task.setStatus(status);
//        }
//    }
}
