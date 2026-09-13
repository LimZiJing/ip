package twitchchat.storage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
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
     * Writes the supplied task lines to the save file, replacing its previous contents.
     * Creates the data directory when it does not already exist.
     *
     * @param taskLines serialized task lines to write
     * @throws IOException if the directory cannot be created or the file cannot be written
     */
    public void saveTasks(List<String> taskLines) throws IOException {
        Files.createDirectories(TASK_FILE_PATH.getParent());
        Files.write(TASK_FILE_PATH, taskLines, StandardCharsets.UTF_8);
    }

    /**
     * Appends one task to the save file.
     *
     * @param task task to store
     * @throws TwitchChatStorageException if the task cannot be written
     */
    public void storeTask(Task task) {
        try {
            Files.createDirectories(TASK_FILE_PATH.getParent());
            Files.writeString(TASK_FILE_PATH, serializeTask(task) + System.lineSeparator(),
                    StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException exception) {
            throw new TwitchChatStorageException(exception);
        }
    }

    private String serializeTask(Task task) {
        String status = task.getStatusIcon().equals("X") ? "1" : "0";
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
