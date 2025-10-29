package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CLEAR_CONFIRM;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class ClearCommandParser implements Parser<ClearCommand> {

    @Override
    public ClearCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_CLEAR_CONFIRM);
        
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_CLEAR_CONFIRM);
        if (!argMultimap.hasValue(PREFIX_CLEAR_CONFIRM)) {
            throw new ParseException(ClearCommand.MESSAGE_USAGE);
        }

        // TODO: restrict values from being passed in to the flag; after merge of other related PR
        return new ClearCommand();
    }
}
