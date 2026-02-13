/**
 * Represents an exception specific to Porus chatbot.
 */
package porus;

public class PorusException extends Exception {

    /**
     * Constructs a PorusException with a message.
     *
     * @param message Error message
     */
    public PorusException(String message) {
        super(message);
    }
}