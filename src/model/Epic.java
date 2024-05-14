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

    public void deleteSubTask(Subtask subtask) {
        subtasks.remove(subtask);
    }

    public void clearSubTasks() {
        subtasks.clear();
    }

    public void addSubTask(Subtask subtask) {
        subtasks.add(subtask);
    }

    public ArrayList<Subtask> getSubtasks() {
        return subtasks;
    }

}
