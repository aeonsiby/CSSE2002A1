package game.exceptions;

/**
 * Exception thrown when an object attempts to move beyond the game boundaries.
 */
public class BoundaryExceededException extends RuntimeException {

    /**
     * Creates a new boundary exceeded exception with the specified message.
     *
     * @param message outlines how exactly boundary was crossed
     */
    public BoundaryExceededException(String message) {
        super(message);
    }
}
