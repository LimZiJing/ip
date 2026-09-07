package twitchchat.exceptions;

public class TwitchChatTooManyArgumentsException extends TwitchChatCommandException {
    public TwitchChatTooManyArgumentsException(String message) {
        super(message);
    }
}
