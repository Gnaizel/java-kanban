package model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Epic extends Task {

    private final ArrayList<Subtask> subTasks;
    private final Type type;
    private final int id;

    public Epic(String epicName,
                String epicDescription) {
        super(Status.NEW, epicName, epicDescription, Duration.ZERO, LocalDateTime.of(2024, 01, 01, 00, 00));
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
            duration.plus(subTask.getDuration());
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
            this.setEndTime(endTime);
        }
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