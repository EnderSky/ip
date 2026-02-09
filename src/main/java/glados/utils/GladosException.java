package glados.utils;

/**
 * Custom exception class for Glados application.
 */
public class GladosException extends RuntimeException {

    /**
     * Constructs a new GladosException with the specified detail message.
     *
     * @param message the detail message.
     */
    public GladosException(String message) {
        super(message);
    }
}
