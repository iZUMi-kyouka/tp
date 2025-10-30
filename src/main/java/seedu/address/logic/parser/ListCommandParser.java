package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LIST_ALL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LIST_ARCHIVE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_RECRUITS;
import static seedu.address.model.Model.PREDICATE_SHOW_ARCHIVED_RECRUITS;
import static seedu.address.model.Model.PREDICATE_SHOW_UNARCHVIED_RECRUITS;

import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListCommandParser implements Parser<ListCommand> {
    /**
     * List Command supports 3 types of list actions
     * This enum represents the 3 different types of actions supported
     */
    public enum ListOperation {
        NORMAL_LIST_OP,
        ARCHIVE_LIST_OP,
        FULL_LIST_OP
    }
    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns a ListCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ListCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_LIST_ALL, PREFIX_LIST_ARCHIVE);
        verifySyntax(argMultimap);

        boolean isListAllPrefixPresent = argMultimap.getValue(PREFIX_LIST_ALL).isPresent();
        boolean isListArchivePrefixPresent = argMultimap.getValue(PREFIX_LIST_ARCHIVE).isPresent();

        // Both flags are provided (invalid input)
        if (isListAllPrefixPresent && isListArchivePrefixPresent) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        } else if (isListAllPrefixPresent) {
            return new ListCommand(PREDICATE_SHOW_ALL_RECRUITS, ListOperation.FULL_LIST_OP);
        } else if (isListArchivePrefixPresent) {
            return new ListCommand(PREDICATE_SHOW_ARCHIVED_RECRUITS, ListOperation.ARCHIVE_LIST_OP);
        } else {
            return new ListCommand(PREDICATE_SHOW_UNARCHVIED_RECRUITS, ListOperation.NORMAL_LIST_OP);
        }
    }

    /**
     * Verify that the list command is valid based on the following criteria:
     * 1. No preamble
     * 2. The value of any flag (e.g. -archived) is empty string
     */
    private void verifySyntax(ArgumentMultimap argMultimap) throws ParseException {
        if (!argMultimap.getPreamble().trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_LIST_ALL, PREFIX_LIST_ARCHIVE);
        argMultimap.verifyValuesOfAllPrefixesAreEmpty();
    }
}
