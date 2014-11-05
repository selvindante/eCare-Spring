package ru.tsystems.tsproject.ecare;

/**
 * Class of own application exception.
 *
 * @author Starostin Konstantin
 */
public class ECareException extends RuntimeException {

    /*Field of exception message*/
    String message;

    /**
     * Constructor for transmitting a message to the class.
     *
     * @param message string message.
     */
    public ECareException(String message) {
        super(message);
        this.message = message;
    }

    /**
     * Constructor for transmitting a message and exception to the class.
     * @param message string message.
     * @param e exception.
     */
    public ECareException(String message, Throwable e) {
        super(message, e);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
