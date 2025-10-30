package seedu.address.model.recruit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import seedu.address.model.recruit.data.Address;
import seedu.address.model.recruit.data.Description;
import seedu.address.model.recruit.data.Email;
import seedu.address.model.recruit.data.Name;
import seedu.address.model.recruit.data.Phone;
import seedu.address.model.recruit.exceptions.DataEntryAlreadyExistsException;
import seedu.address.model.recruit.exceptions.DataEntryNotFoundException;
import seedu.address.model.recruit.exceptions.IllegalRecruitBuilderActionException;
import seedu.address.model.recruit.exceptions.NoNameRecruitException;
import seedu.address.model.recruit.exceptions.TagAlreadyExistsException;
import seedu.address.model.recruit.exceptions.TagNotFoundException;
import seedu.address.model.tag.Tag;

public class RecruitBuilderTest {

    // Constructor tests
    @Test
    void constructor_default_unmodified() {
        RecruitBuilder builder = new RecruitBuilder();
        assertFalse(builder.hasBeenModified());
    }

    @Test
    void copyConstructor_copiesFields() {
        RecruitBuilder original = new RecruitBuilder()
                .withName(new Name(VALID_NAME_AMY))
                .withPhone(new Phone(VALID_PHONE_AMY))
                .withEmail(new Email(VALID_EMAIL_AMY))
                .withAddress(new Address(VALID_ADDRESS_AMY))
                .withDescription(Description.createDescription(VALID_DESCRIPTION_AMY))
                .withTag(new Tag(VALID_TAG_FRIEND));

        RecruitBuilder copy = new RecruitBuilder(original);

        assertNotNull(copy);
        assertHasSameData(original, copy);
    }

