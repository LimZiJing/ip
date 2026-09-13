package twitchchat.exceptions;

/**
 * Represents an error while saving TwitchChat data to disk.
 */
public class TwitchChatStorageException extends RuntimeException {

    /**
     * Creates a storage exception with the specified cause.
     *
     * @param cause underlying file-system error
     */
    public TwitchChatStorageException(Exception cause) {
        super("Unable to save tasks to disk", cause);
    }
}
