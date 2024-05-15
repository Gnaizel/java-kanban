package model;

public class Subtask extends Task {

    private int beEpic;

    Subtask(Status status, String subTaskName, String description, Epic epic) {
        super(status, subTaskName, description);
        setBeEpic(epic);
        int ID = service.ID.SubTaskId++;
    }

    public int getBeEpic() {
        return beEpic;
    }

    public void setBeEpic(Epic epic) {
        beEpic = epic.getID();
    }
}
