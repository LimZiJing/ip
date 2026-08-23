public class Task {

    private String taskName;
    private boolean isDone;

    public Task(String taskName) {
        this.taskName = taskName;
        this.isDone = false;
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
        System.out.printf("[%s] %s\n", this.getStatusIcon(), this.getTaskName());
    }
}
