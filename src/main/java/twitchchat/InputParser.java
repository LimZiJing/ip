package twitchchat;

import twitchchat.commands.Command;
import twitchchat.commands.CommandType;

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

        return new Command("Unknown command");
    }

    private Command parseDeadline(String input){
        // beginIndex = 9 for "Deadline" of length 8
        String[] arguments = input.substring(DEADLINE_PREFIX.length()).split(" /by ", 2);
        return new Command(CommandType.DEADLINE, arguments);
    }

    private Command parseEvent(String input){
        // beginIndex = 6 for "Event" of length 5
        // splits arguments into 2 parts, event and part containing /from and /to
        String[] eventArguments = input.substring(EVENT_PREFIX.length()).split(" /from ", 2);
        // splits the second part containing /from and /to, and split those separately
        String[] timeArguments = eventArguments[1].split("/to ", 2);
        return new Command(CommandType.DEADLINE, new String[]{eventArguments[0], timeArguments[0], timeArguments[1]});
    }

    private Command parseTodo(String input) {
        // beginIndex = 5, command type length 4
        String todo = input.substring(TODO_PREFIX.length());
        return new Command(CommandType.TODO, new String[]{todo});
    }

    private Command parseMark(String input, CommandType commandType) {
        // gets the taskId depending on CommandType by removing command name using their length of characters
        String commandPrefix = commandType == CommandType.MARK ? MARK_PREFIX : UNMARK_PREFIX;
        String taskIdText = input.substring(commandPrefix.length()).trim();
        return new Command(commandType, new String[]{taskIdText});
    }
}
