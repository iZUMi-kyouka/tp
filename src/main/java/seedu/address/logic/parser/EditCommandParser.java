package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireExactlyOneIsTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.EDIT_PREFIX_APPEND;
import static seedu.address.logic.parser.CliSyntax.EDIT_PREFIX_OVERWRITE;
import static seedu.address.logic.parser.CliSyntax.EDIT_PREFIX_PRIMARY;
import static seedu.address.logic.parser.CliSyntax.EDIT_PREFIX_REMOVE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.ParserUtil.extractValueFromMultimap;

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
    @Override
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_DESCRIPTION, PREFIX_TAG, EDIT_PREFIX_PRIMARY,
                        EDIT_PREFIX_APPEND, EDIT_PREFIX_OVERWRITE, EDIT_PREFIX_REMOVE);

        String preamble = argMultimap.getPreamble().trim();
        Optional<UUID> idOpt = tryParseID(preamble);
        Optional<Index> indexOpt = idOpt.isEmpty() ? tryParseIndex(preamble) : Optional.empty();
        if (idOpt.isEmpty() && indexOpt.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        EditCommand.EditOperation operation = parseEditOperation(argMultimap);
        LogsCenter.getLogger(EditCommandParser.class).info(operation.toString());

        if (operation == EditCommand.EditOperation.REMOVE) {
            // If operation is remove, redo parsing without description
            argMultimap =
                    ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                            PREFIX_TAG, EDIT_PREFIX_APPEND, EDIT_PREFIX_OVERWRITE, EDIT_PREFIX_REMOVE);
        }

        EditCommand.EditRecruitDescriptor editBuilder = new EditCommand.EditRecruitDescriptor(operation);

        editBuilder.withNames(parseValuesForEdit(PREFIX_NAME, argMultimap, ParserUtil::parseName));
        editBuilder.withPhones(parseValuesForEdit(PREFIX_PHONE, argMultimap, ParserUtil::parsePhone));
        editBuilder.withEmails(parseValuesForEdit(PREFIX_EMAIL, argMultimap, ParserUtil::parseEmail));
        editBuilder.withAddresses(parseValuesForEdit(PREFIX_ADDRESS, argMultimap, ParserUtil::parseAddress));
        editBuilder.withNames(parseValuesForEdit(PREFIX_NAME, argMultimap, ParserUtil::parseName));
        editBuilder.withPhones(parseValuesForEdit(PREFIX_PHONE, argMultimap, ParserUtil::parsePhone));
        editBuilder.withEmails(parseValuesForEdit(PREFIX_EMAIL, argMultimap, ParserUtil::parseEmail));
        editBuilder.withAddresses(parseValuesForEdit(PREFIX_ADDRESS, argMultimap,
                ParserUtil::parseAddress));
        editBuilder.withDescription(extractValueFromMultimap(PREFIX_DESCRIPTION, argMultimap,
                ParserUtil::parseDescription));
        editBuilder.withTags(parseValuesForEdit(PREFIX_TAG, argMultimap, ParserUtil::parseTag));

        // Checks if user specified any fields to modify
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
     * Uses the result of extracting {@link Prefix} values from the provided {@link ArgumentMultimap}
     * and uses the provided {@link ParserUtil.ParserFunction} to parse the values into the appropriate types.
     * It stores these values in a {@link List} of that type.
     *
     * <p>It has been adapted for the EditCommandParser to check if there is a single blank entry for that
     * prefix, and if there is, just return an empty list.</p>
     *
     * @param prefix The prefix to search for within the ArgumentMultimap
     * @param map The processed ArgumentMultimap
     * @param parserFunction the function that converts the List of Strings extracted from
     *                       the ArgumentMultiMap into it's appropriate type
     * @return null if the corresponding prefix does not exist in the map.
     *         An empty list if the List found from the map contains only 1 blank item.
     *         A List of items of type T if the prefix exists in the map.
     * @throws ParseException If the ParserFunction is unable to parse the List of Strings extracted
     *                        from the ArgumentMultimap
     */
    private static <T> List<T> parseValuesForEdit(Prefix prefix, ArgumentMultimap map,
                                                  ParserUtil.ParserFunction<String, T> parserFunction)
            throws ParseException {
        if (!map.hasValue(prefix)) {
            return null;
        }

        if (map.hasSingleBlankValue(prefix)) {
            return Collections.emptyList();
        }

        return ParserUtil.parseAllValues(map.getAllValues(prefix), parserFunction);
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
        boolean isUpdatePrimary = argMultiMap.getValue(EDIT_PREFIX_PRIMARY).isPresent();
        boolean isOverwrite = argMultiMap.getValue(EDIT_PREFIX_OVERWRITE).isPresent()
                || (!isAppend && !isRemove && !isUpdatePrimary);

        try {
            requireExactlyOneIsTrue(List.of(isAppend, isOverwrite, isRemove, isUpdatePrimary));
        } catch (IllegalArgumentException e) {
            throw new ParseException(String.format(EditCommand.MESSAGE_INVALID_OPERATION_TYPE), e);
        }

        // disallow specifying more than one data of a particular attribute
        // when updating primary data of any attribute
        if (isUpdatePrimary) {
            try {
                argMultiMap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS);
            } catch (ParseException pe) {
                throw new ParseException(String.format(EditCommand.MESSAGE_MAXIMUM_ONE_PRIMARY_DATA), pe);
            }
        }

        return isAppend
                ? EditCommand.EditOperation.APPEND
                : isRemove
                ? EditCommand.EditOperation.REMOVE
                : isUpdatePrimary
                ? EditCommand.EditOperation.UPDATE_PRIMARY
                : EditCommand.EditOperation.OVERWRITE;
    }
}
