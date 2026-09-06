package twitchchat.tasks;

public class Event extends Task{
    private String startTime;
    private String endTime;
    private static final String icon = "E";

    public Event(String taskName, String startTime, String endTime) {
        super(taskName);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public String getIcon() {
        return icon;
    }

    @Override
    public void printTask() {
        String taskDetails = this.getTaskName() + " (from: " + startTime + " to: " + endTime + ")";
        System.out.printf("[%s][%s] %s\n", getIcon(), this.getStatusIcon(), taskDetails);
    }
}
