package twitchchat;

import twitchchat.commands.Command;
import twitchchat.commands.CommandType;
import twitchchat.exceptions.TwitchChatInvalidCommandException;
import twitchchat.exceptions.TwitchChatInvalidTaskIdException;
import twitchchat.exceptions.TwitchChatMissingArgumentException;
import twitchchat.exceptions.TwitchChatTooManyArgumentsException;

// converts only raw String to command
public class InputParser {
    private static final String TODO_PREFIX = "todo ";
    private static final String DEADLINE_PREFIX = "deadline ";
    private static final String EVENT_PREFIX = "event ";
    private static final String MARK_PREFIX = "mark ";
    private static final String UNMARK_PREFIX = "unmark ";

    public Command parse(String input) {
        // remove whitespace
        String trimmedInput = input.trim();

        if (trimmedInput.equals("bye")) {
            return new Command(CommandType.BYE, new String[0]);
        }

        if (trimmedInput.equals("hi")) {
            return new Command(CommandType.HI, new String[0]);
        }

        if (trimmedInput.equals("list")) {
            return new Command(CommandType.LIST, new String[0]);
        }

        if (trimmedInput.equals("todo")) {
            throw new TwitchChatMissingArgumentException("Missing task description");
        }

        if (trimmedInput.equals("deadline")) {
            throw new TwitchChatMissingArgumentException("Missing task name and /by date");
        }

        if (trimmedInput.equals("event")) {
            throw new TwitchChatMissingArgumentException("Missing event name, /from date, and /to date");
        }

        if (trimmedInput.equals("mark") || trimmedInput.equals("unmark")) {
            throw new TwitchChatMissingArgumentException("No task ID specified");
        }

        if (trimmedInput.startsWith(TODO_PREFIX)) {
            return parseTodo(trimmedInput);
        }

        if (trimmedInput.startsWith(DEADLINE_PREFIX)) {
            return parseDeadline(trimmedInput);
        }

        if (trimmedInput.startsWith(EVENT_PREFIX)) {
            return parseEvent(trimmedInput);
        }

        if (trimmedInput.startsWith(MARK_PREFIX)) {
            return parseMark(trimmedInput, CommandType.MARK);
        }

        if (trimmedInput.startsWith(UNMARK_PREFIX)) {
            return parseMark(trimmedInput, CommandType.UNMARK);
        }

        throw new TwitchChatInvalidCommandException("Invalid command");
    }

    private Command parseDeadline(String input) {
        String[] arguments = input.substring(DEADLINE_PREFIX.length()).split(" /by ", -1);
        if (arguments.length < 2 || arguments[0].trim().isEmpty()) {
            throw new TwitchChatMissingArgumentException("Missing task name or /by date");
        }
        if (arguments.length > 2) {
            throw new TwitchChatTooManyArgumentsException("Too many /by dates");
        }
        if (arguments[1].trim().isEmpty()) {
            throw new TwitchChatMissingArgumentException("Missing /by date");
        }
        return new Command(CommandType.DEADLINE,
                new String[]{arguments[0].trim(), arguments[1].trim()});
    }

    private Command parseEvent(String input) {
        // splits arguments into 2 parts, event and part containing /from and /to
        String[] eventArguments = input.substring(EVENT_PREFIX.length()).split(" /from ", -1);
        if (eventArguments.length < 2) {
            throw new TwitchChatMissingArgumentException("Missing /from date");
        }
        if (eventArguments.length > 2) {
            throw new TwitchChatTooManyArgumentsException("Too many /from dates");
        }
        // splits the second part containing /from and /to, and split those separately
        String[] timeArguments = eventArguments[1].split(" /to ", -1);
        if (timeArguments.length < 2) {
            throw new TwitchChatMissingArgumentException("Missing /to date");
        }
        if (timeArguments.length > 2) {
            throw new TwitchChatTooManyArgumentsException("Too many /to dates");
        }
        if (eventArguments[0].trim().isEmpty()) {
            throw new TwitchChatMissingArgumentException("Missing event name or /from date");
        }
        if (timeArguments[0].trim().isEmpty()) {
            throw new TwitchChatMissingArgumentException("Missing /from date");
        }
        if (timeArguments[1].trim().isEmpty()) {
            throw new TwitchChatMissingArgumentException("Missing /to date");
        }
        return new Command(CommandType.EVENT,
                new String[]{eventArguments[0].trim(), timeArguments[0].trim(), timeArguments[1].trim()});
    }

    private Command parseTodo(String input) {
        String todo = input.substring(TODO_PREFIX.length()).trim();
        if (todo.isEmpty()) {
            throw new TwitchChatMissingArgumentException("Missing task description");
        }
        return new Command(CommandType.TODO, new String[]{todo});
    }

    private Command parseMark(String input, CommandType commandType) {
        // get mark command type
        String commandPrefix = (commandType == CommandType.MARK) ? MARK_PREFIX : UNMARK_PREFIX;
        String taskIdText = input.substring(commandPrefix.length()).trim();
        if (taskIdText.isEmpty()) {
            throw new TwitchChatMissingArgumentException("No task ID specified");
        }
        if (taskIdText.trim().split("\\s+").length > 1) {
            throw new TwitchChatTooManyArgumentsException("Too many task IDs");
        }
        try {
            int taskId = Integer.parseInt(taskIdText);
            return new Command(commandType, taskId);
        } catch (NumberFormatException exception) {
            throw new TwitchChatInvalidTaskIdException("Invalid ID, not a number");
        }
    }
}
