package seedu.address.model.recruit.data;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the description of a Recruit in the address book.
 * Guarantees immutability.
 */
public class Description extends Data {

    public static final String MESSAGE_CONSTRAINTS =
            "Descriptions can take any values, and can be blank.";

    /*
     * Descriptions can take any character.
     * It is currently unconstrained, but this allows future validation rules.
     */
    public static final String VALIDATION_REGEX = "(?s).*";

    // Singleton instance of an "empty" Description.
    private static final Description EMPTY_DESCRIPTION = new EmptyDescription();

    /**
     * Constructs a {@code Description}.
     *
     * @param description A description for the Recruit.
     */
    public Description(String description) {
        super(validateDescription(description));
    }

    /**
     * Copy constructor for {@code Description}.
     *
     * @param other The {@code Description} to copy.
     */
    public Description(Description other) {
        super(other.value);
    }

    /**
     * Returns a shared immutable empty {@code Description}.
     */
    public static Description createEmptyDescription() {
        return EMPTY_DESCRIPTION;
    }

    /**
     * Validates that the given description is non-null and matches the regex.
     */
    private static String validateDescription(String test) {
        requireNonNull(test);
        checkArgument(isValidDescription(test), MESSAGE_CONSTRAINTS);
        return test;
    }

    /**
     * Returns true if a given string is a valid description.
     */
    public static boolean isValidDescription(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns a new {@code Description} that is the result of appending
     * the other description to this one.
     *
     * @param otherDescription The description to append.
     * @return The combined description.
     */
    public Description appendDescription(Description otherDescription) {
        requireNonNull(otherDescription);

        if (otherDescription.isEmptyDescription()) {
            return new Description(this);
        }

        String combined = (this.value.strip() + " " + otherDescription.value.strip()).strip();
        return new Description(combined);
    }

    /**
     * Returns {@code true} if this description represents an empty description.
     */
    public boolean isEmptyDescription() {
        return this == EMPTY_DESCRIPTION;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Description
                && value.equals(((Description) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    /**
     * Represents an empty (placeholder) description.
     * Singleton instance returned via {@link #createEmptyDescription()}.
     */
    private static final class EmptyDescription extends Description {
        private static final String EMPTY_VALUE = "";

        private EmptyDescription() {
            super(EMPTY_VALUE);
        }

        @Override
        public Description appendDescription(Description otherDescription) {
            requireNonNull(otherDescription);
            // Just return the other description; empty adds nothing.
            return new Description(otherDescription.value.strip());
        }

        @Override
        public String toString() {
            return "";
        }
    }
}
