package model;

import java.util.ArrayList;

public class Epic extends Task {

    private final ArrayList<Subtask> subtasks;
    private final Type type;
    private final int Id;

    public Epic(String epicName, String epicDescription) {
        super(Status.NEW, epicName, epicDescription);
        this.subtasks = new ArrayList<>();
        this.Id = ++service.ID.EpicId;
        this.type = Type.EPIC;
    }

    public Epic(Status status, String epicName, String epicDescription, int id) {
        super(status, epicName, epicDescription);
        this.subtasks = new ArrayList<>();
        this.Id = id;
        this.type = Type.EPIC;
    }

    public boolean subtasksNull() {
        return subtasks.isEmpty();
    }

    public ArrayList<Subtask> getSubtasks() {
        return this.subtasks;
    }

    public void addSubTask(Subtask subtask) {
        if (!subtasks.contains(subtask)) this.subtasks.add(subtask);
    }

    public boolean allSubtasksDone() {
        for (Task subtask : subtasks) {
            if (subtask.getStatus() != Status.DONE) {
                return false;
            }
        }
        return true;
    }

    public static Epic fromString(String epicString) {
        String[] split = epicString.split(", ");
        return new Epic(Status.valueOf(split[3]), split[2], split[4], Integer.parseInt(split[0]));
    }

    @Override
    public String toString() {
        return Id +
                ", " + type +
                ", " + getTaskName() +
                ", " + getStatus() +
                ", " + getTaskDescription() +
                ", " + getSubtasks();
    }

    @Override
    public int getID() {
        return this.Id;
    }
}