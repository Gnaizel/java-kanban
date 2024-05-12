public class Subtask extends Epic {

    Status status;
    String subTaskName;

    Subtask(Status status, String subTaskName, String description) {
        super(status, subTaskName, description);
        this.status = status;
        this.subTaskName = subTaskName;
    }

    public Status getStatus() {
        return status;
    }
}