    @Test
    void copyConstructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RecruitBuilder((RecruitBuilder) null));
    }

    // UUID
    @Test
    void withUuid_setsProvidedUuid_success() {
        UUID customId = UUID.randomUUID();
        Recruit recruit = new RecruitBuilder()
                .withName(new Name(VALID_NAME_AMY))
                .setId(customId)
                .build();

        assertEquals(customId, recruit.getID());
    }

    // Name
    @Test
    void withName_setsSingleName() {
        RecruitBuilder builder = new RecruitBuilder().withName(new Name(VALID_NAME_AMY));
        assertTrue(builder.hasBeenModified());
    }

    @Test
    void withName_null_doesNothing() {
        RecruitBuilder builder = new RecruitBuilder();
        builder.withName(null);
        assertFalse(builder.hasBeenModified());
    }

    @Test
    void appendNames_addsSuccessfully() {
        RecruitBuilder builder = new RecruitBuilder().withName(new Name(VALID_NAME_AMY));
        builder.appendNames(List.of(new Name(VALID_NAME_BOB)));

        assertTrue(builder.hasBeenModified());
        RecruitBuilder solution = new RecruitBuilder().withNames(
                List.of(new Name(VALID_NAME_AMY), new Name(VALID_NAME_BOB)));

        assertHasSameData(builder, solution);
    }

    @Test
    void appendNames_duplicate_throwsException() {
        RecruitBuilder builder = new RecruitBuilder().withNames(List.of(new Name(VALID_NAME_AMY)));
        assertThrows(DataEntryAlreadyExistsException.class, () ->
                builder.appendNames(List.of(new Name(VALID_NAME_AMY))));
    }

    @Test
    void removeNames_removesSuccessfully() {
        RecruitBuilder builder = new RecruitBuilder()
                .withNames(List.of(new Name(VALID_NAME_AMY), new Name(VALID_NAME_BOB)));
        builder.removeNames(List.of(new Name(VALID_NAME_BOB)));

        assertTrue(builder.hasBeenModified());
        RecruitBuilder solution = new RecruitBuilder().withName(new Name(VALID_NAME_AMY));

        assertHasSameData(builder, solution);
    }

    @Test
    void removeNames_missing_throwsException() {
        RecruitBuilder builder = new RecruitBuilder().withNames(List.of(new Name(VALID_NAME_AMY)));
        assertThrows(DataEntryNotFoundException.class, () ->
                builder.removeNames(List.of(new Name("Bob"))));
    }

    @Test
    void removeNames_withoutSet_throwsException() {
        RecruitBuilder builder = new RecruitBuilder();
        assertThrows(IllegalRecruitBuilderActionException.class, () ->
                builder.removeNames(List.of(new Name(VALID_NAME_AMY))));
    }

    // Phone
    @Test
    void withPhone_setsSinglePhone() {
        RecruitBuilder builder = new RecruitBuilder().withPhone(new Phone(VALID_PHONE_AMY));
        assertTrue(builder.hasBeenModified());
    }

    @Test
    void withPhone_null_doesNothing() {
        RecruitBuilder builder = new RecruitBuilder();
        builder.withPhone(null);
        assertFalse(builder.hasBeenModified());
    }

    @Test
    void appendPhones_addsSuccessfully() {
        RecruitBuilder builder = new RecruitBuilder().withPhone(new Phone(VALID_PHONE_AMY));
        builder.appendPhones(List.of(new Phone(VALID_PHONE_BOB)));

        assertTrue(builder.hasBeenModified());
        RecruitBuilder solution = new RecruitBuilder().withPhones(
                List.of(new Phone(VALID_PHONE_AMY), new Phone(VALID_PHONE_BOB)));

        assertHasSameData(builder, solution);
    }

    @Test
    void appendPhones_duplicate_throwsException() {
        RecruitBuilder builder = new RecruitBuilder().withPhones(List.of(new Phone(VALID_PHONE_AMY)));
        assertThrows(DataEntryAlreadyExistsException.class, () ->
                builder.appendPhones(List.of(new Phone(VALID_PHONE_AMY))));
    }

    @Test
    void removePhones_removesSuccessfully() {
        RecruitBuilder builder = new RecruitBuilder()
                .withPhones(List.of(new Phone(VALID_PHONE_AMY), new Phone(VALID_PHONE_BOB)));
        builder.removePhones(List.of(new Phone(VALID_PHONE_BOB)));

        assertTrue(builder.hasBeenModified());
        RecruitBuilder solution = new RecruitBuilder().withPhone(new Phone(VALID_PHONE_AMY));

        assertHasSameData(builder, solution);
    }

    @Test
    void removePhones_missing_throwsException() {
        RecruitBuilder builder = new RecruitBuilder().withPhones(List.of(new Phone(VALID_PHONE_AMY)));
        assertThrows(DataEntryNotFoundException.class, () ->
                builder.removePhones(List.of(new Phone("87654321"))));
    }

    @Test
    void removePhones_withoutSet_throwsException() {
        RecruitBuilder builder = new RecruitBuilder();
        assertThrows(IllegalRecruitBuilderActionException.class, () ->
                builder.removePhones(List.of(new Phone(VALID_PHONE_AMY))));
    }

    // Email
    @Test
    void withEmail_setsSingleEmail() {
        RecruitBuilder builder = new RecruitBuilder().withEmail(new Email(VALID_EMAIL_AMY));
        assertTrue(builder.hasBeenModified());
    }

    @Test
    void withEmail_null_doesNothing() {
        RecruitBuilder builder = new RecruitBuilder();
        builder.withEmail(null);
        assertFalse(builder.hasBeenModified());
    }

    @Test
    void appendEmails_addsSuccessfully() {
        RecruitBuilder builder = new RecruitBuilder().withEmail(new Email(VALID_EMAIL_AMY));
        builder.appendEmails(List.of(new Email(VALID_EMAIL_BOB)));

        assertTrue(builder.hasBeenModified());
        RecruitBuilder solution = new RecruitBuilder().withEmails(
                List.of(new Email(VALID_EMAIL_AMY), new Email(VALID_EMAIL_BOB)));

        assertHasSameData(builder, solution);
    }

    @Test
    void appendEmails_duplicate_throwsException() {
        RecruitBuilder builder = new RecruitBuilder().withEmails(List.of(new Email(VALID_EMAIL_AMY)));
        assertThrows(DataEntryAlreadyExistsException.class, () ->
                builder.appendEmails(List.of(new Email(VALID_EMAIL_AMY))));
    }

    @Test
    void removeEmails_removesSuccessfully() {
        RecruitBuilder builder = new RecruitBuilder()
                .withEmails(List.of(new Email(VALID_EMAIL_AMY), new Email(VALID_EMAIL_BOB)));
        builder.removeEmails(List.of(new Email(VALID_EMAIL_BOB)));

        assertTrue(builder.hasBeenModified());
        RecruitBuilder solution = new RecruitBuilder().withEmail(new Email(VALID_EMAIL_AMY));

        assertHasSameData(builder, solution);
    }

    @Test
    void removeEmails_missing_throwsException() {
        RecruitBuilder builder = new RecruitBuilder().withEmails(List.of(new Email(VALID_EMAIL_AMY)));
        assertThrows(DataEntryNotFoundException.class, () ->
                builder.removeEmails(List.of(new Email("bob@example.com"))));
    }

    @Test
    void removeEmails_withoutSet_throwsException() {
        RecruitBuilder builder = new RecruitBuilder();
        assertThrows(IllegalRecruitBuilderActionException.class, () ->
                builder.removeEmails(List.of(new Email(VALID_EMAIL_AMY))));
    }


    // Address
    @Test
    void withAddress_setsSingleAddress() {
        RecruitBuilder builder = new RecruitBuilder().withAddress(new Address(VALID_ADDRESS_AMY));
        assertTrue(builder.hasBeenModified());
    }

    @Test
    void withAddress_null_doesNothing() {
        RecruitBuilder builder = new RecruitBuilder();
        builder.withAddress(null);
        assertFalse(builder.hasBeenModified());
    }

    @Test
    void appendAddresses_addsSuccessfully() {
        RecruitBuilder builder = new RecruitBuilder().withAddress(new Address(VALID_ADDRESS_AMY));
        builder.appendAddresses(List.of(new Address(VALID_ADDRESS_BOB)));

        assertTrue(builder.hasBeenModified());
        RecruitBuilder solution = new RecruitBuilder().withAddresses(
                List.of(new Address(VALID_ADDRESS_AMY), new Address(VALID_ADDRESS_BOB)));

        assertHasSameData(builder, solution);
    }

    @Test
    void appendAddresses_duplicate_throwsException() {
        RecruitBuilder builder = new RecruitBuilder().withAddresses(List.of(new Address(VALID_ADDRESS_AMY)));
        assertThrows(DataEntryAlreadyExistsException.class, () ->
                builder.appendAddresses(List.of(new Address(VALID_ADDRESS_AMY))));
    }

    @Test
    void removeAddresses_removesSuccessfully() {
        RecruitBuilder builder = new RecruitBuilder()
                .withAddresses(List.of(new Address(VALID_ADDRESS_AMY), new Address(VALID_ADDRESS_BOB)));
        builder.removeAddresses(List.of(new Address(VALID_ADDRESS_BOB)));

        assertTrue(builder.hasBeenModified());
        RecruitBuilder solution = new RecruitBuilder().withAddress(new Address(VALID_ADDRESS_AMY));

        assertHasSameData(builder, solution);
    }

    @Test
    void removeAddresses_missing_throwsException() {
        RecruitBuilder builder = new RecruitBuilder().withAddresses(List.of(new Address(VALID_ADDRESS_AMY)));
        assertThrows(DataEntryNotFoundException.class, () ->
                builder.removeAddresses(List.of(new Address("789 Unknown Street"))));
    }

    @Test
    void removeAddresses_withoutSet_throwsException() {
        RecruitBuilder builder = new RecruitBuilder();
        assertThrows(IllegalRecruitBuilderActionException.class, () ->
                builder.removeAddresses(List.of(new Address(VALID_ADDRESS_AMY))));
    }


    // Tag
    @Test
    void withTag_setsSingleTag() {
        RecruitBuilder builder = new RecruitBuilder().withTag(new Tag(VALID_TAG_FRIEND));
        assertTrue(builder.hasBeenModified());
    }

    @Test
    void withTag_null_doesNothing() {
        RecruitBuilder builder = new RecruitBuilder();
        builder.withTag(null);
        assertFalse(builder.hasBeenModified());
    }

    @Test
    void appendTags_addsSuccessfully() {
        RecruitBuilder builder = new RecruitBuilder().withTag(new Tag(VALID_TAG_FRIEND));
        builder.appendTags(List.of(new Tag(VALID_TAG_HUSBAND)));

        assertTrue(builder.hasBeenModified());
        RecruitBuilder solution = new RecruitBuilder().withTags(
                List.of(new Tag(VALID_TAG_FRIEND), new Tag(VALID_TAG_HUSBAND)));

        assertHasSameData(builder, solution);
    }

    @Test
    void appendTags_duplicate_throwsException() {
        RecruitBuilder builder = new RecruitBuilder().withTags(List.of(new Tag(VALID_TAG_FRIEND)));
        assertThrows(TagAlreadyExistsException.class, () ->
                builder.appendTags(List.of(new Tag(VALID_TAG_FRIEND))));
    }

    @Test
    void removeTags_removesSuccessfully() {
        RecruitBuilder builder = new RecruitBuilder()
                .withTags(List.of(new Tag(VALID_TAG_FRIEND), new Tag(VALID_TAG_HUSBAND)));
        builder.removeTags(List.of(new Tag(VALID_TAG_HUSBAND)));

        assertTrue(builder.hasBeenModified());
        RecruitBuilder solution = new RecruitBuilder().withTag(new Tag(VALID_TAG_FRIEND));

        assertHasSameData(builder, solution);
    }

    @Test
    void removeTags_missing_throwsException() {
        RecruitBuilder builder = new RecruitBuilder().withTags(List.of(new Tag(VALID_TAG_FRIEND)));
        assertThrows(TagNotFoundException.class, () ->
                builder.removeTags(List.of(new Tag("colleague"))));
    }

    @Test
    void removeTags_withoutSet_throwsException() {
        RecruitBuilder builder = new RecruitBuilder();
        assertThrows(IllegalRecruitBuilderActionException.class, () ->
                builder.removeTags(List.of(new Tag(VALID_TAG_FRIEND))));
    }

    // --- Description handling ---
    @Test
    void appendDescription_combinesDescriptions() {
        RecruitBuilder builder = new RecruitBuilder().withDescription(Description.createDescription("   Hello"));
        builder.appendDescription(Description.createDescription("   World   "));
        assertTrue(builder.toString().contains("Hello"));
        assertTrue(builder.toString().contains("World"));

        RecruitBuilder solution = new RecruitBuilder().withDescription(Description.createDescription("Hello World"));
        assertHasSameData(builder, solution);
    }

    @Test
    void removeDescription_removesDescriptions() {
        RecruitBuilder builder = new RecruitBuilder().withDescription(Description.createDescription("Hello"));
        builder.removeDescription();

        RecruitBuilder solution = new RecruitBuilder().withDescription(Description.createEmptyDescription());
        assertHasSameData(builder, solution);
    }

    // Override Tests

    @Test
    void override_replacesNonNullFields() {
        RecruitBuilder base = new RecruitBuilder()
                .withName(new Name(VALID_NAME_AMY))
                .withPhone(new Phone(VALID_PHONE_AMY));
        RecruitBuilder override = new RecruitBuilder()
                .withName(new Name(VALID_NAME_BOB))
                .withPhone(new Phone(VALID_PHONE_BOB));
        base.override(override);

        RecruitBuilder solution = new RecruitBuilder()
                .withName(new Name(VALID_NAME_BOB))
                .withPhone(new Phone(VALID_PHONE_BOB));

        assertHasSameData(base, solution);
    }

    @Test
    void override_replacesNullFields() {
        RecruitBuilder base = new RecruitBuilder()
                .withName(new Name(VALID_NAME_AMY))
                .withPhone(new Phone(VALID_PHONE_AMY));
        RecruitBuilder override = new RecruitBuilder()
                .withAddress(new Address(VALID_ADDRESS_BOB));
        base.override(override);

        RecruitBuilder solution = new RecruitBuilder()
                .withName(new Name(VALID_NAME_AMY))
                .withPhone(new Phone(VALID_PHONE_AMY))
                .withAddress(new Address(VALID_ADDRESS_BOB));

        assertHasSameData(base, solution);
    }

    @Test
    void override_replacesMixedFields() {
        RecruitBuilder base = new RecruitBuilder()
                .withName(new Name(VALID_NAME_AMY))
                .withPhone(new Phone(VALID_PHONE_AMY));
        RecruitBuilder override = new RecruitBuilder()
                .withName(new Name(VALID_NAME_BOB))
                .withAddress(new Address(VALID_ADDRESS_BOB));
        base.override(override);

        RecruitBuilder solution = new RecruitBuilder()
                .withName(new Name(VALID_NAME_BOB))
                .withPhone(new Phone(VALID_PHONE_AMY))
                .withAddress(new Address(VALID_ADDRESS_BOB));

        assertHasSameData(base, solution);
    }

    @Test
    void override_maximalOverride() {
        RecruitBuilder base = new RecruitBuilder()
                .withName(new Name(VALID_NAME_AMY))
                .withPhone(new Phone(VALID_PHONE_AMY))
                .withEmail(new Email(VALID_EMAIL_AMY))
                .withAddress(new Address(VALID_ADDRESS_AMY))
                .withDescription(Description.createDescription(VALID_DESCRIPTION_AMY))
                .withTag(new Tag(VALID_TAG_FRIEND));

        RecruitBuilder override = new RecruitBuilder()
                .withName(new Name(VALID_NAME_BOB))
                .withPhone(new Phone(VALID_PHONE_BOB))
                .withEmail(new Email(VALID_EMAIL_BOB))
                .withAddress(new Address(VALID_ADDRESS_BOB))
                .withDescription(Description.createDescription(VALID_DESCRIPTION_BOB))
                .withTag(new Tag(VALID_TAG_HUSBAND));
        base.override(override);

        RecruitBuilder solution = new RecruitBuilder(override);

        assertHasSameData(base, solution);
    }

    // Append Tests
    @Test
    void append_mergesDifferentFields() {
        RecruitBuilder base = new RecruitBuilder().withName(new Name(VALID_NAME_AMY));
        RecruitBuilder toAppend = new RecruitBuilder().withPhone(new Phone(VALID_PHONE_AMY));
        base.append(toAppend);


        RecruitBuilder solution = new RecruitBuilder()
                .withName(new Name(VALID_NAME_AMY))
                .withPhone(new Phone(VALID_PHONE_AMY));

        assertHasSameData(base, solution);
    }

    @Test
    void append_appendsSameFields() {
        RecruitBuilder base = new RecruitBuilder().withName(new Name(VALID_NAME_AMY));
        RecruitBuilder toAppend = new RecruitBuilder().withName(new Name(VALID_NAME_BOB));
        base.append(toAppend);

        RecruitBuilder solution = new RecruitBuilder()
                .withNames(List.of(new Name(VALID_NAME_AMY), new Name(VALID_NAME_BOB)));

        assertHasSameData(base, solution);
    }

    @Test
    void append_appendsMixedFields() {
        RecruitBuilder base = new RecruitBuilder()
                .withName(new Name(VALID_NAME_AMY))
                .withPhone(new Phone(VALID_PHONE_AMY));

        RecruitBuilder toAppend = new RecruitBuilder()
                .withPhone(new Phone(VALID_PHONE_BOB))
                .withEmail(new Email(VALID_EMAIL_BOB));
        base.append(toAppend);

        RecruitBuilder solution = new RecruitBuilder()
                .withName(new Name(VALID_NAME_AMY))
                .withPhones(List.of(new Phone(VALID_PHONE_AMY), new Phone(VALID_PHONE_BOB)))
                .withEmail(new Email(VALID_EMAIL_BOB));

        assertHasSameData(base, solution);
    }

    @Test
    void append_maximalAppend() {
        RecruitBuilder base = new RecruitBuilder()
                .withName(new Name(VALID_NAME_AMY))
                .withPhone(new Phone(VALID_PHONE_AMY))
                .withEmail(new Email(VALID_EMAIL_AMY))
                .withAddress(new Address(VALID_ADDRESS_AMY))
                .withDescription(Description.createDescription(VALID_DESCRIPTION_AMY))
                .withTag(new Tag(VALID_TAG_FRIEND));

        RecruitBuilder toAppend = new RecruitBuilder()
                .withName(new Name(VALID_NAME_BOB))
                .withPhone(new Phone(VALID_PHONE_BOB))
                .withEmail(new Email(VALID_EMAIL_BOB))
                .withAddress(new Address(VALID_ADDRESS_BOB))
                .withDescription(Description.createDescription(VALID_DESCRIPTION_BOB))
                .withTag(new Tag(VALID_TAG_HUSBAND));
        base.append(toAppend);

        RecruitBuilder solution = new RecruitBuilder()
                .withNames(List.of(new Name(VALID_NAME_AMY), new Name(VALID_NAME_BOB)))
                .withPhones(List.of(new Phone(VALID_PHONE_AMY), new Phone(VALID_PHONE_BOB)))
                .withEmails(List.of(new Email(VALID_EMAIL_AMY), new Email(VALID_EMAIL_BOB)))
                .withAddresses(List.of(new Address(VALID_ADDRESS_AMY), new Address(VALID_ADDRESS_BOB)))
                .withDescription(Description.createDescription(VALID_DESCRIPTION_AMY)
                        .appendDescription(Description.createDescription(VALID_DESCRIPTION_BOB)))
                .withTags(List.of(new Tag(VALID_TAG_FRIEND), new Tag(VALID_TAG_HUSBAND)));

        assertHasSameData(base, solution);
    }

    // Remove Tests

    @Test
    void remove_removesDifferentFields() {
        RecruitBuilder base = new RecruitBuilder()
                .withName(new Name(VALID_NAME_AMY))
                .withPhone(new Phone(VALID_PHONE_AMY));

        RecruitBuilder toRemove = new RecruitBuilder().withPhone(new Phone(VALID_PHONE_AMY));
        base.remove(toRemove);

        RecruitBuilder solution = new RecruitBuilder().withName(new Name(VALID_NAME_AMY)).withPhones(List.of());

        assertHasSameData(base, solution);
    }

    @Test
    void remove_removesSameFields() {
        RecruitBuilder base = new RecruitBuilder()
                .withNames(List.of(new Name(VALID_NAME_AMY), new Name(VALID_NAME_BOB)));
        RecruitBuilder toRemove = new RecruitBuilder().withName(new Name(VALID_NAME_BOB));
        base.remove(toRemove);

        RecruitBuilder solution = new RecruitBuilder().withName(new Name(VALID_NAME_AMY));

        assertHasSameData(base, solution);
    }

    @Test
    void remove_removesMixedFields() {
        RecruitBuilder base = new RecruitBuilder()
                .withName(new Name(VALID_NAME_AMY))
                .withPhones(List.of(new Phone(VALID_PHONE_AMY), new Phone(VALID_PHONE_BOB)))
                .withEmail(new Email(VALID_EMAIL_AMY));

        RecruitBuilder toRemove = new RecruitBuilder()
                .withPhone(new Phone(VALID_PHONE_BOB))
                .withEmail(new Email(VALID_EMAIL_AMY));
        base.remove(toRemove);

        RecruitBuilder solution = new RecruitBuilder()
                .withName(new Name(VALID_NAME_AMY))
                .withPhone(new Phone(VALID_PHONE_AMY))
                .withEmails(List.of());

        assertHasSameData(base, solution);
    }

    @Test
    void remove_maximalRemove() {
        RecruitBuilder base = new RecruitBuilder()
                .withNames(List.of(new Name(VALID_NAME_AMY), new Name(VALID_NAME_BOB)))
                .withPhones(List.of(new Phone(VALID_PHONE_AMY), new Phone(VALID_PHONE_BOB)))
                .withEmails(List.of(new Email(VALID_EMAIL_AMY), new Email(VALID_EMAIL_BOB)))
                .withAddresses(List.of(new Address(VALID_ADDRESS_AMY), new Address(VALID_ADDRESS_BOB)))
                .withDescription(Description.createDescription(VALID_DESCRIPTION_AMY)
                        .appendDescription(Description.createDescription(VALID_DESCRIPTION_BOB)))
                .withTags(List.of(new Tag(VALID_TAG_FRIEND), new Tag(VALID_TAG_HUSBAND)));

        RecruitBuilder toRemove = new RecruitBuilder()
                .withName(new Name(VALID_NAME_BOB))
                .withPhone(new Phone(VALID_PHONE_BOB))
                .withEmail(new Email(VALID_EMAIL_BOB))
                .withAddress(new Address(VALID_ADDRESS_BOB))
                .withDescription(Description.createDescription(VALID_DESCRIPTION_BOB))
                .withTag(new Tag(VALID_TAG_HUSBAND));
        base.remove(toRemove);

        RecruitBuilder solution = new RecruitBuilder()
                .withName(new Name(VALID_NAME_AMY))
                .withPhone(new Phone(VALID_PHONE_AMY))
                .withEmail(new Email(VALID_EMAIL_AMY))
                .withAddress(new Address(VALID_ADDRESS_AMY))
                .withDescription(Description.createEmptyDescription())
                .withTag(new Tag(VALID_TAG_FRIEND));

        assertHasSameData(base, solution);
    }

    @Test
    void remove_clearsDescription_success() {
        RecruitBuilder base = new RecruitBuilder()
                .withName(new Name(VALID_NAME_AMY))
                .withDescription(Description.createDescription(VALID_DESCRIPTION_AMY));

        RecruitBuilder toRemove = new RecruitBuilder()
                .withDescription(Description.createDescription("To remove"));

        base.remove(toRemove);


        RecruitBuilder solution = new RecruitBuilder()
                .withName(new Name(VALID_NAME_AMY))
                .withDescription(Description.createEmptyDescription());

        assertHasSameData(base, solution);
    }


    // Build Tests
    @Test
    void build_minimumValidRecruit_success() {
        Recruit recruit = new RecruitBuilder()
                .withName(new Name(VALID_NAME_AMY))
                .build();

        assertNotNull(recruit.getID());
        assertTrue(recruit.getNames().contains(new Name(VALID_NAME_AMY)));
        assertTrue(recruit.getPhones().isEmpty());
        assertTrue(recruit.getTags().isEmpty());
    }

    @Test
    void build_noName_throwsInvalidRecruitException() {
        RecruitBuilder builder = new RecruitBuilder();
        assertThrows(NoNameRecruitException.class, builder::build);
    }

    // equals() and toString()

    @Test
    void equals_sameFields_true() {
        Name tempName = new Name(VALID_NAME_AMY);
        RecruitBuilder a = new RecruitBuilder().withName(tempName);
        RecruitBuilder b = new RecruitBuilder().withName(new Name(VALID_NAME_AMY));
        RecruitBuilder c = new RecruitBuilder().withName(new Name(VALID_NAME_AMY));
        assertHasSameData(a, b);
        assertHasSameData(b, c);
    }

    @Test
    void equals_differentFields_false() {
        RecruitBuilder a = new RecruitBuilder().withName(new Name(VALID_NAME_AMY));
        RecruitBuilder b = new RecruitBuilder().withName(new Name(VALID_NAME_BOB));
        assertNotEquals(a, b);
    }

    @Test
    void toString_includesFields() {
        RecruitBuilder builder = new RecruitBuilder().withName(new Name(VALID_NAME_AMY));
        String s = builder.toString();
        assertTrue(s.contains("Amy"));
    }

    @Test
    void hasSameData_sameFields_returnsTrue() {
        RecruitBuilder b1 = new RecruitBuilder()
                .withName(new Name(VALID_NAME_AMY))
                .withPhone(new Phone(VALID_PHONE_AMY))
                .withEmail(new Email(VALID_EMAIL_AMY))
                .withAddress(new Address(VALID_ADDRESS_AMY))
                .withDescription(Description.createDescription(VALID_DESCRIPTION_AMY))
                .withTag(new Tag(VALID_TAG_FRIEND));

        RecruitBuilder b2 = new RecruitBuilder(b1);
        assertTrue(b1.hasSameData(b2));
        assertEquals(b1, b2);
    }

    // todo: Write better automated toString tests
    @Test
    void toString_containsKeyFields() {
        RecruitBuilder builder = new RecruitBuilder()
                .withName(new Name(VALID_NAME_AMY))
                .withEmail(new Email(VALID_EMAIL_AMY))
                .withDescription(Description.createDescription(VALID_DESCRIPTION_AMY));
        String result = builder.toString();
        assertTrue(result.contains("name"));
        assertTrue(result.contains("email"));
        assertTrue(result.contains("description"));
    }

    private static void assertHasSameData(RecruitBuilder builder1, RecruitBuilder builder2) {
        assertTrue(builder1.hasSameData(builder2));
        assertTrue(builder2.hasSameData(builder1));
    }
}
