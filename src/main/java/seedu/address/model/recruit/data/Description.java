package seedu.address.model.recruit.data;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the description of a Recruit in the address book.
 * Guarantees immutability.
 */
public class Description extends Data {

    public static final String MESSAGE_CONSTRAINTS =
            "Descriptions can take any value, but cannot be blank.";

    /*
     * Descriptions can be composed of any character, but it must not be blank.
     */
    public static final String VALIDATION_REGEX = "(?s).*";

    // Singleton instance of an "empty" Description.
    private static final Description EMPTY_DESCRIPTION = new EmptyDescription();

    /**
     * Constructs a {@code Description}.
     *
     * @param description A description for the Recruit.
     */
    private Description(String description) {
        super(validateDescription(description));
    }

    /**
     * Copy constructor for {@code Description}.
     *
     * @param other The {@code Description} to copy.
     */
    public Description(Description other) {
        super(requireNonNull(other).value);
    }

    /**
     * Factory method for creating a {@code Description}
     * Creates an empty description if the String provided is blank
     *
     * @param description
     * @return
     */
    public static Description createDescription(String description) {
        if (description == null || description.isBlank()) {
            return EMPTY_DESCRIPTION;
        }
        return new Description(description);
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
        return Description.createDescription(combined);
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
            return Description.createDescription(otherDescription.value.strip());
        }

        @Override
        public String toString() {
            return "";
        }
    }
}
