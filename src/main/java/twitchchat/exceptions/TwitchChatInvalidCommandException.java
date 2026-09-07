package twitchchat.exceptions;

public class TwitchChatInvalidCommandException extends RuntimeException {
    public TwitchChatInvalidCommandException(String message) {
        super(message);
    }
}
