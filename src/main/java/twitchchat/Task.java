package twitchchat;

/** Represents a task and whether the user has completed it. */
public class Task {

    private String taskName;
    private boolean isDone;

    /** Creates an incomplete task with the specified name. */
    public Task(String taskName) {
        this.taskName = taskName;
        this.isDone = false;
    }

    /** Returns the task name. */
    public String getTaskName() {
        return taskName;
    }

    /** Changes the task name. */
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    /** Returns the display icon for the task's completion status. */
    public String getStatusIcon() {
        if (this.isDone) {
            return "X";
        }
        return " ";
    }

    /** Marks this task as completed. */
    public void markAsDone() {
        this.isDone = true;
    }

    /** Marks this task as incomplete. */
    public void markAsNotDone() {
        this.isDone = false;
    }

    /** Prints this task's status icon and name. */
    public void printTask() {
        System.out.printf("[%s] %s\n", this.getStatusIcon(), this.getTaskName());
    }
}
