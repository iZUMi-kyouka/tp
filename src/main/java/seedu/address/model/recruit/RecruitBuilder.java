package seedu.address.model.recruit;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeSet;
import java.util.UUID;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.recruit.data.Address;
import seedu.address.model.recruit.data.Data;
import seedu.address.model.recruit.data.DataSet;
import seedu.address.model.recruit.data.Description;
import seedu.address.model.recruit.data.Email;
import seedu.address.model.recruit.data.Name;
import seedu.address.model.recruit.data.Phone;
import seedu.address.model.recruit.exceptions.DataEntryAlreadyExistsException;
import seedu.address.model.recruit.exceptions.DataEntryNotFoundException;
import seedu.address.model.recruit.exceptions.IllegalRecruitBuilderActionException;
import seedu.address.model.recruit.exceptions.InvalidRecruitException;
import seedu.address.model.recruit.exceptions.TagAlreadyExistsException;
import seedu.address.model.recruit.exceptions.TagNotFoundException;
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

    protected DataSet<Name> names;
    protected DataSet<Phone> phones;
    protected DataSet<Email> emails;
    protected DataSet<Address> addresses;

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
        this.names = new DataSet<>(recruit.getNames());
        this.phones = new DataSet<>(recruit.getPhones());
        this.addresses = new DataSet<>(recruit.getAddresses());
        this.emails = new DataSet<>(recruit.getEmails());
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
        requireNonNull(toCopy, "Cannot copy a null RecruitBuilder");

        this.id = toCopy.id;
        this.names = toCopy.names == null ? null : new DataSet<>(toCopy.names);
        this.phones = toCopy.phones == null ? null : new DataSet<>(toCopy.phones);
        this.emails = toCopy.emails == null ? null : new DataSet<>(toCopy.emails);
        this.addresses = toCopy.addresses == null ? null : new DataSet<>(toCopy.addresses);
        this.description = toCopy.description == null ? null : new Description(toCopy.description);
        this.tags = toCopy.tags == null ? null : new TreeSet<>(toCopy.tags);
        this.isArchived = toCopy.isArchived;
    }

    /**
     * Sets the UUID if the provided id is not null.
     *
     * @param id the UUID to set
     * @return the Builder instance with the updated UUID
     */
    public RecruitBuilder setId(UUID id) {
        if (id != null) {
            this.id = id;
        }
        return this;
    }

    /**
     * Gets UUID assigned to the builder
     *
     * @return the UUID currently assigned to the builder in the form of an Optional.
     *         The optional is empty if there is no UUID set.
     */
    public Optional<UUID> getId() {
        return Optional.ofNullable(this.id);
    }

    /**
     * Sets names to be a Set with this individual name
     *
     * @param name the name to set
     * @return this Builder instance with names updated
     */
    public RecruitBuilder withName(Name name) {
        if (name != null) {
            this.names = new DataSet<>(name);
        }
        return this;
    }

    /**
     * Sets the primary name to be this {@code name}
     *
     * @param name the name to set
     * @return this Builder instance with names updated
     */
    public RecruitBuilder withPrimaryName(Name name) {
        if (!this.names.setPrimary(name)) {
            throw new DataEntryNotFoundException("name", List.of(name));
        }
        return this;
    }

    /**
     * Sets names to be a Set consisting of all names in the provided list.
     *
     * @param names the list of names to set
     * @return this Builder instance with names updated
     */
    public RecruitBuilder withNames(Collection<Name> names) {
        if (names != null) {
            this.names = new DataSet<>(names);
        }
        return this;
    }

    /**
     * Appends the list of names to the current set of names in the {@code Builder}
     *
     * @param namesToAdd List of names to append
     * @return the Builder with the appended names
     */
    public RecruitBuilder appendNames(Collection<Name> namesToAdd) {
        this.names = appendEntriesToTree("name", this.names, namesToAdd);
        return this;
    }

    /**
     * Removes the list of names from the current set of names in the {@code Builder}
     *
     * @param namesToRemove List of names to remove
     * @return the Builder with the removed names
     */
    public RecruitBuilder removeNames(Collection<Name> namesToRemove) {
        this.names = removeEntriesFromTree("name", this.names, namesToRemove);
        return this;
    }

    /**
     * Gets the names assigned to the builder.
     *
     * @return an Optional containing the set of Names currently assigned to the builder,
     *         or an empty Optional if no Names have been set.
     */
    public Optional<DataSet<Name>> getNames() {
        return Optional.ofNullable(this.names);
    }

    /**
     * Sets phones to be a Set with this individual phone number.
     *
     * @param phone the phone number to set
     * @return this Builder instance with phones updated
     */
    public RecruitBuilder withPhone(Phone phone) {
        if (phone != null) {
            this.phones = new DataSet<>(phone);
        }
        return this;
    }

    /**
     * Sets the primary phone number to be this {@code phone}
     *
     * @param phone the phone to set
     * @return this Builder instance with primary phone updated
     */
    public RecruitBuilder withPrimaryPhone(Phone phone) {
        if (!this.phones.setPrimary(phone)) {
            throw new DataEntryNotFoundException("phone", List.of(phone));
        }
        return this;
    }

    /**
     * Sets phones to be a Set consisting of all phone numbers in the provided list.
     *
     * @param phones the list of phone numbers to set
     * @return this Builder instance with phones updated
     */
    public RecruitBuilder withPhones(Collection<Phone> phones) {
        if (phones != null) {
            this.phones = new DataSet<>(phones);
        }
        return this;
    }

    /**
     * Appends the list of phone numbers to the current set of phone numbers in the {@code Builder},
     * only if the provided list is not null.
     *
     * @param phonesToAdd List of phone numbers to append
     * @return the Builder with the appended phone numbers
     */
    public RecruitBuilder appendPhones(Collection<Phone> phonesToAdd) {
        this.phones = appendEntriesToTree("phone", this.phones, phonesToAdd);
        return this;
    }

    /**
     * Removes the list of phone numbers from the current set of phone numbers in the {@code Builder}.
     *
     * @param phonesToRemove List of phone numbers to remove
     * @return the Builder with the removed phone numbers
     */
    public RecruitBuilder removePhones(Collection<Phone> phonesToRemove) {
        this.phones = removeEntriesFromTree("phone", this.phones, phonesToRemove);
        return this;
    }

    /**
     * Gets the phones assigned to the builder.
     *
     * @return an Optional containing the set of Phones currently assigned to the builder,
     *         or an empty Optional if no Phones have been set.
     */
    public Optional<DataSet<Phone>> getPhones() {
        return Optional.ofNullable(this.phones);
    }

    /**
     * Sets emails to be a Set with this individual email.
     *
     * @param email the email to set
     * @return this Builder instance with emails updated
     */
    public RecruitBuilder withEmail(Email email) {
        if (email != null) {
            this.emails = new DataSet<>(email);
        }
        return this;
    }

    /**
     * Sets the primary email to be this {@code email}
     *
     * @param email the email to set
     * @return this Builder instance with primary email updated
     */
    public RecruitBuilder withPrimaryEmail(Email email) {
        if (!this.emails.setPrimary(email)) {
            throw new DataEntryNotFoundException("email", List.of(email));
        }
        return this;
    }

    /**
     * Sets emails to be a Set consisting of all emails in the provided list.
     *
     * @param emails the list of emails to set
     * @return this Builder instance with emails updated
     */
    public RecruitBuilder withEmails(Collection<Email> emails) {
        if (emails != null) {
            this.emails = new DataSet<>(emails);
        }
        return this;
    }

    /**
     * Appends the list of emails to the current set of emails in the {@code Builder},
     * only if the provided list is not null.
     *
     * @param emailsToAdd List of emails to append
     * @return the Builder with the appended emails
     */
    public RecruitBuilder appendEmails(Collection<Email> emailsToAdd) {
        this.emails = appendEntriesToTree("email", this.emails, emailsToAdd);
        return this;
    }

    /**
     * Removes the list of emails from the current set of emails in the {@code Builder}.
     *
     * @param emailsToRemove List of emails to remove
     * @return the Builder with the removed emails
     */
    public RecruitBuilder removeEmails(Collection<Email> emailsToRemove) {
        this.emails = removeEntriesFromTree("email", this.emails, emailsToRemove);
        return this;
    }

    /**
     * Gets the emails assigned to the builder.
     *
     * @return an Optional containing the set of Emails currently assigned to the builder,
     *         or an empty Optional if no Emails have been set.
     */
    public Optional<DataSet<Email>> getEmails() {
        return Optional.ofNullable(this.emails);
    }

    /**
     * Sets addresses to be a Set with this individual address.
     *
     * @param address the address to set
     * @return this Builder instance with addresses updated
     */
    public RecruitBuilder withAddress(Address address) {
        if (address != null) {
            this.addresses = new DataSet<>(address);
        }
        return this;
    }

    /**
     * Sets the primary address to be this {@code address}
     *
     * @param address the address to set
     * @return this Builder instance with primary address updated
     */
    public RecruitBuilder withPrimaryAddress(Address address) {
        if (!this.addresses.setPrimary(address)) {
            throw new DataEntryNotFoundException("address", List.of(address));
        }
        return this;
    }

    /**
     * Sets addresses to be a Set consisting of all addresses in the provided list.
     *
     * @param addresses the list of addresses to set
     * @return this Builder instance with addresses updated
     */
    public RecruitBuilder withAddresses(Collection<Address> addresses) {
        if (addresses != null) {
            this.addresses = new DataSet<>(addresses);
        }
        return this;
    }

    /**
     * Appends the list of addresses to the current set of addresses in the {@code Builder},
     * only if the provided list is not null.
     *
     * @param addressesToAdd List of addresses to append
     * @return the Builder with the appended addresses
     */
    public RecruitBuilder appendAddresses(Collection<Address> addressesToAdd) {
        this.addresses = appendEntriesToTree("address", this.addresses, addressesToAdd);
        return this;
    }

    /**
     * Removes the list of addresses from the current set of addresses in the {@code Builder}.
     *
     * @param addressesToRemove List of addresses to remove
     * @return the Builder with the removed addresses
     */
    public RecruitBuilder removeAddresses(Collection<Address> addressesToRemove) {
        this.addresses = removeEntriesFromTree("address", this.addresses, addressesToRemove);
        return this;
    }

    /**
     * Gets the addresses assigned to the builder.
     *
     * @return an Optional containing the set of Addresses currently assigned to the builder,
     *         or an empty Optional if no Addresses have been set.
     */
    public Optional<DataSet<Address>> getAddresses() {
        return Optional.ofNullable(this.addresses);
    }

    /**
     * Sets the description if the provided description is not null.
     *
     * @param description the description to set
     * @return the Builder instance with the updated description
     */
    public RecruitBuilder withDescription(Description description) {
        if (description != null) {
            this.description = new Description(description);
        }
        return this;
    }

    /**
     * Appends a different description to the current description stored in the {@code Builder},
     * only if the provided description is not null.
     * Uses {@link Description#appendDescription} to perform this operation.
     *
     * @param otherDescription The description to append
     * @return the Builder with a new Description that is the previous description and the provided
     *         descriptions combined.
     */
    public RecruitBuilder appendDescription(Description otherDescription) {
        if (otherDescription == null) {
            return this;
        }

        if (this.description == null) {
            this.description = new Description(otherDescription);
            return this;
        }

        this.description = this.description.appendDescription(otherDescription);
        return this;
    }

    /**
     * Removes the description from the current set of tags in the {@code Builder}.
     *
     * @return the Builder with the description removed.
     */
    public RecruitBuilder removeDescription() {
        this.description = Description.createEmptyDescription();

        return this;
    }

    /**
     * Gets the description assigned to the builder.
     *
     * @return an Optional containing the description currently assigned to the builder,
     *         or an empty Optional if no Tags have been set.
     */
    public Optional<Description> getDescription() {
        return Optional.ofNullable(this.description);
    }

    /**
     * Sets tags to be a Set with this individual tag.
     *
     * @param tag the tag to set
     * @return this Builder instance with tags updated
     */
    public RecruitBuilder withTag(Tag tag) {
        if (tag != null) {
            this.tags = new TreeSet<>();
            this.tags.add(tag);
        }
        return this;
    }

    /**
     * Sets tags to be a Set consisting of all tags in the provided list.
     *
     * @param tags the list of tags to set
     * @return this Builder instance with tags updated
     */
    public RecruitBuilder withTags(List<Tag> tags) {
        if (tags != null) {
            this.tags = new TreeSet<>(tags);
        }
        return this;
    }

    /**
     * Appends the list of tags to the current set of tags in the {@code Builder},
     * only if the provided list is not null.
     *
     * @param tagsToAdd List of tags to append
     * @return the Builder with the appended tags
     */
    public RecruitBuilder appendTags(List<Tag> tagsToAdd) {
        if (tagsToAdd == null || tagsToAdd.isEmpty()) {
            return this;
        }

        if (this.tags == null) {
            this.tags = new TreeSet<>(tagsToAdd);
        }

        TreeSet<Tag> copy = new TreeSet<>(this.tags);
        List<Tag> duplicatedTags = CollectionUtil.addListToSetReturnDuplicates(copy, tagsToAdd);

        if (!duplicatedTags.isEmpty()) {
            throw new TagAlreadyExistsException(duplicatedTags);
        }

        this.tags.addAll(tagsToAdd);
        return this;
    }

    /**
     * Removes the list of tags from the current set of tags in the {@code Builder}.
     *
     * @param tagsToRemove List of tags to remove
     * @return the Builder with the removed tags
     */
    public RecruitBuilder removeTags(List<Tag> tagsToRemove) {
        if (tagsToRemove == null || tagsToRemove.isEmpty()) {
            return this;
        }

        if (this.tags == null) {
            throw new IllegalRecruitBuilderActionException(
                    "Cannot remove tags from a RecruitBuilder for which tags have not been set");
        }

        TreeSet<Tag> copy = new TreeSet<>(this.tags);
        List<Tag> missing = CollectionUtil.removeListFromSetReturnMissing(copy, tagsToRemove);

        if (!missing.isEmpty()) {
            throw new TagNotFoundException(missing);
        }

        tagsToRemove.forEach(this.tags::remove);
        return this;
    }

    /**
     * Gets the tags assigned to the builder.
     *
     * @return an Optional containing the set of Tags currently assigned to the builder,
     *         or an empty Optional if no Tags have been set.
     */
    public Optional<TreeSet<Tag>> getTags() {
        return Optional.ofNullable(this.tags);
    }

    /**
     * Sets the archival status to be the value "isArchived".
     *
     * @param isArchived the boolean indicating whether the Recruit should be archived
     * @return this Builder instance with this artchival status
     */
    public RecruitBuilder withArchivalState(boolean isArchived) {
        this.isArchived = isArchived;
        return this;
    }

    /**
     * Overrides the fields in the current {@code RecruitBuilder} with the fields in the other RecruitBuilder.
     * If the other RecruitBuilder has fields that have not been modified, ignore those fields.
     *
     * @param other the other builder to override the fields of this builder using.
     * @return this builder but with fields that have been overridden.
     */
    public RecruitBuilder override(RecruitBuilder other) {
        this.id = other.id == null ? this.id : other.id;
        this.names = other.names == null ? this.names : other.names;
        this.phones = other.phones == null ? this.phones : other.phones;
        this.emails = other.emails == null ? this.emails : other.emails;
        this.addresses = other.addresses == null ? this.addresses : other.addresses;
        this.description = other.description == null ? this.description : other.description;
        this.tags = other.tags == null ? this.tags : other.tags;
        return this;
    }

    /**
     * Appends the fields in the current {@code RecruitBuilder} with the fields in the other RecruitBuilder.
     * If the other RecruitBuilder has fields that have not been modified, ignore those fields.
     * Only keeps primary data from the current {@code RecruitBuilder}
     *
     * @param other the other builder to override the fields of this builder using.
     * @return this builder but with fields that have been overridden.
     */
    public RecruitBuilder append(RecruitBuilder other) {
        if (other == null) {
            return this;
        }

        if (other.names != null) {
            this.appendNames(other.names.stream().toList());
        }
        if (other.phones != null) {
            this.appendPhones(other.phones.stream().toList());
        }
        if (other.emails != null) {
            this.appendEmails(other.emails.stream().toList());
        }
        if (other.addresses != null) {
            this.appendAddresses(other.addresses.stream().toList());
        }
        if (other.description != null) {
            this.appendDescription(other.description);
        }
        if (other.tags != null) {
            this.appendTags(other.tags.stream().toList());
        }

        return this;
    }

    /**
     * Remove all data from the fields in the current {@code RecruitBuilder} that exist in the fields 
     * in the other RecruitBuilder. If the other RecruitBuilder has fields that have not been modified,
     * ignore those fields.
     *
     * @param other the other builder to override the fields of this builder using.
     * @return this builder but with fields that have been overridden.
     */
    public RecruitBuilder remove(RecruitBuilder other) {
        if (other == null) {
            return this;
        }

        if (other.names != null) {
            this.removeNames(other.names.stream().toList());
        }
        if (other.phones != null) {
            this.removePhones(other.phones.stream().toList());
        }
        if (other.emails != null) {
            this.removeEmails(other.emails.stream().toList());
        }
        if (other.addresses != null) {
            this.removeAddresses(other.addresses.stream().toList());
        }
        if (other.description != null) {
            this.description = Description.createEmptyDescription();
        }
        if (other.tags != null) {
            this.removeTags(other.tags.stream().toList());
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
        return CollectionUtil.isAnyNonNull(id, names, phones, emails, addresses, description, tags);
    }

    /**
     * Builds a {@code Recruit} using the attributes specified in the {@code Builder}
     *
     * @return the recruit with matching builder attributes
     * @throws InvalidRecruitException if the user attempts to build a recruit without a valid name.
     */
    public Recruit build() {
        if (this.names == null || this.names.isEmpty()) {
            throw new InvalidRecruitException("Cannot build Recruit without at least 1 name!");
        }

        this.id = this.id == null ? UUID.randomUUID() : this.id;
        this.phones = this.phones == null ? new DataSet<>() : this.phones;
        this.emails = this.emails == null ? new DataSet<>() : this.emails;
        this.addresses = this.addresses == null ? new DataSet<>() : this.addresses;
        this.description = this.description == null ? Description.createEmptyDescription() : this.description;
        this.tags = this.tags == null ? new TreeSet<>() : this.tags;
        return new Recruit(this);
    }

    /**
     * Checks if the current builder contains the exact same data entries as
     * the other builder.
     *
     * @param otherBuilder the other builder to compare against
     * @return true if both builders have the same entries
     */
    public boolean hasSameData(RecruitBuilder otherBuilder) {
        return Objects.equals(names, otherBuilder.names)
                && Objects.equals(phones, otherBuilder.phones)
                && Objects.equals(emails, otherBuilder.emails)
                && Objects.equals(addresses, otherBuilder.addresses)
                && Objects.equals(description, otherBuilder.description)
                && Objects.equals(tags, otherBuilder.tags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RecruitBuilder)) {
            return false;
        }

        RecruitBuilder otherBuilder = (RecruitBuilder) other;
        return Objects.equals(id, otherBuilder.id)
                && hasSameData(otherBuilder);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("id", id)
                .add("name", names)
                .add("phone", phones)
                .add("email", emails)
                .add("address", addresses)
                .add("description", description)
                .add("tags", tags)
                .add("isArchived", isArchived)
                .toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, names, phones, emails, addresses, description, tags);
    }

    /**
     * Creates a copy of container and appends all entries in dataToAdd into this copy.
     * This copy is then returned.
     *
     * <p>If dataToAdd is null or empty, it just returns the original container unaltered.</p>
     *
     * <p>Datatype must be provided for the purposes of printing error messages.</p>
     *
     * <p>Guarantees that the original container is not altered in any way.</p>
     *
     * @param dataType the type of data that is being processed
     * @param container the original container with the data
     * @param dataToAdd the data to add into this container
     * @return a copy of the container with entries in dataToAdd added to this copy.
     * @throws DataEntryAlreadyExistsException if any of the elements in dataToAdd already exist in container.
     */
    private <T extends Data & Comparable<T>> TreeSet<T> appendEntriesToTree(
            String dataType, TreeSet<T> container, Collection<? extends T> dataToAdd) {
        if (dataToAdd == null || dataToAdd.isEmpty()) {
            return container;
        }

        if (container == null) {
            return new TreeSet<>(dataToAdd);
        }

        TreeSet<T> copy = new TreeSet<>(container);
        List<T> duplicates = CollectionUtil.addListToSetReturnDuplicates(copy, dataToAdd);

        if (!duplicates.isEmpty()) {
            throw new DataEntryAlreadyExistsException(dataType, duplicates);
        }

        return copy;
    }

    private <T extends Data & Comparable<T>> DataSet<T> appendEntriesToTree(
            String dataType, DataSet<T> container, Collection<? extends T> dataToAdd) {
        TreeSet<T> ts = this.appendEntriesToTree(dataType, (TreeSet<T>) container, dataToAdd);
        DataSet<T> ds = new DataSet<>(ts);
        
        // set the new DataSet's primary data to the container DataSet's primary data, if any
        if (!(container == null) && (!container.isEmpty() && container.getPrimary().isPresent())) {
            ds.setPrimary(container.getPrimary().get());
        }

        return ds;
    }

    /**
     * Creates a copy of container and removes each entry of dataToRemove from this copy.
     * This copy is then returned.
     *
     * <p>Datatype must be provided for the purposes of printing error messages.</p>
     *
     * <p>Guarantees that the original container is not altered in any way.</p>
     *
     * @param dataType the type of data that is being processed
     * @param container the original container with the data
     * @param dataToRemove the data to remove from this container
     * @return a copy of the container with entries in dataToRemove removed from this copy.
     * @throws DataEntryNotFoundException if any of the elements in dataToRemove cannot be found in container.
     * @throws IllegalRecruitBuilderActionException if container was null but dataToRemove is not empty or null.
     */
    private <T extends Data & Comparable<T>> TreeSet<T> removeEntriesFromTree(
            String dataType, TreeSet<T> container, Collection<? extends T> dataToRemove) {
        if (dataToRemove == null || dataToRemove.isEmpty()) {
            return container;
        }

        if (container == null) {
            throw new IllegalRecruitBuilderActionException(
                    String.format("Cannot remove %s entries from a RecruitBuilder where no %s have been set.",
                            dataType, dataType));
        }

        TreeSet<T> copy = new TreeSet<>(container);
        List<T> missing = CollectionUtil.removeListFromSetReturnMissing(copy, dataToRemove);

        if (!missing.isEmpty()) {
            throw new DataEntryNotFoundException(dataType, missing);
        }

        return copy;
    }

    private <T extends Data & Comparable<T>> DataSet<T> removeEntriesFromTree(
            String dataType, DataSet<T> container, Collection<? extends T> dataToAdd) {
        TreeSet<T> ts = this.removeEntriesFromTree(dataType, (TreeSet<T>) container, dataToAdd);
        DataSet<T> ds = new DataSet<>(ts);

        if (!(container == null) && (container.isEmpty() || container.getPrimary().isEmpty())) {
            return ds;
        }

        // set the new DataSet's primary data to the previous primary data, if any
        T prevPrimary = container.getPrimary().get();
        if (ds.contains(prevPrimary)) {
            ds.setPrimary(prevPrimary);
        }

        return ds;
    }
}
