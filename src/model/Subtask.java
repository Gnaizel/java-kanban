package model;

import java.time.Duration;
import java.time.LocalDateTime;

public class Subtask extends Task {

    private final Type type;
    private final int id;
    private final Epic epic;
    private final int count;
    private LocalDateTime endTime;

    public Subtask(Status status,
                   String subTaskName,
                   String description,
                   Epic epic,
                   Duration duration,
                   LocalDateTime startTime) {
        super(status, subTaskName, description, duration, startTime);
        this.epic = epic;
        epic.addSubTask(this);
        this.endTime = getEndTime();
        this.count = ++service.ID.EpicCount; // Нужен для рабботы map В будующем уёдет так как не думаю что пользователь будет что- то искать по счёту тасков
        id = service.ID.generateId();
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
        this.count = ++service.ID.SubTaskCount;
        this.endTime = getEndTime();
        this.id = id;
        epic.addSubTask(this);
        this.type = Type.SUBTASK;
    }

    public int getEpicId() {
        if (this.epic != null) return this.epic.getID();
        else return 0;
    }

    public LocalDateTime getEndTime() {
        return getStartTime().plusMinutes(this.getDuration());
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
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
                ", " + getStartTime() +
                ", " + getEpicId();
    }
}