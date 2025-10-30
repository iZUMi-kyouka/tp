package seedu.address.logic.parser;


import static seedu.address.logic.parser.CliSyntax.PREFIX_CLEAR_CONFIRM;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ClearCommand;

public class ClearCommandParserTest {
    private static final ClearCommandParser parser = new ClearCommandParser();

    @Test
    public void parse_noArgs_exceptionThrown() {
        ClearCommand expectedClearCommand = new ClearCommand();
        assertParseFailure(parser, ClearCommand.COMMAND_WORD , ClearCommand.MESSAGE_USAGE);
    }

    @Test
    public void parse_withConfirmFlag_exceptionThrown() {
        ClearCommand expectedClearCommand = new ClearCommand();
        assertParseSuccess(parser, ClearCommand.COMMAND_WORD + " " + PREFIX_CLEAR_CONFIRM, expectedClearCommand);
    }
}
