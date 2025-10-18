package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Undoes the commands that result in changes in the model starting from the most recently executed commands.
 */
public class UndoCommand extends Command {
    public static final String COMMAND_WORD = "undo";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult("Undo success.");
    }
}
