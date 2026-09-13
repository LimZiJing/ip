package twitchchat.tasks;

import java.util.ArrayList;
import java.util.List;

import twitchchat.exceptions.TwitchChatInvalidTaskIdException;
import twitchchat.storage.TaskStorage;

public class TaskList {

    private final ArrayList<Task> tasks;
    private final TaskStorage storage;

    public TaskList() {
        this.tasks = new ArrayList<>();
        this.storage = new TaskStorage();
        List<Task> savedTasks = storage.loadTasks();
        tasks.addAll(savedTasks);
    }

    public void addTask(Task task) {
        tasks.add(task);
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
        if (taskId < 1 || taskId > tasks.size()) {
            throw new TwitchChatInvalidTaskIdException("Invalid task ID");
        }
        return tasks.get(taskId - 1); // 1-indexed to 0-indexed
    }

    public int getTaskCount() {
        return tasks.size();
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
        saveTasks();
    }

    private void saveTasks() {
        storage.saveTasks(this.tasks);
    }
}
