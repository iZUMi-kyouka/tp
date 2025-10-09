package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalRecruits.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.recruit.Recruit;
import seedu.address.testutil.RecruitBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newRecruit_success() {
        Recruit validRecruit = new RecruitBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addRecruit(validRecruit);

        assertCommandSuccess(new AddCommand(validRecruit), model,
                String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validRecruit)),
                expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Recruit recruitInList = model.getAddressBook().getRecruitList().get(0);
        assertCommandFailure(new AddCommand(recruitInList), model,
                AddCommand.MESSAGE_DUPLICATE_RECRUIT);
    }

}
