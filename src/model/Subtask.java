package model;

public class Subtask extends Task {

    private String subTaskName;
    private Status status;

    Subtask(Status status, String subTaskName, String description) {
        super(status, subTaskName, description);
        this.status = status;
        this.subTaskName = subTaskName;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getSubTaskName() {
        return subTaskName;
    }

    public void setSubTaskName(String subTaskName) {
        this.subTaskName = subTaskName;
    }
}
