package seedu.address.model.person.exceptions;

/**
 * Signals that the operation will result in trying to strip captaincy from someone who is already not a captain.
 */
public class AlreadyNotCaptainException extends RuntimeException {
    public AlreadyNotCaptainException() {
        super("Person already not a captain");
    }
}
