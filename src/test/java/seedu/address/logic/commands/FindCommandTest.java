package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_RECRUITS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.parser.CliSyntax.SEARCH_PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.SEARCH_PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.SEARCH_PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.SEARCH_PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.SEARCH_PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.SEARCH_PREFIX_TAG;
import static seedu.address.testutil.TypicalRecruits.ALICE;
import static seedu.address.testutil.TypicalRecruits.BENSON;
import static seedu.address.testutil.TypicalRecruits.CARL;
import static seedu.address.testutil.TypicalRecruits.DANIEL;
import static seedu.address.testutil.TypicalRecruits.ELLE;
import static seedu.address.testutil.TypicalRecruits.FIONA;
import static seedu.address.testutil.TypicalRecruits.GEORGE;
import static seedu.address.testutil.TypicalRecruits.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.recruit.FieldContainsKeywordsPredicate;
import seedu.address.model.recruit.NestedOrPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        FieldContainsKeywordsPredicate firstPredicate =
                new FieldContainsKeywordsPredicate(Collections.singletonList("first"), SEARCH_PREFIX_NAME);
        FieldContainsKeywordsPredicate secondPredicate =
                new FieldContainsKeywordsPredicate(Collections.singletonList("second"), SEARCH_PREFIX_NAME);

        FindCommand findFirstCommand = new FindCommand(new NestedOrPredicate(firstPredicate));
        FindCommand findSecondCommand = new FindCommand(new NestedOrPredicate(secondPredicate));

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(new NestedOrPredicate(firstPredicate));
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_RECRUITS_LISTED_OVERVIEW, 0);
        FieldContainsKeywordsPredicate predicate = preparePredicate("|", SEARCH_PREFIX_NAME);
        FindCommand command = new FindCommand(new NestedOrPredicate(predicate));
        expectedModel.updateFilteredRecruitList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredRecruitList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_RECRUITS_LISTED_OVERVIEW, 3);
        FieldContainsKeywordsPredicate predicate = preparePredicate("Kurz|Elle|Kunz", SEARCH_PREFIX_NAME);
        FindCommand command = new FindCommand(new NestedOrPredicate(predicate));
        expectedModel.updateFilteredRecruitList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredRecruitList());
    }

    @Test
    public void execute_addressKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_RECRUITS_LISTED_OVERVIEW, 2);
        FieldContainsKeywordsPredicate predicate = preparePredicate("wall street|10th street", SEARCH_PREFIX_ADDRESS);
        FindCommand command = new FindCommand(new NestedOrPredicate(predicate));
        expectedModel.updateFilteredRecruitList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, DANIEL), model.getFilteredRecruitList());
    }

    @Test
    public void execute_addressZeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_RECRUITS_LISTED_OVERVIEW, 0);
        FieldContainsKeywordsPredicate predicate = preparePredicate("|", SEARCH_PREFIX_ADDRESS);
        FindCommand command = new FindCommand(new NestedOrPredicate(predicate));
        expectedModel.updateFilteredRecruitList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredRecruitList());
    }

    @Test
    public void execute_idKeywords_singlePersonFound() {
        String expectedMessage = String.format(MESSAGE_RECRUITS_LISTED_OVERVIEW, 1);
        FieldContainsKeywordsPredicate predicate = preparePredicate("eac9b117-2ded-42c3-9264-ccf3dfaaa954",
                SEARCH_PREFIX_ID);
        FindCommand command = new FindCommand(new NestedOrPredicate(predicate));
        expectedModel.updateFilteredRecruitList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ELLE), model.getFilteredRecruitList());
    }

    @Test
    public void execute_idZeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_RECRUITS_LISTED_OVERVIEW, 0);
        FieldContainsKeywordsPredicate predicate = preparePredicate("|", SEARCH_PREFIX_ID);
        FindCommand command = new FindCommand(new NestedOrPredicate(predicate));
        expectedModel.updateFilteredRecruitList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredRecruitList());
    }

    @Test
    public void execute_phoneKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_RECRUITS_LISTED_OVERVIEW, 2);
        FieldContainsKeywordsPredicate predicate = preparePredicate("9482224|9482427", SEARCH_PREFIX_PHONE);
        FindCommand command = new FindCommand(new NestedOrPredicate(predicate));
        expectedModel.updateFilteredRecruitList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ELLE, FIONA), model.getFilteredRecruitList());
    }

    @Test
    public void execute_phoneZeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_RECRUITS_LISTED_OVERVIEW, 0);
        FieldContainsKeywordsPredicate predicate = preparePredicate(" ", SEARCH_PREFIX_PHONE);
        FindCommand command = new FindCommand(new NestedOrPredicate(predicate));
        expectedModel.updateFilteredRecruitList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredRecruitList());
    }

    @Test
    public void execute_emailKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_RECRUITS_LISTED_OVERVIEW, 2);
        FieldContainsKeywordsPredicate predicate = preparePredicate("alice@example.com|anna@example.com",
                SEARCH_PREFIX_EMAIL);
        FindCommand command = new FindCommand(new NestedOrPredicate(predicate));
        expectedModel.updateFilteredRecruitList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, GEORGE), model.getFilteredRecruitList());
    }

    @Test
    public void execute_emailZeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_RECRUITS_LISTED_OVERVIEW, 0);
        FieldContainsKeywordsPredicate predicate = preparePredicate(" ", SEARCH_PREFIX_EMAIL);
        FindCommand command = new FindCommand(new NestedOrPredicate(predicate));
        expectedModel.updateFilteredRecruitList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredRecruitList());
    }

    @Test
    public void execute_tagKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_RECRUITS_LISTED_OVERVIEW, 3);
        FieldContainsKeywordsPredicate predicate = preparePredicate("friends|owesMoney", SEARCH_PREFIX_TAG);
        FindCommand command = new FindCommand(new NestedOrPredicate(predicate));
        expectedModel.updateFilteredRecruitList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, DANIEL), model.getFilteredRecruitList());
    }

    @Test
    public void execute_tagZeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_RECRUITS_LISTED_OVERVIEW, 0);
        FieldContainsKeywordsPredicate predicate = preparePredicate(" ", SEARCH_PREFIX_TAG);
        FindCommand command = new FindCommand(new NestedOrPredicate(predicate));
        expectedModel.updateFilteredRecruitList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredRecruitList());
    }

    @Test
    public void toStringMethod() {
        FieldContainsKeywordsPredicate namePredicate = new FieldContainsKeywordsPredicate(
                Arrays.asList("keyword"), SEARCH_PREFIX_NAME);
        NestedOrPredicate predicate = new NestedOrPredicate(namePredicate);
        FindCommand findCommand = new FindCommand(predicate);
        String expected = FindCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code FieldContainsKeywordsPredicate}.
     */
    private FieldContainsKeywordsPredicate preparePredicate(String userInput, Prefix prefix) {
        return new FieldContainsKeywordsPredicate(Arrays.asList(userInput.split("\\|")), prefix);
    }
}
