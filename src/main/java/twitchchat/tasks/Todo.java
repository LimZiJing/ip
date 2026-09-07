package twitchchat.tasks;

public class Todo extends Task {
    private static final String ICON = "T";

    public Todo(String taskName) {
        super(taskName);
    }

    @Override
    public String getIcon() {
        return ICON;
    }

    @Override
    public void printTask() {
        System.out.printf("[%s][%s] %s\n", getIcon(), this.getStatusIcon(), this.getTaskName());
    }
}
