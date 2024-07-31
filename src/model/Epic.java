package model;

import java.util.ArrayList;

public class Epic extends Task {

    private final ArrayList<Subtask> subtasks;
    private final Type type;
    private final int id;

    public Epic(String epicName, String epicDescription) {
        super(Status.NEW, epicName, epicDescription);
        this.subtasks = new ArrayList<>();
        this.id = ++service.ID.EpicId;
        this.type = Type.EPIC;
    }

    public Epic(Status status, String epicName, String epicDescription, int id) {
        super(status, epicName, epicDescription);
        this.subtasks = new ArrayList<>();
        this.id = id;
        this.type = Type.EPIC;
    }

    public boolean hasNoSubtasks() {
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

    public void removeSubTask(Subtask subtask) {
        subtasks.remove(subtask);
    }

    @Override
    public String toString() {
        return id +
                ", " + type +
                ", " + getTaskName() +
                ", " + getStatus() +
                ", " + getTaskDescription() +
                ", " + getSubtasks();
    }

    @Override
    public int getID() {
        return this.id;
    }
}