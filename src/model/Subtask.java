package model;

import java.time.Duration;
import java.time.LocalDateTime;

public class Subtask extends Task {

    private final Type type;
    private final int id;
    private final Epic epic;


    public Subtask(Status status,
                   String subTaskName,
                   String description,
                   Epic epic,
                   Duration duration,
                   LocalDateTime startTime) {
        super(status, subTaskName, description, duration, startTime);
        this.epic = epic;
        epic.addSubTask(this);
        this.id = ++service.ID.SubTaskId;
        this.type = Type.SUBTASK;
    }


    public Subtask(Status status,
                   String subTaskName,
                   String description,
                   int id,
                   Epic epic,
                   Duration duration,
                   LocalDateTime startTime) {
        super(status, subTaskName, description, duration, startTime);
        this.epic = epic;
        this.id = id;
        epic.addSubTask(this);
        this.type = Type.SUBTASK;
    }

    public int getEpicId() {
        if (this.epic != null) return this.epic.getID();
        else return 0;
    }

    @Override
    public int getID() {
        return this.id;
    }

    @Override
    public String toString() {
        return id +
                ", " + type +
                ", " + getTaskName() +
                ", " + getStatus() +
                ", " + getTaskDescription() +
                ", " + getDuration() +
                ", " + getStartTime().format(formatter) +
                ", " + getEpicId();
    }
}