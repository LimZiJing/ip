package twitchchat.tasks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import twitchchat.exceptions.TwitchChatInvalidTaskIdException;
import twitchchat.storage.TaskStorage;

public class TaskList {

    private static final int MAX_TASKS = 100;

    private final Task[] tasks;
    private final TaskStorage storage;
    private int taskCount;

    public TaskList() {
        this.tasks = new Task[MAX_TASKS];
        this.storage = new TaskStorage();
        this.taskCount = 0;
    }

    public void addTask(Task task) {
        tasks[taskCount] = task;
        taskCount++;
        saveTasks();
    }

    /**
     * Returns the task with the specified one-based task ID.
     *
     * @param taskId one-based ID of the task to retrieve
     * @return task associated with the specified ID
     * @throws TwitchChatInvalidTaskIdException if the ID is outside the task list range
     */
    public Task getTask(int taskId) {
        if (taskId < 1 || taskId > taskCount) {
            throw new TwitchChatInvalidTaskIdException("Invalid task ID");
        }
        return tasks[taskId - 1]; // Convert 1-indexed taskId to 0-index for accessing task
    }

    public int getTaskCount() {
        return taskCount;
    }

    /**
     * Marks the specified task as done and saves the updated task list.
     *
     * @param taskId one-based ID of the task to mark
     * @return the updated task
     * @throws TwitchChatInvalidTaskIdException if the ID is outside the task list range
     */
    public Task markTask(int taskId) {
        Task task = getTask(taskId);
        task.markAsDone();
        saveTasks();
        return task;
    }

    /**
     * Marks the specified task as not done and saves the updated task list.
     *
     * @param taskId one-based ID of the task to unmark
     * @return the updated task
     * @throws TwitchChatInvalidTaskIdException if the ID is outside the task list range
     */
    public Task unmarkTask(int taskId) {
        Task task = getTask(taskId);
        task.markAsNotDone();
        saveTasks();
        return task;
    }

    public void printTasks() {
        for (int i = 0; i < taskCount; i++) {
            System.out.printf("%d.", i + 1);
            tasks[i].printTask();
        }
    }

    private void saveTasks() {
        List<Task> currentTasks = new ArrayList<>(Arrays.asList(tasks).subList(0, taskCount));
        storage.saveTasks(currentTasks);
    }
}
