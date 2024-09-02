package model;

import service.FileBackedTaskManager;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Task implements Comparable<Task> {

    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    protected Type type;
    protected String taskName;
    protected String taskDescription;
    protected Status status;
    protected int id;
    protected Duration duration;
    protected LocalDateTime startTime;

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
        LocalDateTime startTime = LocalDateTime.parse(line[6], formatter);
        int id = Integer.parseInt(line[0]);
        switch (line[1]) {
            case "TASK":
                manager.createTaskForSaved(new Task(status, name, description, duration, startTime, id));
                break;
            case "EPIC":
                manager.createEpicForSaved(new Epic(status, name, description, duration, startTime, id));
                break;
            case "SUBTASK":
                int epicId = Integer.parseInt(line[7]);
                manager.createSubtaskForSaved(new Subtask(status, name, description, id, manager.getEpicById(epicId), duration, startTime));
                break;
            default:
                break;
        }
    }

    public Type getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public DateTimeFormatter getFormatter() {
        return formatter;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public long getDuration() {
        return duration.toMinutes();
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(taskName, task.taskName) &&
                Objects.equals(taskDescription, task.taskDescription) &&
                Objects.equals(status, task.status) &&
                id == task.id &&
                Objects.equals(duration, task.duration) &&
                Objects.equals(startTime, task.startTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskName, taskDescription, status, id, duration, startTime);
    }

    @Override
    public int compareTo(Task task) {
        return this.getStartTime().compareTo(task.getStartTime());
    }

    @Override
    public String toString() {
        return id + ", "
                + type + ", "
                + taskName + ", "
                + status + ", "
                + taskDescription + ", "
                + duration.toMinutes() + ", "
                + startTime.format(formatter);
    }
}