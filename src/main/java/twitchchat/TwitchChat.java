package twitchchat;

import java.util.List;

import twitchchat.commands.Command;
import twitchchat.exceptions.TwitchChatCommandException;
import twitchchat.exceptions.TwitchChatStorageException;
import twitchchat.storage.Storage;
import twitchchat.tasks.Task;
import twitchchat.tasks.TaskList;

public class TwitchChat {

    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;

    /**
     * Creates the application using the specified task file.
     *
     * @param filePath path to the task file
     */
    public TwitchChat(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        List<Task> loadedTasks;
        try {
            loadedTasks = storage.load();
        } catch (TwitchChatStorageException exception) {
            ui.showLoadingError();
            loadedTasks = List.of();
        }
        tasks = new TaskList(loadedTasks);
    }

    /**
     * Runs the command loop.
     */
    public void run() {
        InputParser parser = new InputParser();
        CommandHandler commandHandler = new CommandHandler(tasks, ui, storage);
        ui.showWelcome();

        while (true) {
            String userInput = ui.readCommand();
            ui.showLine();

            try {
                Command command = parser.parse(userInput);
                if (!commandHandler.executeCommand(command)) {
                    return;
                }
            } catch (TwitchChatCommandException e) {
                ui.showError(e.getMessage());
            }

            ui.showLine();
        }
    }

    public static void main(String[] args) {
        new TwitchChat("data/tasks.txt").run();
    }

}
