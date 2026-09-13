package twitchchat.storage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
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
        Path temporaryFile = null;
        try {
            if (tasks == null) {
                throw new IllegalArgumentException("Task list cannot be null");
            }
            List<String> taskLines = tasks.stream()
                    .map(this::serializeTask)
                    .toList();
            Files.createDirectories(TASK_FILE_PATH.getParent());
            temporaryFile = Files.createTempFile(TASK_FILE_PATH.getParent(), "twitchchat", ".tmp");
            Files.write(temporaryFile, taskLines, StandardCharsets.UTF_8);
            try {
                Files.move(temporaryFile, TASK_FILE_PATH, StandardCopyOption.ATOMIC_MOVE,
                        StandardCopyOption.REPLACE_EXISTING);
            } catch (AtomicMoveNotSupportedException exception) {
                Files.move(temporaryFile, TASK_FILE_PATH, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException | IllegalArgumentException exception) {
            throw new TwitchChatStorageException("Unable to save tasks to disk", exception);
        } finally {
            if (temporaryFile != null) {
                try {
                    Files.deleteIfExists(temporaryFile);
                } catch (IOException exception) {
                    // The completed save is still valid if temporary-file cleanup fails.
                }
            }
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
            List<String> lines = Files.readAllLines(TASK_FILE_PATH, StandardCharsets.UTF_8);
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                if (!line.isBlank()) {
                    try {
                        tasks.add(deserializeTask(line));
                    } catch (IllegalArgumentException exception) {
                        System.err.printf("Warning: Skipping invalid task record on line %d.%n", i + 1);
                    }
                }
            }
            return tasks;
        } catch (IOException exception) {
            throw new TwitchChatStorageException("Unable to load tasks from disk", exception);
        }
    }

    private String serializeTask(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task cannot be null");
        }
        String status = task.isDone() ? "1" : "0";
        if (task instanceof Deadline deadline) {
            return String.format("DEADLINE | %s | %s | %s", status, escape(task.getTaskName()),
                    escape(deadline.getEndTime()));
        }
        if (task instanceof Event event) {
            return String.format("EVENT | %s | %s | %s | %s", status, escape(task.getTaskName()),
                    escape(event.getStartTime()), escape(event.getEndTime()));
        }
        return String.format("TODO | %s | %s", status, escape(task.getTaskName()));
    }

    private Task deserializeTask(String line) {
        String[] fields = splitFields(line);
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

    private String[] splitFields(String line) {
        List<String> fields = new ArrayList<>();
        StringBuilder field = new StringBuilder();
        boolean isEscaped = false;
        for (char character : line.toCharArray()) {
            if (isEscaped) {
                field.append(unescape(character));
                isEscaped = false;
            } else if (character == '\\') {
                isEscaped = true;
            } else if (character == '|') {
                fields.add(field.toString().trim());
                field.setLength(0);
            } else {
                field.append(character);
            }
        }
        if (isEscaped) {
            throw new IllegalArgumentException("Unfinished escape sequence");
        }
        fields.add(field.toString().trim());
        return fields.toArray(String[]::new);
    }

    private String escape(String value) {
        return value.replace("\\", "\\\\")
                .replace("|", "\\|")
                .replace("\r", "\\r")
                .replace("\n", "\\n");
    }

    private char unescape(char character) {
        switch (character) {
        case '|':
        case '\\':
            return character;
        case 'r':
            return '\r';
        case 'n':
            return '\n';
        default:
            return character;
        }
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
