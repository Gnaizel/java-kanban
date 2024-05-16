package model;

public class Subtask extends Task {

    private int beEpic;
    private Epic epic;

    Subtask(Status status, String subTaskName, String description, Epic epic) {
        super(status, subTaskName, description);
        setBeEpic(epic);
        int ID = service.ID.SubTaskId;
        this.epic = epic;
    }

    public Epic getEpic() {
        return epic;
    }

    public void setBeEpic(Epic epic) {
        beEpic = epic.getID();
    }
}
