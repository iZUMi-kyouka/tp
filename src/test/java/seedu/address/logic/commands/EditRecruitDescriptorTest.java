package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditCommand.EditRecruitDescriptor;
import seedu.address.model.recruit.data.Address;
import seedu.address.model.recruit.data.Description;
import seedu.address.model.recruit.data.Email;
import seedu.address.model.recruit.data.Name;
import seedu.address.model.recruit.data.Phone;
import seedu.address.model.tag.Tag;

public class EditRecruitDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditCommand.EditRecruitDescriptor descriptorWithSameValues = new EditRecruitDescriptor(DESC_AMY);
        assertTrue(DESC_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_AMY.equals(DESC_AMY));

        // null -> returns false
        assertFalse(DESC_AMY.equals(null));

        // different types -> returns false
        assertFalse(DESC_AMY.equals(5));

        // different values -> returns false
        assertFalse(DESC_AMY.equals(DESC_BOB));

        // different name -> returns false
        EditCommand.EditRecruitDescriptor editedAmy = new EditRecruitDescriptor(DESC_AMY);
        editedAmy.withName(new Name(VALID_NAME_BOB));
        assertFalse(DESC_AMY.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new EditRecruitDescriptor(DESC_AMY);
        editedAmy.withPhone(new Phone(VALID_PHONE_BOB));
        assertFalse(DESC_AMY.equals(editedAmy));

        // different email -> returns false
        editedAmy = new EditRecruitDescriptor(DESC_AMY);
        editedAmy.withEmail(new Email(VALID_EMAIL_BOB));
        assertFalse(DESC_AMY.equals(editedAmy));

        // different address -> returns false
        editedAmy = new EditRecruitDescriptor(DESC_AMY);
        editedAmy.withAddress(new Address(VALID_ADDRESS_BOB));
        assertFalse(DESC_AMY.equals(editedAmy));

        // different description -> returns false
        editedAmy = new EditRecruitDescriptor(DESC_AMY);
        editedAmy.withDescription(Description.createDescription(VALID_DESCRIPTION_BOB));
        assertFalse(DESC_AMY.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditRecruitDescriptor(DESC_AMY);
        editedAmy.withTag(new Tag(VALID_TAG_HUSBAND));
        assertFalse(DESC_AMY.equals(editedAmy));
    }

    @Test
    public void toStringMethod() {
        EditCommand.EditRecruitDescriptor editRecruitDescriptor = new EditCommand.EditRecruitDescriptor();
        String expected = EditCommand.EditRecruitDescriptor.class.getCanonicalName() + "{name="
                + editRecruitDescriptor.getNames().orElse(null) + ", phone="
                + editRecruitDescriptor.getPhones().orElse(null) + ", email="
                + editRecruitDescriptor.getEmails().orElse(null) + ", address="
                + editRecruitDescriptor.getDescription().orElse(null) + ", description="
                + editRecruitDescriptor.getAddresses().orElse(null) + ", tags="
                + editRecruitDescriptor.getTags().orElse(null) + ", operation="
                + editRecruitDescriptor.getOperation() + "}";
        assertEquals(expected, editRecruitDescriptor.toString());
    }
}
