package model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Epic extends Task {

    private final ArrayList<Subtask> subTasks;
    private final Type type;
    private final int id;
    private final int count;
    private LocalDateTime endTime;

    public Epic(String epicName,
                String epicDescription) {
        super(Status.NEW, epicName, epicDescription, Duration.ZERO, LocalDateTime.of(2024, 01, 01, 00, 00));
        this.subTasks = new ArrayList<>();
        this.count = ++service.ID.EpicCount; // Нужен для рабботы map В будующем уёдет так как не думаю что пользователь будет что- то искать по счёту тасков
        this.endTime = getEndTime();
        id = service.ID.generateId();
        this.type = Type.EPIC;
    }

    public Epic(Status status,
                String epicName,
                String epicDescription,
                Duration duration,
                LocalDateTime startTime, int id) {
        super(status, epicName, epicDescription, duration, startTime);
        this.id = id;
        this.subTasks = new ArrayList<>();
        this.count = ++service.ID.EpicCount;
        this.endTime = getEndTime();
        this.type = Type.EPIC;
    }

    public void updateTime() {
        updateDurationEpic();
        updateEndTimeEpic();
        calculateStartTimeEpic();
    }

    private void updateDurationEpic() {
        Duration duration = Duration.ZERO;
        for (Subtask subTask : subTasks) {
            duration.plus(Duration.ofMinutes(subTask.getDuration()));
        }
        this.setDuration(duration);
    }

    private void updateEndTimeEpic() {
        if (!subTasks.isEmpty()) {
            LocalDateTime endTime = subTasks.get(0).getEndTime();
            for (Subtask subTask : subTasks) {
                if (subTask.getEndTime().isAfter(endTime)) {
                    endTime = subTask.getEndTime();
                }
            }
            setEndTime(endTime);
        }
    }

    public LocalDateTime getEndTime() {
        return getStartTime().plusMinutes(this.getDuration());
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    private void calculateStartTimeEpic() {
        if (!subTasks.isEmpty()) {
            LocalDateTime startTime = subTasks.get(0).getStartTime();
            for (Subtask subTask : subTasks) {
                if (subTask.getStartTime().isBefore(startTime)) {
                    startTime = subTask.getStartTime();
                }
            }
            this.setStartTime(startTime);
        }
    }

    public boolean hasSubtasksIsEmpty() {
        return subTasks.isEmpty();
    }

    public ArrayList<Subtask> getSubTasks() {
        return this.subTasks;
    }

    public void addSubTask(Subtask subtask) {
        if (!subTasks.contains(subtask)) this.subTasks.add(subtask);
    }

    public boolean allSubtasksDone() {
        for (Task subtask : subTasks) {
            if (subtask.getStatus() != Status.DONE) {
                return false;
            }
        }
        return true;
    }

    public void removeSubTask(Subtask subtask) {
        subTasks.remove(subtask);
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
                ", " + getSubTasks();
    }

    @Override
    public int getID() {
        return this.id;
    }
}