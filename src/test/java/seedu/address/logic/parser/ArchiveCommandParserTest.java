package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ArchiveCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.testutil.TypicalIDs;

public class ArchiveCommandParserTest {

    private final ArchiveCommandParser parser = new ArchiveCommandParser();

    @Test
    public void parse_validArgsIndex_returnsArchiveCommand() {
        // no leading/trailing whitespaces
        ArchiveCommand expectedCommand = new ArchiveCommand(Index.fromOneBased(1));
        assertParseSuccess(parser, "1", expectedCommand);

        // with extra whitespaces
        assertParseSuccess(parser, "   1   ", expectedCommand);
    }

    @Test
    public void parse_validArgsUUID_returnsArchiveCommand() {
        assertParseSuccess(parser, "eac9b117-2ded-42c3-9264-ccf3dfaaa950",
                new ArchiveCommand(TypicalIDs.ID_FIRST_RECRUIT));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // non-integer input
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ArchiveCommand.MESSAGE_USAGE));

        // negative index
        assertParseFailure(parser, "-1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ArchiveCommand.MESSAGE_USAGE));

        // zero index
        assertParseFailure(parser, "0",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ArchiveCommand.MESSAGE_USAGE));

        // empty input
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ArchiveCommand.MESSAGE_USAGE));
    }
}
