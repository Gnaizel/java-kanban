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
        setBeEpic(epic);
        this.taskDescription = description;
        this.status = status;
        this.ID = service.ID.SubTaskId++;
    }

    public int getBeEpic() {
        return beEpic;
    }

    public void setBeEpic(Epic epic) {
        beEpic = epic.getID();
    }
}
