package twitchchat.tasks;

public class Event extends Task{
    private final String startTime;
    private final String endTime;
    private static final String ICON = "E";

    public Event(String taskName, String startTime, String endTime) {
        super(taskName);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public String getIcon() {
        return ICON;
    }

    @Override
    public void printTask() {
        String taskDetails = this.getTaskName() + " (from: " + startTime + " to: " + endTime + ")";
        System.out.printf("[%s][%s] %s\n", getIcon(), this.getStatusIcon(), taskDetails);
    }
}
