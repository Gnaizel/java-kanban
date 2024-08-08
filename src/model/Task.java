package model;

import service.FileBackedTaskManager;

import java.time.Duration;
import java.time.LocalDateTime;

public class Task implements Comparable<Task> {

    private final Type type;
    private final String taskName;
    private final String taskDescription;
    private Status status;
    private final int id;
    private Duration duration; //продолжительность задачи
    private LocalDateTime startTime; // дата и время, когда предполагается приступить к выполнению задачи.

    public Task(Status status, String taskName, String taskDescription, Duration duration, LocalDateTime startTime) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.duration = duration;
        this.startTime = startTime;
        this.status = status;
        this.id = ++service.ID.TaskId;
        this.type = Type.TASK;
    }

    public Task(Status status, String taskName, String taskDescription, Duration duration, LocalDateTime startTime, int id) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.duration = duration;
        this.startTime = startTime;
        this.status = status;
        this.id = id;
        this.type = Type.TASK;
    }

    public static void fromString(String str, FileBackedTaskManager manager) {
        String[] line = str.split(", ");
        if (line.length < 5) {
            throw new IllegalArgumentException("Недостаточно параметров для создания задачи");
        }
        Status status = Status.valueOf(line[3]);
        String name = line[2];
        String description = line[4];
        Duration duration = Duration.ofMinutes(Long.parseLong(line[5]));
        LocalDateTime startTime = LocalDateTime.parse(line[6]);
        int id = Integer.parseInt(line[0]);
        switch (line[1]) {
            case "TASK":
                manager.createTaskForSaved(new Task(status, name, description, duration, startTime, id));
                break;
            default:
                break;
            case "EPIC":
                manager.createEpicForSaved(new Epic(status, name, description, duration, startTime, id));
                break;
            case "SUBTASK":
                int epicId = Integer.parseInt(line[7]);
                manager.createSubtaskForSaved(new Subtask(status, name, description, id, manager.getEpicById(epicId), duration, startTime));
                break;
        }
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public long getDuration() {
        return duration.toMinutes();
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return this.startTime.plus(this.duration);
    } //дата и время завершения задачи, которые рассчитываются исходя из startTime и duration.

    public String getTaskName() {
        return taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getID() {
        return id;
    }

    @Override
    public int compareTo(Task task) {
        return this.getStartTime().compareTo(task.getStartTime());
    }

    @Override
    public String toString() {
        return id + ", " + type + ", " + taskName + ", " + status + ", " + taskDescription + ", " + duration.toMinutes() + ", " + startTime;
    }
}