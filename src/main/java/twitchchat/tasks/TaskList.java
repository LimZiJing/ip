package twitchchat.tasks;

import twitchchat.exceptions.TwitchChatInvalidTaskIdException;

public class TaskList {

    private static final int MAX_TASKS = 100;

    private final Task[] tasks;
    private int taskCount;

    public TaskList() {
        this.tasks = new Task[MAX_TASKS];
        this.taskCount = 0;
    }

    public void addTask(Task task) {
        tasks[taskCount] = task;
        taskCount++;
    }

    public Task getTask(int taskId) {
        if (taskId < 1 || taskId > taskCount) {
            throw new TwitchChatInvalidTaskIdException("Invalid task ID");
        }
        return tasks[taskId - 1]; // Convert 1-indexed taskId to 0-index for accessing task
    }

    public int getTaskCount() {
        return taskCount;
    }

    public void printTasks() {
        for (int i = 0; i < taskCount; i++) {
            System.out.printf("%d.", i + 1);
            tasks[i].printTask();
        }
    }
}
