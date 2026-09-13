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

    /**
     * Returns the event start time.
     *
     * @return event start time
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * Returns the event end time.
     *
     * @return event end time
     */
    public String getEndTime() {
        return endTime;
    }

    @Override
    public void printTask() {
        String taskDetails = this.getTaskName() + " (from: " + startTime + " to: " + endTime + ")";
        System.out.printf("[%s][%s] %s\n", getIcon(), this.getStatusIcon(), taskDetails);
    }
}
