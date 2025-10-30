package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;
import java.util.UUID;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        Optional<UUID> idOpt = tryParseID(trimmedArgs);
        if (idOpt.isPresent()) {
            return new DeleteCommand(idOpt.get());
        }

        Optional<Index> indexOpt = tryParseIndex(trimmedArgs);
        if (indexOpt.isPresent()) {
            return new DeleteCommand(indexOpt.get());
        }

        throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE)
        );
    }

    private Optional<UUID> tryParseID(String s) {
        try {
            return Optional.of(ParserUtil.parseID(s));
        } catch (ParseException e) {
            return Optional.empty();
        }
    }

    private Optional<Index> tryParseIndex(String s) {
        try {
            return Optional.of(ParserUtil.parseIndex(s));
        } catch (ParseException e) {
            return Optional.empty();
        }
    }

}
