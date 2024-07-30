package model;

import service.FileBackedTaskManager;

public class Task {

    private final Type type;
    private final String taskName;
    private final String taskDescription;
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

    public static void fromString(String str, FileBackedTaskManager manager) {
        String[] line = str.split(", ");
        if (line.length < 5) {
            throw new IllegalArgumentException("Недостаточно параметров для создания задачи");
        }
        Status status = Status.valueOf(line[3]);
        String name = line[2];
        String description = line[4];
        int id = Integer.parseInt(line[0]);
        switch (line[1]) {
            case "TASK":
                manager.createTaskForSaved(new Task(status, name, description, id));
                break;
            default:
                break;
            case "EPIC":
                manager.createEpicForSaved(new Epic(status, name, description, id));
                break;
            case "SUBTASK":
                int epicId = Integer.parseInt(line[5]);
                manager.createSubtaskForSaved(new Subtask(status, name, description, id, manager.getEpicById(epicId)));
                break;
        }
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