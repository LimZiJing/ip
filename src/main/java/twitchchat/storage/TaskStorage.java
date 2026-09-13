package twitchchat.storage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Writes task data to the chatbot's save file.
 *
 * The save file path is relative to the project root. Task serialization and
 * automatic saving when the task list changes will be added in later steps.
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
}
