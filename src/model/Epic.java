package model;

import java.util.ArrayList;

public class Epic extends Task {

    private int ID;
    private ArrayList<Subtask> subtasks;

    public Epic(Status status, String epicName, String epicDescription) {
        super(status, epicName, epicDescription);
        this.ID = service.ID.EpicId++;
        this.subtasks = new ArrayList<>();
    }

    public boolean subtasksNull() {
        return subtasks.isEmpty();
    }

    public ArrayList<Subtask> getSubtasks() {
        return subtasks;
    }

    public void updateEpicStatus() {
        if (allSubtasksDone()) {
            setStatus(Status.DONE);
        } else if (allSubtasksNew()) {
            setStatus(Status.NEW);
        } else {
            setStatus(Status.IN_PROGRESS);
        }
    }

    public boolean allSubtasksDone() {
        for (Task subtask : subtasks) {
            if (subtask.getStatus() != Status.DONE) {
                return false;
            }
        }
        return true;
    }

    public boolean allSubtasksNew() {
        for (Task subtask : subtasks) {
            if (subtask.getStatus() != Status.NEW) {
                return false;
            }
        }
        return true;
    }


}
