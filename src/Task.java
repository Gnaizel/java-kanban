import java.util.Objects;

public class Task {
    protected int  taskCount = 1;
    protected int taskId = 1;
    String taskName;
    String taskDescription;

    Task(Status status, String taskName, String taskDescription) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        taskId = Objects.hashCode(taskCount);
        taskCount++;
    }

//    @Override
//    public boolean equals(Object o) {
//
//    }

    //Set and Get
    public int getTaskCount() {
        return taskCount;
    }

    public void setTaskCount(int taskCount) {
        this.taskCount = taskCount;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }


}