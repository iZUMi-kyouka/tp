package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EDIT_OP_FLAG_APPEND;
import static seedu.address.logic.commands.CommandTestUtil.EDIT_OP_FLAG_OVERWRITE;
import static seedu.address.logic.commands.CommandTestUtil.EDIT_OP_FLAG_REMOVE;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_RECRUIT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_RECRUIT;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_RECRUIT;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditOperation;
import seedu.address.model.recruit.data.Email;
import seedu.address.model.recruit.data.Name;
import seedu.address.model.recruit.data.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EditRecruitDescriptorBuilder;
import seedu.address.testutil.TypicalIDs;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        UUID targetId = TypicalIDs.ID_FIRST_RECRUIT;
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, targetId.toString(), EditCommand.MESSAGE_NO_FIELD_PROVIDED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // There is currently no way to obtain an invalid address or description when parsing the edit command
        // since if a blank String is provided to the edit command, it is treated as clearing the Data field.

        UUID targetId = TypicalIDs.ID_FIRST_RECRUIT;
        assertParseFailure(parser, targetId + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, targetId + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, targetId + INVALID_EMAIL_DESC, Email.MESSAGE_CONSTRAINTS); // invalid email
        assertParseFailure(parser, targetId + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // invalid phone followed by valid email
        assertParseFailure(parser, targetId + INVALID_PHONE_DESC + EMAIL_DESC_AMY, Phone.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Recruit} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, targetId + TAG_DESC_FRIEND + TAG_DESC_HUSBAND + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, targetId + TAG_DESC_FRIEND + TAG_EMPTY + TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, targetId + TAG_EMPTY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, targetId + INVALID_NAME_DESC + INVALID_EMAIL_DESC + VALID_ADDRESS_AMY
                        + VALID_PHONE_AMY,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_multipleOperationType_failure() {
        assertParseFailure(parser, "1" + NAME_DESC_AMY + EDIT_OP_FLAG_APPEND + EDIT_OP_FLAG_OVERWRITE,
                EditCommand.MESSAGE_INVALID_OPERATION_TYPE);
        assertParseFailure(parser, "1" + EDIT_OP_FLAG_REMOVE + EDIT_OP_FLAG_APPEND + PHONE_DESC_BOB,
                EditCommand.MESSAGE_INVALID_OPERATION_TYPE);
        assertParseFailure(parser, "1" + EDIT_OP_FLAG_REMOVE + EDIT_OP_FLAG_APPEND + EDIT_OP_FLAG_OVERWRITE
                + PHONE_DESC_BOB, EditCommand.MESSAGE_INVALID_OPERATION_TYPE);
    }

    @Test
    public void parse_overwriteOperationAllFieldsSpecified_success() {
        UUID targetId = TypicalIDs.ID_SECOND_RECRUIT;
        String userInput = targetId + PHONE_DESC_BOB + TAG_DESC_HUSBAND
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + DESCRIPTION_DESC_BOB + NAME_DESC_AMY + TAG_DESC_FRIEND;

        // implicit (no overwrite flag)
        EditCommand.EditRecruitDescriptor descriptor = new EditRecruitDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withDescription(VALID_DESCRIPTION_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        EditCommand expectedCommand = new EditCommand(targetId, descriptor);

        // explicit (with overwrite flag)
        userInput = userInput + EDIT_OP_FLAG_OVERWRITE;
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_appendOperationAllFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_RECRUIT;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + TAG_DESC_HUSBAND
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + NAME_DESC_AMY + TAG_DESC_FRIEND + EDIT_OP_FLAG_APPEND;

        EditCommand.EditRecruitDescriptor descriptor = new EditRecruitDescriptorBuilder(EditOperation.APPEND)
                .withName(VALID_NAME_AMY).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    public void parse_someFieldsSpecified_success() {
        UUID targetId = TypicalIDs.ID_FIRST_RECRUIT;
        String userInput = targetId + PHONE_DESC_BOB + EMAIL_DESC_AMY;

        EditCommand.EditRecruitDescriptor descriptor = new EditRecruitDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetId, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_removeOperationAllFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_RECRUIT;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + TAG_DESC_HUSBAND
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + NAME_DESC_AMY + TAG_DESC_FRIEND + EDIT_OP_FLAG_REMOVE;

        EditCommand.EditRecruitDescriptor descriptor = new EditRecruitDescriptorBuilder(EditOperation.REMOVE)
                .withName(VALID_NAME_AMY).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_overwriteOperationSomeFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_RECRUIT;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + EMAIL_DESC_AMY;

        // implicit (no overwrite flag)
        EditCommand.EditRecruitDescriptor descriptor = new EditRecruitDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // explicit (with overwrite flag)
        userInput = userInput + EDIT_OP_FLAG_OVERWRITE;
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_appendOperationSomeFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_RECRUIT;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + EMAIL_DESC_AMY + EDIT_OP_FLAG_APPEND;

        EditCommand.EditRecruitDescriptor descriptor = new EditRecruitDescriptorBuilder(EditOperation.APPEND)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_removeOperationSomeFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_RECRUIT;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + EMAIL_DESC_AMY + EDIT_OP_FLAG_REMOVE;

        EditCommand.EditRecruitDescriptor descriptor = new EditRecruitDescriptorBuilder(EditOperation.REMOVE)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_overwriteOperationOneFieldSpecified_success() {
        // name
        UUID targetId = TypicalIDs.ID_THIRD_RECRUIT;
        String userInput = targetId + NAME_DESC_AMY;
        EditCommand.EditRecruitDescriptor descriptor =
                new EditRecruitDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetId, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetId + PHONE_DESC_AMY;
        descriptor = new EditRecruitDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        expectedCommand = new EditCommand(targetId, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = targetId + EMAIL_DESC_AMY;
        descriptor = new EditRecruitDescriptorBuilder().withEmail(VALID_EMAIL_AMY).build();
        expectedCommand = new EditCommand(targetId, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = targetId + ADDRESS_DESC_AMY;
        descriptor = new EditRecruitDescriptorBuilder().withAddress(VALID_ADDRESS_AMY).build();
        expectedCommand = new EditCommand(targetId, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetId + TAG_DESC_FRIEND;
        descriptor = new EditRecruitDescriptorBuilder().withTags(VALID_TAG_FRIEND).build();
        expectedCommand = new EditCommand(targetId, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_appendOperationOneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_RECRUIT;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY + EDIT_OP_FLAG_APPEND;
        EditCommand.EditRecruitDescriptor descriptor = new EditRecruitDescriptorBuilder(EditOperation.APPEND)
                .withName(VALID_NAME_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetIndex.getOneBased() + PHONE_DESC_AMY + EDIT_OP_FLAG_APPEND;
        descriptor = new EditRecruitDescriptorBuilder(EditOperation.APPEND)
                .withPhone(VALID_PHONE_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = targetIndex.getOneBased() + EMAIL_DESC_AMY + EDIT_OP_FLAG_APPEND;
        descriptor = new EditRecruitDescriptorBuilder(EditOperation.APPEND)
                .withEmail(VALID_EMAIL_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = targetIndex.getOneBased() + ADDRESS_DESC_AMY + EDIT_OP_FLAG_APPEND;
        descriptor = new EditRecruitDescriptorBuilder(EditOperation.APPEND)
                .withAddress(VALID_ADDRESS_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_FRIEND + EDIT_OP_FLAG_APPEND;
        descriptor = new EditRecruitDescriptorBuilder(EditOperation.APPEND)
                .withTags(VALID_TAG_FRIEND).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_removeOperationOneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_RECRUIT;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY + EDIT_OP_FLAG_REMOVE;
        EditCommand.EditRecruitDescriptor descriptor =
                new EditRecruitDescriptorBuilder(EditOperation.REMOVE)
                        .withName(VALID_NAME_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetIndex.getOneBased() + PHONE_DESC_AMY + EDIT_OP_FLAG_REMOVE;
        descriptor = new EditRecruitDescriptorBuilder(EditOperation.REMOVE)
                .withPhone(VALID_PHONE_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = targetIndex.getOneBased() + EMAIL_DESC_AMY + EDIT_OP_FLAG_REMOVE;
        descriptor = new EditRecruitDescriptorBuilder(EditOperation.REMOVE)
                .withEmail(VALID_EMAIL_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = targetIndex.getOneBased() + ADDRESS_DESC_AMY + EDIT_OP_FLAG_REMOVE;
        descriptor = new EditRecruitDescriptorBuilder(EditOperation.REMOVE)
                .withAddress(VALID_ADDRESS_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_FRIEND + EDIT_OP_FLAG_REMOVE;
        descriptor = new EditRecruitDescriptorBuilder(EditOperation.REMOVE)
                .withTags(VALID_TAG_FRIEND).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_removeOperationDescriptionSpecified_failure() {
        Index targetIndex = INDEX_FIRST_RECRUIT;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + EMAIL_DESC_AMY
                + EDIT_OP_FLAG_REMOVE + DESCRIPTION_DESC_AMY;

        String expectedMessage = EditCommandParser.MESSAGE_DESCRIPTION_IN_REMOVE;

        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_resetTags_success() {
        UUID targetId = TypicalIDs.ID_THIRD_RECRUIT;
        String userInput = targetId + TAG_EMPTY;

        EditCommand.EditRecruitDescriptor descriptor = new EditRecruitDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(targetId, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
