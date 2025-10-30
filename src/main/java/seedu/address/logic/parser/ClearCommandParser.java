package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CLEAR_CONFIRM;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ClearCommand object
 */
public class ClearCommandParser implements Parser<ClearCommand> {

    @Override
    public ClearCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_CLEAR_CONFIRM);

        argMultimap.verifyPreambleIsEmpty();
        if (!argMultimap.hasValue(PREFIX_CLEAR_CONFIRM)) {
            throw new ParseException(ClearCommand.MESSAGE_USAGE);
        }
        if (!argMultimap.hasSingleBlankValue(PREFIX_CLEAR_CONFIRM)) {
            throw new ParseException(Messages.MESSAGE_NON_VALUE_ACCEPTING_FLAGS + PREFIX_CLEAR_CONFIRM);
        }

        return new ClearCommand();
    }
}
