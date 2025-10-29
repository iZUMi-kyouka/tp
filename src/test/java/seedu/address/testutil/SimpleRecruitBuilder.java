package seedu.address.testutil;

import static seedu.address.commons.util.CollectionUtil.combine;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import seedu.address.model.recruit.Recruit;
import seedu.address.model.recruit.RecruitBuilder;
import seedu.address.model.recruit.data.Address;
import seedu.address.model.recruit.data.Description;
import seedu.address.model.recruit.data.Email;
import seedu.address.model.recruit.data.Name;
import seedu.address.model.recruit.data.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class SimpleRecruitBuilder {
    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_DESCRIPTION = "It's nothing but the E-mail Guy";

    private UUID id;
    private List<Name> names;
    private List<Phone> phones;
    private List<Email> emails;
    private List<Address> addresses;
    private Description description;
    private Set<Tag> tags;
    private boolean isArchived;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public SimpleRecruitBuilder() {
        id = UUID.randomUUID();
        names = List.of(new Name(DEFAULT_NAME));
        phones = List.of(new Phone(DEFAULT_PHONE));
        emails = List.of(new Email(DEFAULT_EMAIL));
        addresses = List.of(new Address(DEFAULT_ADDRESS));
        description = Description.createDescription(DEFAULT_DESCRIPTION);
        tags = new HashSet<>();
        isArchived = false;
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public SimpleRecruitBuilder(Recruit recruitToCopy) {
        id = recruitToCopy.getID();
        names = recruitToCopy.getNames();
        phones = recruitToCopy.getPhones();
        emails = recruitToCopy.getEmails();
        addresses = recruitToCopy.getAddresses();
        description = recruitToCopy.getDescription();
        tags = new HashSet<>(recruitToCopy.getTags());
        isArchived = recruitToCopy.isArchived();
    }

    /**
     * Sets the {@code Id} of the {@code Recruit} that we are building.
     */
    public SimpleRecruitBuilder withID(String id) {
        this.id = UUID.fromString(id);
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code Recruit} that we are building.
     */
    public SimpleRecruitBuilder withName(String name) {
        this.names = List.of(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code Recruit} that we are building.
     */
    public SimpleRecruitBuilder withAdditionalNames(String... names) {
        this.names = combine(List.of(this.names, Arrays.asList(names).stream().map(Name::new).toList()));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Recruit} that we are building.
     */
    public SimpleRecruitBuilder withPhone(String phone) {
        this.phones = List.of(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code Recruit} that we are building.
     */
    public SimpleRecruitBuilder withAdditionalPhones(String... phones) {
        this.phones = combine(List.of(this.phones, Arrays.asList(phones).stream().map(Phone::new).toList()));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Recruit} that we are building.
     */
    public SimpleRecruitBuilder withEmail(String email) {
        this.emails = List.of(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code Recruit} that we are building.
     */
    public SimpleRecruitBuilder withAdditionalEmails(String... emails) {
        this.emails = combine(List.of(this.emails, Arrays.asList(emails).stream().map(Email::new).toList()));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Recruit} that we are building.
     */
    public SimpleRecruitBuilder withAddress(String address) {
        this.addresses = List.of(new Address(address));
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code Recruit} that we are building.
     */
    public SimpleRecruitBuilder withAdditionalAddresses(String... addresses) {
        this.addresses = combine(
                List.of(this.addresses, Arrays.asList(addresses).stream().map(Address::new).toList()));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Recruit} that we are building.
     */
    public SimpleRecruitBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Recruit} that we are building.
     */
    public SimpleRecruitBuilder withAdditionalTags(String ... tags) {
        this.tags = new HashSet<>(combine(List.of(this.tags, SampleDataUtil.getTagSet(tags))));
        return this;
    }

    /**
     * Sets the {@code Archive Status} of the {@code Recruit} that we are building.
     */
    public SimpleRecruitBuilder withArchivedStatus(boolean isArchived) {
        this.isArchived = isArchived;
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code Recruit} that we are building.
     */
    public SimpleRecruitBuilder withDescription(String description) {
        this.description = Description.createDescription(description);
        return this;
    }

    /**
     * Builds the recruit given the current settings
     *
     * @return the resulting Recruit
     */
    public Recruit build() {
        return new RecruitBuilder()
                .setId(this.id)
                .withNames(names)
                .withPhones(phones)
                .withEmails(emails)
                .withAddresses(addresses)
                .withDescription(description)
                .withTags(tags.stream().toList())
                .withArchivalState(isArchived)
                .build();
    }

}
