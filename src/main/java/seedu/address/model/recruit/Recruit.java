package seedu.address.model.recruit;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonBlankString;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
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
    private final UUID id;
    private final TreeSet<Name> names;

    // Data fields
    private final TreeSet<Phone> phones;
    private final TreeSet<Email> emails;
    private final TreeSet<Address> addresses;
    private final Description description;
    private final TreeSet<Tag> tags;

    private Recruit(Builder builder) {
        this.id = builder.id;
        this.names = builder.names;
        this.phones = builder.phones;
        this.emails = builder.emails;
        this.addresses = builder.addresses;
        this.description = builder.description;
        this.tags = builder.tags;
        this.isArchived = builder.isArchived;
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
    public Recruit archive() {
        return new Recruit.Builder(this).setArchivalState(true).build();
    }

    /**
     * Sets the {@code Recruit} as "not archived". This is the default state.
     */
    public Recruit unarchive() {
        return new Recruit.Builder(this).setArchivalState(false).build();
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

        private boolean isArchived;

        /**
         * Constructor for Builder
         */
        public Builder() {
            this.id = UUID.randomUUID();
            this.names = new TreeSet<>();
            this.phones = new TreeSet<>();
            this.addresses = new TreeSet<>();
            this.emails = new TreeSet<>();
            this.description = Description.createEmptyDescription();
            this.tags = new TreeSet<>();
            this.isArchived = false;
        }

        /**
         * Constructor for Builder that builds upon an exsiting Recruit
         */
        public Builder(Recruit recruit) {
            this.id = recruit.id;
            this.names = new TreeSet<>(recruit.names);
            this.phones = new TreeSet<>(recruit.phones);
            this.addresses = new TreeSet<>(recruit.addresses);
            this.emails = new TreeSet<>(recruit.emails);
            this.description = new Description(recruit.description);
            this.tags = new TreeSet<>(recruit.tags);
            this.isArchived = recruit.isArchived;
        }

        /**
         * Copy constructor for Builder
         *
         * @param toCopy
         */
        public Builder(Builder toCopy) {
            this.id = toCopy.id;
            this.names = new TreeSet<>(toCopy.names);
            this.phones = new TreeSet<>(toCopy.phones);
            this.emails = new TreeSet<>(toCopy.emails);
            this.addresses = new TreeSet<>(toCopy.addresses);
            this.description = new Description(toCopy.description);
            this.tags = new TreeSet<>(toCopy.tags);
            this.isArchived = toCopy.isArchived;
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

        public Builder setArchivalState(boolean isArchived) {
            this.isArchived = isArchived;
        }

        public Builder appendNames(List<Name> names) {
            this.names.addAll(names);
            return this;
        }

        public Builder appendPhones(List<Phone> phones) {
            this.phones.addAll(phones);
            return this;
        }

        public Builder appendEmails(List<Email> emails) {
            this.emails.addAll(emails);
            return this;
        }

        public Builder appendAddresses(List<Address> addresses) {
            this.addresses.addAll(addresses);
            return this;
        }

        public Builder appendTags(Set<Tag> tags) {
            this.tags.addAll(tags);
            return this;
        }

        /**
         * Builds a {@code Recruit} using the attributes specified in the {@code Builder}
         * @return the Recruit with matching {@code Builder} attributes
         */
        public Recruit build() {
            requireNonNull(names);
            requireAllNonBlankString(names.stream().map(Name::toString).toList());

            return new Recruit(this);
        }
    }
}
