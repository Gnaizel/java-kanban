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

    public Task(Status status, String taskName, String taskDescription, int Id) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.status = status;
        this.Id = Id;
        this.type = Type.TASK;
    }

        public static Task fromString(String str) {
            String[] line = str.split(", ");
            if (line.length < 5) {
                throw new IllegalArgumentException("Недостаточно параметров для создания задачи");
            }
            Status status = Status.valueOf(line[3]);
            String name = line[2];
            String description = line[4];
            return new Task(status, name, description, Integer.parseInt(line[0]));
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