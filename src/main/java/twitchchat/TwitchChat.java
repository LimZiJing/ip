package twitchchat;

import twitchchat.commands.Command;
import twitchchat.exceptions.TwitchChatCommandException;
import twitchchat.tasks.TaskList;

public class TwitchChat {

    public static void main(String[] args) {
        Ui ui = new Ui();
        InputParser parser = new InputParser();
        TaskList tasks = new TaskList();
        CommandHandler commandHandler = new CommandHandler(tasks, ui);
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

}
