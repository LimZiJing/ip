package twitchchat;

import twitchchat.commands.Command;
import twitchchat.commands.CommandType;

// converts only raw String to command
public class InputParser {
    public Command parse(String input){
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

        if (trimmedInput.startsWith("todo ")) {
            return parseTodo(trimmedInput);
        }

        if (trimmedInput.startsWith("deadline ")) {
            return parseDeadline(trimmedInput);
        }

        if (trimmedInput.startsWith("event ")) {
            return parseEvent(trimmedInput);
        }

        if (trimmedInput.startsWith("mark ")) {
            return parseMark(trimmedInput, CommandType.MARK);
        }

        if (trimmedInput.startsWith("unmark ")) {
            return parseMark(trimmedInput, CommandType.UNMARK);
        }

        return new Command("Unknown command");
    }

    private Command parseDeadline(String input){
        // beginIndex = 9 for "Deadline" of length 8
        String[] arguments = input.substring(9).split(" /by ", 2);
        return new Command(CommandType.DEADLINE, arguments);
    }

    private Command parseEvent(String input){
        // beginIndex = 6 for "Event" of length 5
        // splits arguments into 2 parts, event and part containing /from and /to
        String[] eventArguments = input.substring(6).split(" /from ", 2);
        // splits the second part containing /from and /to, and split those separately
        String[] timeArguments = eventArguments[1].split("/to ", 2);
        return new Command(CommandType.DEADLINE, new String[]{eventArguments[0], timeArguments[0], timeArguments[1]});
    }

    private Command parseTodo(String input) {
        // beginIndex = 5, command type length 4
        String todo = input.substring(5);
        return new Command(CommandType.TODO, new String[]{todo});
    }

    private Command parseMark(String input, CommandType commandType) {
        // gets the taskId depending on CommandType by removing command name using their length of characters
        String taskIdText = input.substring((commandType == CommandType.MARK) ? 5 : 7).trim();
        return new Command(commandType, new String[]{taskIdText});
    }
}
