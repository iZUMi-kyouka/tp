package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLEAR_CONFIRM;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String OPERATION_DESCRIPTOR = "deletion of all recruits";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes all recruits in the address book. "
            + "\nWhen used without any flag, this command simply displays this help message."
            + "\nUse " + PREFIX_CLEAR_CONFIRM +  " to confirm deletion of all recruits. ";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setAddressBook(new AddressBook());
        model.commitAddressBook(OPERATION_DESCRIPTOR);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
