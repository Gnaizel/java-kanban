package model;

public class Subtask extends Task {

    private final Type type;
    private final int Id;
    private Epic epic;

    public Subtask(Status status, String subTaskName, String description, Epic epic) {
        super(status, subTaskName, description);
        this.epic = epic;
        epic.addSubTask(this);
        this.Id = ++service.ID.SubTaskId;
        this.type = Type.SUBTASK;
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

    @Override
    public String toString() {
        return Id +
                ", " + type +
                ", " + getTaskName() +
                ", " + getStatus() +
                ", " + getTaskDescription() +
                ", " + getEpicId();
    }
}