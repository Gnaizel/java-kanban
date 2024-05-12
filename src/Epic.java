import java.util.Objects;
import java.util.ArrayList;

public class Epic extends Task {
    String epicName;
    String epicDescription;
    int epicNumber = 1;
    int subTaskNumber = 0;
    int epicId = 1;
    ArrayList<Subtask> subtasks;

    Epic(Status status, String epicName, String epicDescription) {
        super(status, epicName, epicDescription);
        this.epicName = epicName;
        this.epicDescription = epicDescription;
        epicId = Objects.hashCode(epicNumber);
        subtasks = new ArrayList<>();
        epicNumber++;
    }

    public void setSubTask(String subtaskName) {
        subtasks.add(new Subtask(subtaskName));
        subTaskNumber++;
    }
    public void deleteSubTask(String subtaskName) {
        subtasks.remove(subTaskNumber);
        subTaskNumber--;
    }

}
