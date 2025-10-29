package seedu.address.testutil;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand;
import seedu.address.model.recruit.Recruit;
import seedu.address.model.recruit.data.Address;
import seedu.address.model.recruit.data.Description;
import seedu.address.model.recruit.data.Email;
import seedu.address.model.recruit.data.Name;
import seedu.address.model.recruit.data.Phone;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditRecruitDescriptorBuilder {

    private EditCommand.EditRecruitDescriptor descriptor;

    public EditRecruitDescriptorBuilder() {
        descriptor = new EditCommand.EditRecruitDescriptor();
    }

    public EditRecruitDescriptorBuilder(EditCommand.EditOperation op) {
        descriptor = new EditCommand.EditRecruitDescriptor(op);
    }

    public EditRecruitDescriptorBuilder(EditCommand.EditRecruitDescriptor descriptor) {
        this.descriptor = new EditCommand.EditRecruitDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code person}'s details
     */
    public EditRecruitDescriptorBuilder(Recruit recruit) {
        descriptor = new EditCommand.EditRecruitDescriptor();
        descriptor.setId(recruit.getID());
        descriptor.withNames(recruit.getNames());
        descriptor.withPhones(recruit.getPhones());
        descriptor.withEmails(recruit.getEmails());
        descriptor.withAddresses(recruit.getAddresses());
        descriptor.withDescription(recruit.getDescription());
        descriptor.withTags(recruit.getTags().stream().toList());
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code person}'s details
     */
    public EditRecruitDescriptorBuilder(EditCommand.EditOperation op, Recruit recruit) {
        descriptor = new EditCommand.EditRecruitDescriptor(op);
        descriptor.setId(recruit.getID());
        descriptor.withNames(recruit.getNames());
        descriptor.withPhones(recruit.getPhones());
        descriptor.withEmails(recruit.getEmails());
        descriptor.withAddresses(recruit.getAddresses());
        descriptor.withDescription(recruit.getDescription());
        descriptor.withTags(recruit.getTags().stream().toList());
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditRecruitDescriptorBuilder withID(String id) {
        descriptor.setId(UUID.fromString(id));
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditRecruitDescriptorBuilder withName(String name) {
        descriptor.withNames(List.of(new Name(name)));
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditRecruitDescriptorBuilder withNames(String... names) {
        descriptor.withNames(Arrays.asList(names).stream().map(Name::new).toList());
        return this;
    }


    /**
     * Sets the {@code Phone} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditRecruitDescriptorBuilder withPhone(String phone) {
        descriptor.withPhones(List.of(new Phone(phone)));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditRecruitDescriptorBuilder withPhones(String... phones) {
        descriptor.withPhones(Arrays.asList(phones).stream().map(Phone::new).toList());
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditRecruitDescriptorBuilder withEmail(String email) {
        descriptor.withEmails(List.of(new Email(email)));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditRecruitDescriptorBuilder withEmails(String... emails) {
        descriptor.withEmails(Arrays.asList(emails).stream().map(Email::new).toList());
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditRecruitDescriptorBuilder withAddress(String address) {
        descriptor.withAddresses(List.of(new Address(address)));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditRecruitDescriptorBuilder withAddresses(String... addresses) {
        descriptor.withAddresses(Arrays.asList(addresses).stream().map(Address::new).toList());
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditRecruitDescriptorBuilder withDescription(String description) {
        descriptor.withDescription(new Description(description));
        return this;
    }


    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditRecruitDescriptorBuilder withTags(String... tags) {
        descriptor.withTags(Stream.of(tags).map(Tag::new).toList());
        return this;
    }

    public EditCommand.EditRecruitDescriptor build() {
        return descriptor;
    }
}
