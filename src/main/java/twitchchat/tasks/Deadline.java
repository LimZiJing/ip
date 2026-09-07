package twitchchat.tasks;

public class Deadline extends Task{

    private final String endTime;
    private static final String ICON = "D";

    public Deadline(String taskName, String endTime) {
        super(taskName);
        this.endTime = endTime;
    }

    public String getIcon() {
        return ICON;
    }

    @Override
    public void printTask() {
        String taskDetails  = this.getTaskName() + " (by: " + endTime + ")";
        System.out.printf("[%s][%s] %s\n", getIcon(), this.getStatusIcon(), taskDetails);
    }
}
