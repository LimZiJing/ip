package twitchchat.commands;

public class Command {
    private final CommandType type;
    private final String[] arguments;
    private final int id;
    public Command(CommandType type, String[] arguments) {
        this.type = type;
        this.arguments = arguments;
        this.id = 0;
    }

    public Command(CommandType type, int id){
        this.type = type;
        this.arguments = new String[0];
        this.id = id;
    }

    public CommandType getType(){
        return this.type;
    }

    public String[] getArguments() {
        return arguments.clone();
    }

}
