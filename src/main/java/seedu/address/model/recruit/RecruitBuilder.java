package seedu.address.model.recruit;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import java.util.stream.Stream;

import seedu.address.commons.util.RecruitUtil;
import seedu.address.model.recruit.exceptions.InvalidRecruitException;
import seedu.address.model.tag.Tag;

/**
 * Represents a Builder class to build a Recruit.
 * Guarantees:
 * <ul>
 *     <li>A new {@code Recruit} with valid fields.</li>
 *     <li>The created{@code Recruit} has to have at least 1 name</li>
 *     <li>If a UUID is not provided, a new one is randomly generated</li>
 * </ul>
 */
public class RecruitBuilder {

    protected UUID id;

    protected TreeSet<Name> names;
    protected TreeSet<Phone> phones;
    protected TreeSet<Email> emails;
    protected TreeSet<Address> addresses;

    protected Description description;
    protected TreeSet<Tag> tags;

    protected boolean isArchived;

    /**
     * Constructor for Builder
     */
    public RecruitBuilder() {

    }

    /**
     * Constructor for Builder that builds upon an existing Recruit
     */
    public RecruitBuilder(Recruit recruit) {
        this.id = recruit.getID();
        this.names = new TreeSet<>(recruit.getNames());
        this.phones = new TreeSet<>(recruit.getPhones());
        this.addresses = new TreeSet<>(recruit.getAddresses());
        this.emails = new TreeSet<>(recruit.getEmails());
        this.description = new Description(recruit.getDescription());
        this.tags = new TreeSet<>(recruit.getTags());
        this.isArchived = recruit.isArchived();
    }

    /**
     * Copy constructor for Builder
     *
     * @param toCopy The other builder to copy
     */
    public RecruitBuilder(RecruitBuilder toCopy) {
        this.id = toCopy.id;
        this.names = new TreeSet<>(toCopy.names);
        this.phones = new TreeSet<>(toCopy.phones);
        this.emails = new TreeSet<>(toCopy.emails);
        this.addresses = new TreeSet<>(toCopy.addresses);
        this.description = new Description(toCopy.description);
        this.tags = new TreeSet<>(toCopy.tags);
        this.isArchived = toCopy.isArchived;
    }

    /**
     * Sets the UUID if the provided id is not null.
     *
     * @param id the UUID to set
     * @return the Builder instance with the updated UUID
     */
    public RecruitBuilder setUuid(UUID id) {
        if (id != null) {
            this.id = id;
        }
        return this;
    }

    /**
     * Sets the list of names if the provided list is not null.
     *
     * @param names the list of names to set
     * @return the Builder instance with the updated names
     */
    public RecruitBuilder setNames(List<Name> names) {
        if (names != null) {
            this.names = new TreeSet<>(names);
        }
        return this;
    }

    /**
     * Sets the list of phone numbers if the provided list is not null.
     *
     * @param phones the list of phone numbers to set
     * @return the Builder instance with the updated phone numbers
     */
    public RecruitBuilder setPhones(List<Phone> phones) {
        if (phones != null) {
            this.phones = new TreeSet<>(phones);
        }
        return this;
    }

    /**
     * Sets the list of emails if the provided list is not null.
     *
     * @param emails the list of emails to set
     * @return the Builder instance with the updated emails
     */
    public RecruitBuilder setEmails(List<Email> emails) {
        if (emails != null) {
            this.emails = new TreeSet<>(emails);
        }
        return this;
    }

    /**
     * Sets the list of addresses if the provided list is not null.
     *
     * @param addresses the list of addresses to set
     * @return the Builder instance with the updated addresses
     */
    public RecruitBuilder setAddresses(List<Address> addresses) {
        if (addresses != null) {
            this.addresses = new TreeSet<>(addresses);
        }
        return this;
    }

    /**
     * Sets the description if the provided description is not null.
     *
     * @param description the description to set
     * @return the Builder instance with the updated description
     */
    public RecruitBuilder setDescription(Description description) {
        if (description != null) {
            this.description = new Description(description);
        }
        return this;
    }

    /**
     * Sets the set of tags if the provided set is not null.
     *
     * @param tags the set of tags to set
     * @return the Builder instance with the updated tags
     */
    public RecruitBuilder setTags(Set<Tag> tags) {
        if (tags != null) {
            this.tags = new TreeSet<>(tags);
        }
        return this;
    }

    public RecruitBuilder setArchivalState(boolean isArchived) {
        this.isArchived = isArchived;
        return this;
    }

    /**
     * Appends the list of names to the current set of names in the {@code Builder},
     * only if the provided list is not null.
     *
     * @param names List of names to append
     * @return the Builder with the appended names
     */
    public RecruitBuilder appendNames(List<Name> names) {
        if (names != null) {
            if (this.names == null) {
                this.names = new TreeSet<>(names);
            } else {
                this.names.addAll(names);
            }
        }
        return this;
    }

    /**
     * Appends the list of phone numbers to the current set of phone numbers in the {@code Builder},
     * only if the provided list is not null.
     *
     * @param phones List of phone numbers to append
     * @return the Builder with the appended phone numbers
     */
    public RecruitBuilder appendPhones(List<Phone> phones) {
        if (phones != null) {
            if (this.phones == null) {
                this.phones = new TreeSet<>(phones);
            } else {
                this.phones.addAll(phones);
            }
        }
        return this;
    }

