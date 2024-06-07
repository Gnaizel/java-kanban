package model;

import service.ID;

import java.util.ArrayList;

public class Epic extends Task {

    private ArrayList<Subtask> subtasks;

    public Epic(Status status, String epicName, String epicDescription) {
        super(status, epicName, epicDescription);
        final int ID = service.ID.EpicId;
        this.subtasks = new ArrayList<>();
    }

    public boolean subtasksNull() {
        return subtasks.isEmpty();
    }

    public ArrayList<Subtask> getSubtasks() {
        return subtasks;
    }

    public void addSubTask(Subtask subtask) {
        this.subtasks.add(subtask);
    }

    public boolean allSubtasksDone() {
        for (Task subtask : subtasks) {
            if (subtask.getStatus() != Status.DONE) {
                return false;
            }
        }
        return true;
    }

}
