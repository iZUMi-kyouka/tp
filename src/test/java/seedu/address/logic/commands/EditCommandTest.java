package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showRecruitAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_RECRUIT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_RECRUIT;
import static seedu.address.testutil.TypicalRecruits.ALICE;
import static seedu.address.testutil.TypicalRecruits.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditCommand.EditRecruitDescriptor;
import seedu.address.logic.commands.EditCommand.EditRecruitDescriptor.EditRecruitOperation;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.recruit.Recruit;
import seedu.address.testutil.EditRecruitDescriptorBuilder;
import seedu.address.testutil.RecruitBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_overwriteOperationAllFieldsSpecifiedUnfilteredList_success() {
        Recruit editedRecruit = new RecruitBuilder(ALICE).build();
        EditRecruitDescriptor descriptor = new EditRecruitDescriptorBuilder(editedRecruit).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_RECRUIT, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_RECRUIT_SUCCESS,
                editCommand.formatDelta(editedRecruit, editedRecruit));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setRecruit(model.getFilteredRecruitList().get(0), editedRecruit);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_appendOperationAllFieldsSpecifiedUnfilteredList_success() {
        Recruit initialRecruit = new RecruitBuilder(ALICE).build();
        Recruit expectedRecruit = new RecruitBuilder(initialRecruit)
                .withAdditionalNames(VALID_NAME_AMY, VALID_NAME_BOB)
                .withAdditionalPhones(VALID_PHONE_AMY, VALID_PHONE_BOB)
                .withAdditionalEmails(VALID_EMAIL_AMY, VALID_EMAIL_BOB)
                .withAdditionalAddresses(VALID_ADDRESS_AMY, VALID_ADDRESS_BOB)
                .build();

        EditRecruitDescriptor descriptor = new EditRecruitDescriptorBuilder()
                .withOperation(EditRecruitOperation.APPEND)
                .withNames(VALID_NAME_AMY, VALID_NAME_BOB)
                .withPhones(VALID_PHONE_AMY, VALID_PHONE_BOB)
                .withEmails(VALID_EMAIL_AMY, VALID_EMAIL_BOB)
                .withAddresses(VALID_ADDRESS_AMY, VALID_ADDRESS_BOB)
                .build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_RECRUIT, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_RECRUIT_SUCCESS,
                editCommand.formatDelta(initialRecruit, expectedRecruit));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setRecruit(model.getFilteredRecruitList().get(0), initialRecruit);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_overwriteOperationSomeFieldsSpecifiedUnfilteredList_success() {
        Index indexLastRecruit = Index.fromOneBased(model.getFilteredRecruitList().size());
        Recruit lastRecruit = model.getFilteredRecruitList().get(indexLastRecruit.getZeroBased());

        RecruitBuilder recruitInList = new RecruitBuilder(lastRecruit);
        Recruit initialRecruit = recruitInList.build();
        Recruit editedRecruit = recruitInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditRecruitDescriptor descriptor = new EditRecruitDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommand = new EditCommand(indexLastRecruit, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_RECRUIT_SUCCESS,
                editCommand.formatDelta(initialRecruit, editedRecruit));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setRecruit(lastRecruit, editedRecruit);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_overwriteOperationNoFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_RECRUIT, new EditRecruitDescriptor());
        Recruit editedRecruit = model.getFilteredRecruitList().get(INDEX_FIRST_RECRUIT.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_RECRUIT_SUCCESS,
                editCommand.formatDelta(editedRecruit, editedRecruit));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showRecruitAtIndex(model, INDEX_FIRST_RECRUIT);

        Recruit recruitInFilteredList = model.getFilteredRecruitList().get(INDEX_FIRST_RECRUIT.getZeroBased());
        Recruit initialRecruit = new RecruitBuilder(recruitInFilteredList).build();
        Recruit editedRecruit = new RecruitBuilder(recruitInFilteredList).withName(VALID_NAME_BOB).build();

        EditRecruitDescriptor descriptor = new EditRecruitDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_RECRUIT, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_RECRUIT_SUCCESS,
                editCommand.formatDelta(initialRecruit, editedRecruit));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setRecruit(model.getFilteredRecruitList().get(0), editedRecruit);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredRecruitList().size() + 1);
        EditRecruitDescriptor descriptor = new EditRecruitDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_RECRUIT_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showRecruitAtIndex(model, INDEX_FIRST_RECRUIT);
        Index outOfBoundIndex = INDEX_SECOND_RECRUIT;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getRecruitList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditRecruitDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_RECRUIT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_RECRUIT, DESC_AMY);

        // same values -> returns true
        EditRecruitDescriptor copyDescriptor = new EditRecruitDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_RECRUIT, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_RECRUIT, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_RECRUIT, DESC_BOB)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditRecruitDescriptor editRecruitDescriptor = new EditRecruitDescriptor();
        EditCommand editCommand = new EditCommand(index, editRecruitDescriptor);
        String expected = EditCommand.class.getCanonicalName() + "{index=" + index + ", editPersonDescriptor="
                + editRecruitDescriptor + "}";
        assertEquals(expected, editCommand.toString());
    }

}