    /**
     * Appends the list of emails to the current set of emails in the {@code Builder},
     * only if the provided list is not null.
     *
     * @param emails List of emails to append
     * @return the Builder with the appended emails
     */
    public RecruitBuilder appendEmails(List<Email> emails) {
        if (emails != null) {
            if (this.emails == null) {
                this.emails = new TreeSet<>(emails);
            } else {
                this.emails.addAll(emails);
            }
        }
        return this;
    }

    /**
     * Appends the list of addresses to the current set of addresses in the {@code Builder},
     * only if the provided list is not null.
     *
     * @param addresses List of addresses to append
     * @return the Builder with the appended addresses
     */
    public RecruitBuilder appendAddresses(List<Address> addresses) {
        if (addresses != null) {
            if (this.addresses == null) {
                this.addresses = new TreeSet<>(addresses);
            } else {
                this.addresses.addAll(addresses);
            }
        }
        return this;
    }

    /**
     * Appends a different description to the current description stored in the {@code Builder},
     * only if the provided description is not null.
     * Uses {@link Description#appendDescription} to perform this operation.
     *
     * @param description The description to append
     * @return the Builder with a new Description that is the previous description and the provided
     *         descriptions combined.
     */
    public RecruitBuilder appendDescription(Description description) {
        if (description != null) {
            if (this.description == null) {
                this.description = new Description(description);
            } else {
                this.description.appendDescription(description);
            }
        }
        return this;
    }

    /**
     * Appends the list of tags to the current set of tags in the {@code Builder},
     * only if the provided set is not null.
     *
     * @param tags List of tags to append
     * @return the Builder with the appended tags
     */
    public RecruitBuilder appendTags(Set<Tag> tags) {
        if (tags != null) {
            if (this.tags == null) {
                this.tags = new TreeSet<>(tags);
            } else {
                this.tags.addAll(tags);
            }
        }
        return this;
    }

    /**
     * Removes the list of names from the current set of names in the {@code Builder},
     * only if the provided list is not null.
     *
     * @param names List of names to remove
     * @return the Builder with the removed names
     */
    public RecruitBuilder removeNames(List<Name> names) {
        if (names != null) {
            names.forEach(this.names::remove);
        }
        return this;
    }

    /**
     * Removes the list of phone numbers from the current set of phone numbers in the {@code Builder},
     * only if the provided list is not null.
     *
     * @param phones List of phone numbers to remove
     * @return the Builder with the removed phone numbers
     */
    public RecruitBuilder removePhones(List<Phone> phones) {
        if (phones != null) {
            phones.forEach(this.phones::remove);
        }
        return this;
    }

    /**
     * Removes the list of emails from the current set of emails in the {@code Builder},
     * only if the provided list is not null.
     *
     * @param emails List of emails to remove
     * @return the Builder with the removed emails
     */
    public RecruitBuilder removeEmails(List<Email> emails) {
        if (emails != null) {
            emails.forEach(this.emails::remove);
        }
        return this;
    }

    /**
     * Removes the list of addresses from the current set of addresses in the {@code Builder},
     * only if the provided list is not null.
     *
     * @param addresses List of addresses to remove
     * @return the Builder with the removed addresses
     */
    public RecruitBuilder removeAddresses(List<Address> addresses) {
        if (addresses != null) {
            addresses.forEach(this.addresses::remove);
        }
        return this;
    }

    /**
     * Removes the Description stored in {@code Builder}.
     * This replaces the Description with a new empty description if the current description is not null.
     *
     * @return the Builder with description now being an empty description
     */
    public RecruitBuilder removeDescription() {
        this.description = Description.createEmptyDescription();
        return this;
    }

    /**
     * Removes the list of tags from the current set of tags in the {@code Builder},
     * only if the provided set is not null.
     *
     * @param tags List of tags to remove
     * @return the Builder with the removed tags
     */
    public RecruitBuilder removeTags(Set<Tag> tags) {
        if (tags != null) {
            tags.forEach(this.tags::remove);
        }
        return this;
    }

    /**
     * Checks if the {@code Builder} has been modified before.
     * A builder has been modified if any of the "set" or "append" methods have been called.
     *
     * @return boolean indicating whether the Builder has been modified.
     */
    public boolean hasBeenModified() {
        return Stream.of(this.names, this.phones, this.emails, this.addresses, this.description, this.tags)
                .anyMatch(Objects::nonNull);
    }

    /**
     * Builds a {@code Recruit} using the attributes specified in the {@code Builder}
     *
     * @return the recruit with matching builder attributes
     * @throws InvalidRecruitException if the user attempts to build a recruit without a valid name.
     */
    public Recruit build() {
        RecruitUtil.requireNonEmptyField(names);

        this.id = this.id == null ? UUID.randomUUID() : this.id;
        this.phones = this.phones == null ? new TreeSet<>() : this.phones;
        this.emails = this.emails == null ? new TreeSet<>() : this.emails;
        this.addresses = this.addresses == null ? new TreeSet<>() : this.addresses;
        this.description = this.description == null ? Description.createEmptyDescription() : this.description;
        this.tags = this.tags == null ? new TreeSet<>() : this.tags;
        return new Recruit(this);
    }
}
