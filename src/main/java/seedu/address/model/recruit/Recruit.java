package seedu.address.model.recruit;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonBlankString;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Recruit {

    private boolean isArchived;

    // Identity fields
    private UUID id;
    private TreeSet<Name> names;

    // Data fields
    private TreeSet<Phone> phones;
    private TreeSet<Email> emails;
    private TreeSet<Address> addresses;
    private Description description;
    private TreeSet<Tag> tags;

    private Recruit(Builder builder) {
        this.id = builder.id;
        this.names = builder.names;
        this.phones = builder.phones;
        this.emails = builder.emails;
        this.addresses = builder.addresses;
        this.description = builder.description;
        this.tags = builder.tags;
        this.isArchived = false;
    }

    public UUID getID() {
        return id;
    }

    public Name getName() {
        return names.first();
    }

    public List<Name> getNames() {
        return this.names.stream().toList();
    }

    public Phone getPhone() {
        return phones.first();
    }

    public List<Phone> getPhones() {
        return this.phones.stream().toList();
    }

    public Email getEmail() {
        return emails.first();
    }

    public List<Email> getEmails() {
        return this.emails.stream().toList();
    }

    public Description getDescription() {
        return this.description;
    }

    public Address getAddress() {
        return addresses.first();
    }

    public List<Address> getAddresses() {
        return this.addresses.stream().toList();
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons have the same fields except id.
     * Returns boolean representing whether recruit entry is archived or not
     */
    public boolean isArchived() {
        return this.isArchived;
    }

    /**
     * Sets the {@code Recruit} as "archived".
     */
    public void archive() {
        this.isArchived = true;
    }

    /**
     * Sets the {@code Recruit} as "not archived". This is the default state.
     */
    public void unarchive() {
        this.isArchived = false;
    }

    /**
     * Returns true if both persons have the same fields except id.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameRecruit(Recruit otherRecruit) {
        if (otherRecruit == this) {
            return true;
        }

        return otherRecruit != null
                && names.equals(otherRecruit.names)
                && phones.equals(otherRecruit.phones)
                && emails.equals(otherRecruit.emails)
                && addresses.equals(otherRecruit.addresses)
                && tags.equals(otherRecruit.tags);
    }

    /**
     * Returns true if both recruits have the same id and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Recruit)) {
            return false;
        }

        Recruit otherRecruit = (Recruit) other;
        return id.equals(otherRecruit.id)
                && names.equals(otherRecruit.names)
                && phones.equals(otherRecruit.phones)
                && emails.equals(otherRecruit.emails)
                && addresses.equals(otherRecruit.addresses)
                && description.equals(otherRecruit.description)
                && tags.equals(otherRecruit.tags)
                && this.isArchived() == otherRecruit.isArchived();
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(names, phones, emails, addresses, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("id", id)
                .add("names", names)
                .add("phones", phones)
                .add("emails", emails)
                .add("addresses", addresses)
                .add("tags", tags)
                .toString();
    }

    /**
     * Represents a Builder class to build a Recruit.
     * Guarantees:
     * <ul>
     *     <li>A new {@code Recruit} with valid fields.</li>
     *     <li>The created{@code Recruit} has to have at least 1 name</li>
     *     <li>If a UUID is not provided, a new one is randomly generated</li>
     * </ul>
     */
    public static class Builder {
        private UUID id;
        private TreeSet<Name> names;
        private TreeSet<Phone> phones;
        private TreeSet<Email> emails;
        private TreeSet<Address> addresses;
        private Description description;
        private TreeSet<Tag> tags;

        /**
         * Copy constructor for Builder
         *
         * @param toCopy
         */
        public Builder(Builder toCopy) {
            this.id = toCopy.id; // safe to copy as UUID is immutable
            this.names = new TreeSet<>(toCopy.names);
            this.phones = new TreeSet<>(toCopy.phones);
            this.emails = new TreeSet<>(toCopy.emails);
            this.addresses = new TreeSet<>(toCopy.addresses);
            this.description = new Description(toCopy.description);
            this.tags = new TreeSet<>(toCopy.tags);
        }

        public Builder setUuid(UUID id) {
            this.id = id;
            return this;
        }

        public Builder setNames(List<Name> names) {
            this.names = new TreeSet<>(names);
            return this;
        }

        public Builder setPhones(List<Phone> phones) {
            this.phones = new TreeSet<>(phones);
            return this;
        }

        public Builder setEmails(List<Email> emails) {
            this.emails = new TreeSet<>(emails);
            return this;
        }

        public Builder setAddresses(List<Address> addresses) {
            this.addresses = new TreeSet<>(addresses);
            return this;
        }

        public Builder setDescription(Description description) {
            this.description = new Description(description);
            return this;
        }

        public Builder setTags(Set<Tag> tags) {
            this.tags = new TreeSet<>(tags);
            return this;
        }

        /**
         * Builds a {@code Recruit} using the attributes specified in the {@code Builder}
         * @return the Recruit with matching {@code Builder} attributes
         */
        public Recruit build() {
            requireNonNull(names);
            requireAllNonBlankString(names.stream().map(Name::toString).toList());

            Builder clone = new Builder(this);

            clone.id = Optional.ofNullable(id).orElse(UUID.randomUUID());
            clone.phones = Optional.ofNullable(phones).orElse(new TreeSet<>());
            clone.addresses = Optional.ofNullable(addresses).orElse(new TreeSet<>());
            clone.emails = Optional.ofNullable(emails).orElse(new TreeSet<>());
            clone.description = Optional.ofNullable(description).orElse(Description.createEmptyDescription());
            clone.tags = Optional.ofNullable(tags).orElse(new TreeSet<>());

            return new Recruit(clone);
        }
    }
}
