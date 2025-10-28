package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_DUPLICATE_FIELDS;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_NON_VALUE_ACCEPTING_FLAGS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ListCommandParser.ListOperation;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_RECRUITS;
import static seedu.address.model.Model.PREDICATE_SHOW_ARCHIVED_RECRUITS;
import static seedu.address.model.Model.PREDICATE_SHOW_UNARCHVIED_RECRUITS;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ListCommand;

public class ListCommandParserTest {

    private final ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_noArgs_returnsListCommand() {
        ListCommand expectedListCommand = new ListCommand(PREDICATE_SHOW_UNARCHVIED_RECRUITS,
                ListOperation.NORMAL_LIST_OP);
        assertParseSuccess(parser, "", expectedListCommand);
    }

    @Test
    public void parse_allArg_returnsListCommand() {
        ListCommand expectedListCommand = new ListCommand(PREDICATE_SHOW_ALL_RECRUITS, ListOperation.FULL_LIST_OP);
        assertParseSuccess(parser, " " + "-all", expectedListCommand);
        assertParseSuccess(parser, "\n -all \t", expectedListCommand);
    }

    @Test
    public void parse_archivedArg_returnsListCommand() {
        ListCommand expectedListCommand = new ListCommand(PREDICATE_SHOW_ARCHIVED_RECRUITS,
                ListOperation.ARCHIVE_LIST_OP);
        assertParseSuccess(parser, " " + "-archived", expectedListCommand);
        assertParseSuccess(parser, "\n -archived \t", expectedListCommand);
    }

    @Test
    public void parse_allAndArchivedArgs_throwsParseException() {
        assertParseFailure(parser, " " + "-all -archived",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " " + "-archived -all",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_duplicateFlags_throwsParseException() {
        assertParseFailure(parser, " " + "-all -all",
                MESSAGE_DUPLICATE_FIELDS + "-all");
        assertParseFailure(parser, " " + "-archived -archived",
                MESSAGE_DUPLICATE_FIELDS + "-archived");
    }

    @Test
    public void parse_preambleSupplied_throwsParseException() {
        assertParseFailure(parser, "random_preamble",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "archived",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
    }


    @Test
    public void parse_flagValueSupplied_throwsParseException() {
        assertParseFailure(parser, " " + "-all random_value",
                MESSAGE_NON_VALUE_ACCEPTING_FLAGS + "-all");
        assertParseFailure(parser, " " + "-archived random",
                MESSAGE_NON_VALUE_ACCEPTING_FLAGS + "-archived");
    }

    @Test
    public void parse_flagValueAndPreambleSupplied_throwsParseException() {
        assertParseFailure(parser, "preamble -all random_value",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "random -archived random",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
    }
}
