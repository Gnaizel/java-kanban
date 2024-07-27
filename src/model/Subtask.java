package model;

import java.util.HashMap;
import java.util.Map;

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

    public Subtask(Status status, String subTaskName, String description, int id,  Epic epic) {
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

    public static Subtask fromString(String line, Map<Integer, Epic> epics) {
        String[] parts = line.split(", ");
        if (parts.length < 5) {
            throw new RuntimeException("Subtask line does not contain 5 parts: " + line);
        }
        return new Subtask(Status.valueOf(parts[3]), parts[2], parts[4], Integer.parseInt(parts[0]), epics.get(Integer.parseInt(parts[5])));
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