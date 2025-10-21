package seedu.address.model.recruit;

import static java.util.Objects.requireNonNull;

/**
 * Represents the Description of a Recruit in the address book.
 * Guarantees: immutable
 */
public class Description {
    public final String description;

    /**
     * Constructs a {@code Description}.
     *
     * @param description A description for that Recruit.
     */
    public Description(String description) {
        requireNonNull(description);
        this.description = description;
    }


    @Override
    public String toString() {
        return description;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Description)) {
            return false;
        }

        Description otherDescription = (Description) other;
        return description.equals(otherDescription.description);
    }

    @Override
    public int hashCode() {
        return description.hashCode();
    }

}
