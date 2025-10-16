package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.SEARCH_PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.recruit.FieldContainsKeywordsPredicate;
import seedu.address.model.recruit.NestedOrPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new NestedOrPredicate(
                        new FieldContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"), SEARCH_PREFIX_NAME)));
        assertParseSuccess(parser, "find -n Alice|Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "find \n -n Alice| \n \t Bob  \t", expectedFindCommand);
    }

}
