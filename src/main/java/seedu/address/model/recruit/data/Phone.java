package seedu.address.model.recruit.data;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class Phone extends Data implements Comparable<Phone> {

    public static final String MESSAGE_CONSTRAINTS =
            "Phone numbers should only contain numbers, spaces, and optionally a plus symbol only at the start. "
            + "There must be at least 3 digits.";
    public static final String VALIDATION_REGEX = "^[+]{0,1}([ ]*[\\d]){3,}[ \\d]*$";

    /**
     * Constructs a {@code Phone}.
     *
     * @param phone A valid phone number.
     */
    public Phone(String phone) {
        super(validatePhone(phone));
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidPhone(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    private static String validatePhone(String test) {
        requireNonNull(test);
        checkArgument(isValidPhone(test), MESSAGE_CONSTRAINTS);
        return test;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Phone)) {
            return false;
        }

        Phone otherPhone = (Phone) other;
        return value.equals(otherPhone.value);
    }

    @Override
    public int compareTo(Phone o) {
        return this.value.compareTo(o.value);
    }
}
