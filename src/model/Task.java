package model;

public class Task {

    private final Type type;
    private String taskName;
    private String taskDescription;
    private Status status;
    private final int Id;

    public Task(Status status, String taskName, String taskDescription) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.status = status;
        this.Id = ++service.ID.TaskId;
        this.type = Type.TASK;
    }

    public void manageStatus(Status status) {
        this.status = status;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getID() {
        return Id;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return Id +
                ", " + type +
                ", " + taskName +
                ", " + status +
                ", " + taskDescription;
    }
}