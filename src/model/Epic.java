package model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {

    private final List<Subtask> subTasks;
    private final Type type;
    private final int id;
    private LocalDateTime endTime;

    public Epic(String epicName,
                String epicDescription) {
        super(Status.NEW, epicName, epicDescription, Duration.ZERO, LocalDateTime.now());
        this.subTasks = new ArrayList<>();
        this.id = ++service.ID.EpicId;
        this.type = Type.EPIC;
    }

    public Epic(Status status,
                String epicName,
                String epicDescription,
                Duration duration,
                LocalDateTime startTime,
                int id) {
        super(status, epicName, epicDescription, duration, startTime);
        this.subTasks = new ArrayList<>();
        this.id = id;
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
            LocalDateTime endTime = subTasks.getFirst().getEndTime();
            for (Subtask subTask : subTasks) {
                if (subTask.getEndTime().isAfter(endTime)) {
                    endTime = subTask.getEndTime();
                }
            }
            setEndTime(endTime);
        }
    }

    private void calculateStartTimeEpic() {
        if (!subTasks.isEmpty()) {
            LocalDateTime startTime = subTasks.getFirst().getStartTime();
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

    public List<Subtask> getSubTasks() {
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

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
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
                ", " + getStartTime().format(formatter) +
                ", " + getSubTasks();
    }

    @Override
    public int getID() {
        return this.id;
    }
}