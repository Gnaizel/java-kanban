package model;

import service.ID;

import java.util.ArrayList;

public class Epic extends Task {

    private final ArrayList<Subtask> subtasks;
    private final int id;

    public Epic(String epicName, String epicDescription) {
        super(Status.NEW, epicName, epicDescription);
        this.subtasks = new ArrayList<>();
        this.id = ++ID.EpicId;
    }

    public boolean hasNoSubtasks() {
        return subtasks.isEmpty();
    }

    public ArrayList<Subtask> getSubtasks() {
        return this.subtasks;
    }

    public void addSubTask(Subtask subtask) {
        if (subtask == null) return;
        if (!subtasks.contains(subtask)) this.subtasks.add(subtask);
    }

    public void removeSubTask(Subtask subtask) {
        this.subtasks.remove(subtask);
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
        return this.id;
    }

}