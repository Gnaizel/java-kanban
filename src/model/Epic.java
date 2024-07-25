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

    @Override
    public int getID() {
        return this.Id;
    }
}