package model;

public class Subtask extends Task {

    private final int Id;
    private Epic epic;

    public Subtask(Status status, String subTaskName, String description, Epic epic) {
        super(status, subTaskName, description);
        this.epic = epic;
        epic.addSubTask(this);
        this.Id = service.ID.SubTaskId;
    }

    public int getEpicId() {
        if (this.epic != null) return this.epic.getID();
        else return 0; // или какое-то другое значение по умолчанию
    }

    public void setEpic(Epic epic) {
        this.epic = epic;
    }

    @Override
    public int getID() {
        return this.Id;
    }
}