package twitchchat;

public class Task {

    private String taskName;
    private boolean isDone;
    private TaskType taskType;
    private String startTime;
    private String endTime;

    public Task(String taskName) {
        this(taskName, TaskType.TODO, "", "");
    }

    public Task(String taskName, TaskType taskType, String startTime, String endTime) {
        this.taskName = taskName;
        this.isDone = false;
        this.taskType = taskType;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getStatusIcon() {
        if (this.isDone) {
            return "X";
        }
        return " ";
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsNotDone() {
        this.isDone = false;
    }

    public void printTask() {
        String taskDetails = this.getTaskName();
        if (taskType == TaskType.DEADLINE) {
            taskDetails += " (by: " + endTime + ")";
        } else if (taskType == TaskType.EVENT) {
            taskDetails += " (from: " + startTime + " to: " + endTime + ")";
        }
        System.out.printf("[%s][%s] %s\n", taskType.getIcon(), this.getStatusIcon(), taskDetails);
    }
}

enum TaskType {
    TODO("T"),
    DEADLINE("D"),
    EVENT("E");

    private final String icon;

    TaskType(String icon) {
        this.icon = icon;
    }

    public String getIcon() {
        return icon;
    }
}
