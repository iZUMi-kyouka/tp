package seedu.address.model.recruit.data;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name extends Data implements Comparable<Name> {

    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain Unicode letter and numeric digit characters, spaces, and allowed symbols"
            + " (comma, doublequote, apostrophe, hyphen, at, and forward slash), and it must not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{L}\\p{N}\\-,/\"'@][\\p{L}\\p{N}\\-,/\"'@ ]*";

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Name(String name) {
        super(validateName(name));
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {

        return test.matches(VALIDATION_REGEX);
    }

    private static String validateName(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        return name;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Name)) {
            return false;
        }

        Name otherName = (Name) other;
        return this.value.equals(otherName.value);
    }

    @Override
    public int compareTo(Name o) {
        return this.value.compareTo(o.value);
    }
}
