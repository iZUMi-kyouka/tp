package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.SORT_PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.SORT_PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.SORT_PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.SORT_PREFIX_PHONE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.SortCommand.SortCriterion;
import seedu.address.logic.commands.SortCommand.SortOrder;

/**
 * Contains tests for SortCommandParser.
 */
public class SortCommandParserTest {

    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_emptyArgs_success() {
        List<SortCriterion> criteria = new ArrayList<>();
        criteria.add(new SortCriterion(SORT_PREFIX_NAME, SortOrder.ASCENDING));

        SortCommand expectedCommand = new SortCommand(criteria);
        assertParseSuccess(parser, "", expectedCommand);
        assertParseSuccess(parser, "   ", expectedCommand);
    }

    @Test
    public void parse_ascOnly_success() {
        List<SortCriterion> criteria = new ArrayList<>();
        criteria.add(new SortCriterion(SORT_PREFIX_NAME, SortOrder.ASCENDING));

        SortCommand expectedCommand = new SortCommand(criteria);
        assertParseSuccess(parser, "asc", expectedCommand);
        assertParseSuccess(parser, "  asc  ", expectedCommand);
    }

    @Test
    public void parse_descOnly_success() {
        List<SortCriterion> criteria = new ArrayList<>();
        criteria.add(new SortCriterion(SORT_PREFIX_NAME, SortOrder.DESCENDING));

        SortCommand expectedCommand = new SortCommand(criteria);
        assertParseSuccess(parser, "desc", expectedCommand);
        assertParseSuccess(parser, "  desc  ", expectedCommand);
    }

    @Test
    public void parse_validSingleFieldAscending_success() {
        List<SortCriterion> criteria = new ArrayList<>();
        criteria.add(new SortCriterion(SORT_PREFIX_NAME, SortOrder.ASCENDING));

        SortCommand expectedCommand = new SortCommand(criteria);
        assertParseSuccess(parser, "-n asc", expectedCommand);
    }

    @Test
    public void parse_validSingleFieldDescending_success() {
        List<SortCriterion> criteria = new ArrayList<>();
        criteria.add(new SortCriterion(SORT_PREFIX_PHONE, SortOrder.DESCENDING));

        SortCommand expectedCommand = new SortCommand(criteria);
        assertParseSuccess(parser, "-p desc", expectedCommand);
    }

    @Test
    public void parse_validMultipleFields_success() {
        List<SortCriterion> criteria = new ArrayList<>();
        criteria.add(new SortCriterion(SORT_PREFIX_NAME, SortOrder.ASCENDING));
        criteria.add(new SortCriterion(SORT_PREFIX_PHONE, SortOrder.DESCENDING));
        criteria.add(new SortCriterion(SORT_PREFIX_ADDRESS, SortOrder.ASCENDING));

        SortCommand expectedCommand = new SortCommand(criteria);
        assertParseSuccess(parser, "-n asc -p desc -a asc", expectedCommand);
        assertParseSuccess(parser, "-n \t\r\nasc \r\n -p desc\t\t -a \tasc\n", expectedCommand);

    }

    @Test
    public void parse_validAllFields_success() {
        List<SortCriterion> criteria = new ArrayList<>();
        criteria.add(new SortCriterion(SORT_PREFIX_NAME, SortOrder.ASCENDING));
        criteria.add(new SortCriterion(SORT_PREFIX_PHONE, SortOrder.DESCENDING));
        criteria.add(new SortCriterion(SORT_PREFIX_EMAIL, SortOrder.ASCENDING));
        criteria.add(new SortCriterion(SORT_PREFIX_ADDRESS, SortOrder.DESCENDING));

        SortCommand expectedCommand = new SortCommand(criteria);
        assertParseSuccess(parser, "-n asc -p desc -e asc -a desc", expectedCommand);
    }

    @Test
    public void parse_orderPreservation_success() {
        // Test that left-to-right order is preserved
        List<SortCriterion> criteria1 = new ArrayList<>();
        criteria1.add(new SortCriterion(SORT_PREFIX_PHONE, SortOrder.DESCENDING));
        criteria1.add(new SortCriterion(SORT_PREFIX_NAME, SortOrder.ASCENDING));

        SortCommand expectedCommand1 = new SortCommand(criteria1);
        assertParseSuccess(parser, "-p desc -n asc", expectedCommand1);

        List<SortCriterion> criteria2 = new ArrayList<>();
        criteria2.add(new SortCriterion(SORT_PREFIX_NAME, SortOrder.ASCENDING));
        criteria2.add(new SortCriterion(SORT_PREFIX_PHONE, SortOrder.DESCENDING));

        SortCommand expectedCommand2 = new SortCommand(criteria2);
        assertParseSuccess(parser, "-n asc -p desc", expectedCommand2);
    }

    @Test
    public void parse_extraSpaces_success() {
        List<SortCriterion> criteria = new ArrayList<>();
        criteria.add(new SortCriterion(SORT_PREFIX_NAME, SortOrder.ASCENDING));

        SortCommand expectedCommand = new SortCommand(criteria);

        assertParseSuccess(parser, "-n  asc", expectedCommand);
        assertParseSuccess(parser, "-n   asc", expectedCommand);
        assertParseSuccess(parser, "  -n asc  ", expectedCommand);
    }

    @Test
    public void parse_caseInsensitiveOrder_failure() {
        assertParseFailure(parser, "-n aSc -p DeSC",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "-n asc -p desC",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_caseInsensitivePrefix_failure() {
        assertParseFailure(parser, "-N asc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidPrefix_failure() {
        assertParseFailure(parser, "-x asc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "-x asc -y desc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "n asc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "p desc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "n/asc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidOrder_failure() {
        assertParseFailure(parser, "-n invalid",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "-n ascending",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "-p up",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_preamblePresent_failure() {
        assertParseFailure(parser, "some text -n asc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "hello -p desc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_duplicatePrefix_failure() {
        assertParseFailure(parser,
                "-n asc -n desc",
                Messages.getErrorMessageForDuplicatePrefixes(SORT_PREFIX_NAME));

        assertParseFailure(parser,
                "-p asc -p desc",
                Messages.getErrorMessageForDuplicatePrefixes(SORT_PREFIX_PHONE));
    }
}

