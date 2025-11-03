package seedu.address.logic.parser;


import static seedu.address.logic.parser.CliSyntax.PREFIX_CLEAR_CONFIRM;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.ClearCommand;

public class ClearCommandParserTest {
    private static final ClearCommandParser parser = new ClearCommandParser();

    @Test
    public void parse_noArgs_exceptionThrown() {
        assertParseFailure(parser, "" , ClearCommand.MESSAGE_USAGE);
    }

    @Test
    public void parse_withConfirmFlagOnly_success() {
        ClearCommand expectedClearCommand = new ClearCommand();
        assertParseSuccess(parser, " " + PREFIX_CLEAR_CONFIRM, expectedClearCommand);
    }

    @Test
    public void parse_withConfirmFlagAndPreamble_exceptionThrown() {
        assertParseFailure(parser,
                " " + "3" + " " + PREFIX_CLEAR_CONFIRM, Messages.MESSAGE_PREAMBLE_NOT_ACCEPTED);
    }

    @Test
    public void parse_withConfirmFlagAndValue_exceptionThrown() {
        assertParseFailure(parser,
                " " + PREFIX_CLEAR_CONFIRM + " " + "5",
                Messages.MESSAGE_NON_VALUE_ACCEPTING_FLAGS + PREFIX_CLEAR_CONFIRM);
    }
}
