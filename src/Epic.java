import java.util.Objects;
import java.util.ArrayList;

public class Epic extends Task {
    String epicName;
    String epicDescription;
    Status status;
    int epicNumber = 1;
    int subTaskNumber = 0;
    int epicId = 1;
    ArrayList<Subtask> subtasks;

    Epic(Status status, String epicName, String epicDescription) {
        super(status, epicName, epicDescription);
        this.epicName = epicName;
        this.status = status;
        this.epicDescription = epicDescription;
        epicId = Objects.hashCode(epicNumber);
        subtasks = new ArrayList<>();
        epicNumber++;
    }

    @Override
    public void manageStatus(Status status) {
        boolean allSubtasksDone = true;
        for (Task subtask : getSubtasks()) {
            if (subtask.getStatus() != Status.DONE) {
                allSubtasksDone = false;
                break;
            }
        }
        if (allSubtasksDone) {
            setStatus(Status.DONE);
        } else {
            setStatus(status);
        }
    }

    public void setSubTask(Status status, String subtaskName, String description) {
        subtasks.add(new Subtask(status, subtaskName, description));
        subTaskNumber++;
    }
    public void deleteSubTask(String subtaskName) {
        subtasks.remove(subTaskNumber);
        subTaskNumber--;
    }

    public String getEpicName() {
        return epicName;
    }

    public void setEpicName(String epicName) {
        this.epicName = epicName;
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
}
