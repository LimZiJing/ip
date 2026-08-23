package twitchchat;

/** Stores and displays the tasks entered by the user. */
public class TaskList {
    /** Maximum number of tasks supported by this application. */
    private static final int MAX_TASKS = 100;

    private Task[] tasks;
    private int taskCount;

    /** Creates an empty task list. */
    public TaskList() {
        this.tasks = new Task[MAX_TASKS];
        this.taskCount = 0;
    }

    /** Adds a task with the specified name to this list. */
    public void addTask(String taskName) {
        tasks[taskCount] = new Task(taskName);
        taskCount++;
    }

    /** Returns the task at the specified one-based task ID. */
    public Task getTask(int taskId) {
        return tasks[taskId - 1]; // Convert 1-indexed taskId to 0-index for accessing task
    }

    /** Returns the number of tasks currently stored. */
    public int getTaskCount() {
        return taskCount;
    }

    /** Prints all tasks with their one-based IDs. */
    public void printTasks() {
        for (int i = 0; i < taskCount; i++) {
            System.out.printf("%d.", i + 1);
            tasks[i].printTask();
        }
    }
}
