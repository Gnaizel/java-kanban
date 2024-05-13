package model;

import java.util.ArrayList;

public class Epic extends Task {
    Status status;
    int subTaskNumber = 0;
    int epicId = 1;
    ArrayList<Subtask> subtasks;

    Epic(Status status, String epicName, String epicDescription) {
        super(status, epicName, epicDescription);
        taskName = epicName;
        this.status = status;
        taskDescription = epicDescription;
        epicId++;
        subtasks = new ArrayList<>();
    }

    public boolean subtasksNull() {
        return subtasks.isEmpty();
    }

    public void addSubTask(Status status, String subtaskName, String description) {
        subtasks.add(new Subtask(status, subtaskName, description));
        subTaskNumber++;
    }
    public void deleteSubTask(Subtask subtask) {
        subtasks.remove(subtask);
        subTaskNumber--;
    }

    public String getEpicName() {
        return taskName;
    }

    public void setEpicName(String epicName) {
        this.taskName = epicName;
    }

    public ArrayList<Subtask> getSubtasks() {
        return subtasks;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void addSubTask(Subtask subtask) {
        subtasks.add(subtask);
        subTaskNumber++;
    }
}
