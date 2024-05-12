import java.util.Objects;

public class Task {
    protected int  taskCount = 1;
    protected int taskId = 1;
    String taskName;
    String taskDescription;
    Status status;

    Task(Status status, String taskName, String taskDescription) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        taskId = Objects.hashCode(taskCount);
        taskCount++;
    }

    public void manageStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getTaskCount() {
        return taskCount;
    }

    public int getTaskId() {
        return taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }


}