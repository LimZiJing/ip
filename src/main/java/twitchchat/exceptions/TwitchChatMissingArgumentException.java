package twitchchat.exceptions;

public class TwitchChatMissingArgumentException extends RuntimeException {
    public TwitchChatMissingArgumentException(String message) {
        super(message);
    }
}
