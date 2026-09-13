package twitchchat.storage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import twitchchat.exceptions.TwitchChatStorageException;
import twitchchat.tasks.Deadline;
import twitchchat.tasks.Event;
import twitchchat.tasks.Task;

/**
 * Writes task data to the chatbot's save file.
 *
 * The save file path is relative to the project root. Tasks are serialized
 * into a simple line-based format before being written to the file.
 */
public class TaskStorage {

    private static final Path TASK_FILE_PATH = Path.of("data", "twitchchat.txt");

    /**
     * Rewrites the save file with the current tasks.
     *
     * @param tasks tasks to serialize and write
     * @throws TwitchChatStorageException if the tasks cannot be written
     */
    public void saveTasks(List<Task> tasks) {
        try {
            Files.createDirectories(TASK_FILE_PATH.getParent());
            List<String> taskLines = tasks.stream()
                    .map(this::serializeTask)
                    .toList();
            Files.write(TASK_FILE_PATH, taskLines, StandardCharsets.UTF_8);
        } catch (IOException exception) {
            throw new TwitchChatStorageException(exception);
        }
    }

    private String serializeTask(Task task) {
        String status = task.isDone() ? "1" : "0";
        if (task instanceof Deadline deadline) {
            return String.format("DEADLINE | %s | %s | %s", status, task.getTaskName(), deadline.getEndTime());
        }
        if (task instanceof Event event) {
            return String.format("EVENT | %s | %s | %s | %s", status, task.getTaskName(),
                    event.getStartTime(), event.getEndTime());
        }
        return String.format("TODO | %s | %s", status, task.getTaskName());
    }
}
