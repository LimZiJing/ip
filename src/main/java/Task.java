public class Task {
    protected String taskName;
    protected boolean isDone;

    public Task(String taskName) {
        this.taskName = taskName;
        this.isDone = false;
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
}
