package twitchchat.tasks;

public class Deadline extends Task{

    private String endTime;
    private static final String icon = "D";

    public Deadline(String taskName, String endTime) {
        super(taskName);
        this.endTime = endTime;
    }

    public String getIcon() {
        return icon;
    }

    @Override
    public void printTask() {
        String taskDetails  = this.getTaskName() + " (by: " + endTime + ")";
        System.out.printf("[%s][%s] %s\n", getIcon(), this.getStatusIcon(), taskDetails);
    }
}
