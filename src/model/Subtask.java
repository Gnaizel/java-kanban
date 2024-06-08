package model;

public class Subtask extends Task {

    private int beEpic;
    private Epic epic;

    public Subtask(Status status, String subTaskName, String description, Epic epic) {
        super(status, subTaskName, description);
        setBeEpic(epic);
        int ID = service.ID.SubTaskId;
        this.epic = epic;
        int epicID = epic.getID();
    }

    public Epic getEpic() {
        return epic;
    }

    public void setBeEpic(Epic epic) {
        beEpic = epic.getID();
    }

    public int getEpicId() {
        return epic.getID();
    }
}
