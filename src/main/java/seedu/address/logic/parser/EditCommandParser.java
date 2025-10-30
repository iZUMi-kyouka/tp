package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireExactlyOneIsTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.EDIT_PREFIX_APPEND;
import static seedu.address.logic.parser.CliSyntax.EDIT_PREFIX_OVERWRITE;
import static seedu.address.logic.parser.CliSyntax.EDIT_PREFIX_REMOVE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.ParserUtil.extractValueFromMultimap;
import static seedu.address.logic.parser.ParserUtil.extractValuesFromMultimap;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_DESCRIPTION, PREFIX_TAG,
                        EDIT_PREFIX_APPEND, EDIT_PREFIX_OVERWRITE, EDIT_PREFIX_REMOVE);

        String preamble = argMultimap.getPreamble().trim();
        Optional<UUID> idOpt = tryParseID(preamble);
        Optional<Index> indexOpt = idOpt.isEmpty() ? tryParseIndex(preamble) : Optional.empty();
        if (idOpt.isEmpty() && indexOpt.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        EditCommand.EditOperation operation = parseEditOperation(argMultimap);
        LogsCenter.getLogger(EditCommandParser.class).info(operation.toString());

        EditCommand.EditRecruitDescriptor editBuilder = new EditCommand.EditRecruitDescriptor(operation);

        editBuilder.withNames(extractValuesFromMultimap(PREFIX_NAME, argMultimap, ParserUtil::parseName));
        editBuilder.withPhones(extractValuesFromMultimap(PREFIX_PHONE, argMultimap, ParserUtil::parsePhone));
        editBuilder.withEmails(extractValuesFromMultimap(PREFIX_EMAIL, argMultimap, ParserUtil::parseEmail));
        editBuilder.withAddresses(extractValuesFromMultimap(PREFIX_ADDRESS, argMultimap, ParserUtil::parseAddress));
        editBuilder.withDescription(extractValueFromMultimap(PREFIX_DESCRIPTION, argMultimap,
                ParserUtil::parseDescription));

        List<String> tagStrings = argMultimap.getAllValues(PREFIX_TAG);
        if (tagStrings != null) {
            if (tagStrings.size() == 1 && tagStrings.get(0).isBlank()) {
                editBuilder.withTags(List.of());
            } else {
                editBuilder.withTags(ParserUtil.parseAllValues(tagStrings, ParserUtil::parseTag));
            }
        }

        if (!editBuilder.hasBeenModified()) {
            throw new ParseException(EditCommand.MESSAGE_NO_FIELD_PROVIDED);
        }

        if (idOpt.isPresent()) {
            return new EditCommand(idOpt.get(), editBuilder);
        } else {
            return new EditCommand(indexOpt.get(), editBuilder);
        }
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

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

    private EditCommand.EditOperation parseEditOperation(ArgumentMultimap argMultiMap)
            throws ParseException {
        boolean isAppend = argMultiMap.getValue(EDIT_PREFIX_APPEND).isPresent();
        boolean isRemove = argMultiMap.getValue(EDIT_PREFIX_REMOVE).isPresent();
        boolean isOverwrite = argMultiMap.getValue(EDIT_PREFIX_OVERWRITE).isPresent() || (!isAppend && !isRemove);

        try {
            requireExactlyOneIsTrue(List.of(isAppend, isOverwrite, isRemove));
        } catch (IllegalArgumentException e) {
            throw new ParseException(
                    String.format(EditCommand.MESSAGE_INVALID_OPERATION), e);
        }

        return isAppend
                ? EditCommand.EditOperation.APPEND
                : isRemove
                ? EditCommand.EditOperation.REMOVE
                : EditCommand.EditOperation.OVERWRITE;
    }
}
