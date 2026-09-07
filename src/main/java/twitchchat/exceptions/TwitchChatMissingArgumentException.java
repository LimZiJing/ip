package twitchchat.exceptions;

public class TwitchChatMissingArgumentException extends TwitchChatCommandException {
    public TwitchChatMissingArgumentException(String message) {
        super(message);
    }
}
