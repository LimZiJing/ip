package twitchchat.commands;

public class Command {
    private final CommandType type;
    private final String[] arguments;
    private final String errorMessage;

    public Command(CommandType type, String[] arguments, String errorMessage) {
        this.type = type;
        this.arguments = arguments;
        this.errorMessage = errorMessage;
    }

    public Command(String errorMessage) {
        this.type = CommandType.INVALID;
        this.arguments = new String[0];
        this.errorMessage = errorMessage;
    }

    public CommandType getType(){
        return this.type;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public String[] getArguments() {
        return arguments;
    }
}
