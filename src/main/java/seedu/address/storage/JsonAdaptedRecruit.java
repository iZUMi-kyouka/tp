package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.recruit.Recruit;
import seedu.address.model.recruit.RecruitBuilder;
import seedu.address.model.recruit.data.Address;
import seedu.address.model.recruit.data.Description;
import seedu.address.model.recruit.data.Email;
import seedu.address.model.recruit.data.Name;
import seedu.address.model.recruit.data.Phone;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Recruit}.
 */
class JsonAdaptedRecruit {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Recruit's %s field is missing!";

    private final String id;
    private final List<String> names;
    private final List<String> phones;
    private final List<String> emails;
    private final List<String> addresses;
    private final String primaryName;
    private final String primaryPhone;
    private final String primaryEmail;
    private final String primaryAddress;
    private final String description;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();
    private final boolean isArchived;

    /**
     * Constructs a {@code JsonAdaptedRecruit} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedRecruit(@JsonProperty("id") String id,
                              @JsonProperty("names") List<String> name,
                              @JsonProperty("phones") List<String> phone,
                              @JsonProperty("emails") List<String> email,
                              @JsonProperty("addresses") List<String> address,
                              @JsonProperty("description") String description,
                              @JsonProperty("tags") List<JsonAdaptedTag> tags,
                              @JsonProperty("isArchived") boolean isArchived,
                              @JsonProperty("primaryName") String primaryName,
                              @JsonProperty("primaryPhone") String primaryPhone,
                              @JsonProperty("primaryEmail") String primaryEmail,
                              @JsonProperty("primaryAddress") String primaryAddress) {
        this.id = id;
        this.names = name;
        this.phones = phone;
        this.emails = email;
        this.addresses = address;
        this.primaryName = primaryName;
        this.primaryPhone = primaryPhone;
        this.primaryEmail = primaryEmail;
        this.primaryAddress = primaryAddress;
        this.description = description;
        if (tags != null) {
            this.tags.addAll(tags);
        }
        this.isArchived = isArchived;
    }

    /**
     * Converts a given {@code Recruit} into this class for Jackson use.
     */
    public JsonAdaptedRecruit(Recruit source) {
        id = source.getID().toString();
        names = source.getNames().stream().map(n -> n.value).toList();
        phones = source.getPhones().stream().map(p -> p.value).toList();
        emails = source.getEmails().stream().map(e -> e.value).toList();
        addresses = source.getAddresses().stream().map(a -> a.value).toList();
        this.primaryName = source.getName().value;
        this.primaryPhone = source.getPhone().map(p -> p.value).orElse(null);
        this.primaryEmail = source.getEmail().map(e -> e.value).orElse(null);
        this.primaryAddress = source.getAddress().map(a -> a.value).orElse(null);
        description = source.getDescription().value;
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .toList());
        isArchived = source.isArchived();
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Recruit toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            personTags.add(tag.toModelType());
        }
        if (id == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, UUID.class.getSimpleName()));
        }
        final UUID modelId = UUID.fromString(id);

        if (names == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (names.stream().anyMatch(n -> !Name.isValidName(n))) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }

        final List<Name> modelNames = names.stream().map(Name::new).toList();

        if (phones == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (phones.stream().anyMatch(p -> !Phone.isValidPhone(p))) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final List<Phone> modelPhones = phones.stream().map(Phone::new).toList();

        if (emails == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (emails.stream().anyMatch(e -> !Email.isValidEmail(e))) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final List<Email> modelEmails = emails.stream().map(Email::new).toList();

        if (addresses == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (addresses.stream().anyMatch(a -> !Address.isValidAddress(a))) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final List<Address> modelAddresses = addresses.stream().map(Address::new).toList();

        if (description == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(description)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        final Description modelDescription = Description.createDescription(description);

        RecruitBuilder rb = new RecruitBuilder()
                .setId(modelId)
                .withNames(modelNames)
                .withPhones(modelPhones)
                .withEmails(modelEmails)
                .withAddresses(modelAddresses)
                .withDescription(modelDescription)
                .withTags(personTags)
                .withArchivalState(isArchived);

        if (primaryName != null && Name.isValidName(primaryName)) {
            rb.withPrimaryName(new Name(primaryName));
        }
        if (primaryPhone != null && Phone.isValidPhone(primaryPhone)) {
            rb.withPrimaryPhone(new Phone(primaryPhone));
        }
        if (primaryEmail != null && Email.isValidEmail(primaryEmail)) {
            rb.withPrimaryEmail(new Email(primaryEmail));
        }
        if (primaryAddress != null && Address.isValidAddress(primaryAddress)) {
            rb.withPrimaryAddress(new Address(primaryAddress));
        }

        return rb.build();
    }

}
