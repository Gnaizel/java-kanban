package model;

public class Subtask extends Task {

    private String subTaskName;
    private int beEpic;
    private String taskDescription;
    private Status status;
    private int ID;

    Subtask(Status status, String subTaskName, String description, Epic epic) {
        super(status, subTaskName, description);
        this.subTaskName = subTaskName;
        this.beEpic = epic.getID();
        this.taskDescription = description;
        this.status = status;
        this.ID = service.ID.SubTaskId++;
    }
}
