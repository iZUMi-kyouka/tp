package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ArchiveCommand;
import seedu.address.logic.commands.UnarchiveCommand;
import seedu.address.testutil.TypicalIDs;

public class UnarchiveCommandParserTest {

    private final UnarchiveCommandParser parser = new UnarchiveCommandParser();

    @Test
    public void parse_validArgs_returnsUnarchiveCommand() {
        // no leading/trailing whitespaces
        UnarchiveCommand expectedCommand = new UnarchiveCommand(Index.fromOneBased(1));
        assertParseSuccess(parser, "1", expectedCommand);

        // with extra whitespaces
        assertParseSuccess(parser, "   1   ", expectedCommand);
    }

    @Test
    public void parse_validArgsUUID_returnsUnarchiveCommand() {
        assertParseSuccess(parser, "eac9b117-2ded-42c3-9264-ccf3dfaaa950",
                new UnarchiveCommand(TypicalIDs.ID_FIRST_RECRUIT));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // non-integer input
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnarchiveCommand.MESSAGE_USAGE));

        // negative index
        assertParseFailure(parser, "-1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnarchiveCommand.MESSAGE_USAGE));

        // zero index
        assertParseFailure(parser, "0",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnarchiveCommand.MESSAGE_USAGE));

        // empty input
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnarchiveCommand.MESSAGE_USAGE));
    }
}
