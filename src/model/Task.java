package model;

public class Task {

    private final Type type;
    private final String taskName;
    private String taskDescription;
    private Status status;
    private final int id;

    public Task(Status status, String taskName, String taskDescription) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.status = status;
        this.id = ++service.ID.TaskId;
        this.type = Type.TASK;
    }

    public Task(Status status, String taskName, String taskDescription, int id) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.status = status;
        this.id = id;
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

    public String getTaskName() {
        return taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getID() {
        return id;
    }

    @Override
    public String toString() {
        return id +
                ", " + type +
                ", " + taskName +
                ", " + status +
                ", " + taskDescription;
    }
}