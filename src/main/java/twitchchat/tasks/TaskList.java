package twitchchat.tasks;

import java.util.ArrayList;

import twitchchat.exceptions.TwitchChatInvalidTaskIdException;

public class TaskList {

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
        return tasks.get(taskId - 1); // 1-indexed to 0-indexed
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

    /**
     * Removes the task with the specified one-based task ID.
     *
     * @param taskId one-based ID of the task to remove
     * @throws TwitchChatInvalidTaskIdException if the ID is outside the task list range
     */
    public void removeTask(int taskId) {
        if (taskId < 1 || taskId > tasks.size()) {
            throw new TwitchChatInvalidTaskIdException("Invalid task ID");
        }
        tasks.remove(taskId - 1); // 1-indexed to 0-indexed
    }
}
