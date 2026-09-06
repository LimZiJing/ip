package twitchchat.tasks;

public class Todo extends Task {
    private static final String icon = "T";

    public Todo(String taskName) {
        super(taskName);
    }

    @Override
    public String getIcon() {
        return icon;
    }

    @Override
    public void printTask() {
        System.out.printf("[%s][%s] %s\n", getIcon(), this.getStatusIcon(), this.getTaskName());
    }
}
