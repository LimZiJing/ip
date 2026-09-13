package twitchchat.storage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import twitchchat.exceptions.TwitchChatStorageException;
import twitchchat.tasks.Deadline;
import twitchchat.tasks.Event;
import twitchchat.tasks.Task;
import twitchchat.tasks.Todo;

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

    /**
     * Loads all saved tasks in their stored order.
     *
     * @return saved tasks, or an empty list if no save file exists
     * @throws TwitchChatStorageException if the save file cannot be read or parsed
     */
    public List<Task> loadTasks() {
        if (Files.notExists(TASK_FILE_PATH)) {
            return new ArrayList<>();
        }

        try {
            List<Task> tasks = new ArrayList<>();
            for (String line : Files.readAllLines(TASK_FILE_PATH, StandardCharsets.UTF_8)) {
                if (!line.isBlank()) {
                    tasks.add(deserializeTask(line));
                }
            }
            return tasks;
        } catch (IOException | IllegalArgumentException exception) {
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

    private Task deserializeTask(String line) {
        String[] fields = line.split("\\s*\\|\\s*", -1);
        if (fields.length < 3) {
            throw new IllegalArgumentException("Invalid task record");
        }

        String taskType = fields[0];
        boolean isDone = parseStatus(fields[1]);
        Task task;
        switch (taskType) {
        case "TODO":
            if (fields.length != 3) {
                throw new IllegalArgumentException("Invalid todo record");
            }
            task = new Todo(fields[2]);
            break;
        case "DEADLINE":
            if (fields.length != 4) {
                throw new IllegalArgumentException("Invalid deadline record");
            }
            task = new Deadline(fields[2], fields[3]);
            break;
        case "EVENT":
            if (fields.length != 5) {
                throw new IllegalArgumentException("Invalid event record");
            }
            task = new Event(fields[2], fields[3], fields[4]);
            break;
        default:
            throw new IllegalArgumentException("Unknown task type");
        }

        if (isDone) {
            task.markAsDone();
        }
        return task;
    }

    private boolean parseStatus(String status) {
        if (status.equals("0")) {
            return false;
        }
        if (status.equals("1")) {
            return true;
        }
        throw new IllegalArgumentException("Invalid task status");
    }
}
