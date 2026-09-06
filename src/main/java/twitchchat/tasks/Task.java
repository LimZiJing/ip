package twitchchat.tasks;

public abstract class Task {

    private String taskName;
    private boolean isDone;

    public Task(String taskName) {
        this.taskName = taskName;
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

    public abstract String getIcon();

    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsNotDone() {
        this.isDone = false;
    }

    public abstract void printTask();
}
