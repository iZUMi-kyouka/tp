package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import seedu.address.commons.util.CsvUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.recruit.Recruit;

/**
 * Lists all recruits in the address book to the user.
 */
public class ExportCommand extends Command {
    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_SUCCESS = "Exported all recruits to %s";

    public static final String MESSAGE_FAILURE =
            "Could not export all recruits due to the following error %s";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Exports all recruit information to the specified file.\n"
            + "Parameters: FILEPATH (must be a valid file path ending with .csv, absolute or relative)\n"
            + "Example: " + COMMAND_WORD + "./data/recruits.csv";
    private final Path filePath;

    /**
     * Creates an ExportCommand to add the specified {@code Person}
     */
    public ExportCommand(Path filePath) {
        requireNonNull(filePath);
        this.filePath = filePath;
    }

    /**
     * Creates an ExportCommand to add the specified {@code Person}
     */
    public ExportCommand() {
        this.filePath = null;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Path filePath = this.filePath;
        if (this.filePath == null) {
            filePath = model.getUserPrefs().getExportFilePath();
        }
        List<Recruit> recruits = model.getAddressBook().getRecruitList();
        assert recruits != null;
        try {
            CsvUtil.serializeRecruitsToCsvFile(filePath, recruits);
        } catch (IOException e) {
            throw new CommandException(String.format(MESSAGE_FAILURE, e.getMessage()), e);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, filePath));
    }
}
