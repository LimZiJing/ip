package twitchchat.tasks;

import twitchchat.exceptions.TwitchChatInvalidTaskIdException;
import java.util.ArrayList;

public class TaskList {

    private static final int MAX_TASKS = 100;
    private final ArrayList<Task> tasks;


    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Returns the task with the specified one-based task ID.
     *
     * @param taskId one-based ID of the task to retrieve
     * @return task associated with the specified ID
     * @throws TwitchChatInvalidTaskIdException if the ID is outside the task list range
     */
    public Task getTask(int taskId) {
        if (taskId < 1 || taskId > tasks.size()) {
            throw new TwitchChatInvalidTaskIdException("Invalid task ID");
        }
        return tasks.get(taskId - 1); // Convert 1-indexed taskId to 0-index for accessing task
    }

    public int getTaskCount() {
        return tasks.size();
    }

    public void printTasks() {

        for (int i = 0; i < tasks.size(); i++) {
            System.out.printf("%d.", i + 1);
            tasks.get(i).printTask();
        }
    }
}
