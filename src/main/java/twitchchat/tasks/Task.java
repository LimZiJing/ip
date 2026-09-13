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

    /**
     * Returns whether this task is completed.
     *
     * @return true if the task is completed, otherwise false
     */
    public Boolean isDone() {
        return this.isDone;
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsNotDone() {
        this.isDone = false;
    }

    public abstract void printTask();
}
