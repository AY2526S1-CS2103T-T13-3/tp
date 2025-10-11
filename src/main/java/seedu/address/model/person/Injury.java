package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's injury status in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidInjuryName(String)}
 */
public class Injury {

    public static final String MESSAGE_CONSTRAINTS =
            "Injuries should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the injury must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String injuryName;

    /**
     * Constructs a {@code Injury}.
     *
     * @param name A valid injury name.
     */
    public Injury(String name) {
        requireNonNull(name);
        checkArgument(isValidInjuryName(name), MESSAGE_CONSTRAINTS);
        this.injuryName = name;
    }

    /**
     * Returns true if a given string is a valid injury name.
     */
    public static boolean isValidInjuryName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return this.injuryName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Injury)) {
            return false;
        }
        Injury otherInjury = (Injury) other;
        return this.injuryName.equalsIgnoreCase(otherInjury.injuryName);
    }

    @Override
    public int hashCode() {
        return this.injuryName.toLowerCase().hashCode();
    }

    public String getInjuryName() {
        return injuryName;
    }
}
