package seedu.address.logic.parser;


import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;
import java.util.stream.Stream;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.recruit.Recruit;
import seedu.address.model.recruit.RecruitBuilder;
import seedu.address.model.recruit.data.Address;
import seedu.address.model.recruit.data.Email;
import seedu.address.model.recruit.data.Name;
import seedu.address.model.recruit.data.Phone;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                       PREFIX_DESCRIPTION, PREFIX_TAG);
        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        if (!arePrefixesPresent(argMultimap, PREFIX_NAME)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_NO_NAME));
        }
        if (!argMultimap.hasNoDuplicateEntriesForAllPrefixes()) {
            throw new ParseException(Messages.MESSAGE_NO_DUPLICATE_ENTRY_ALLOWED);
        }

        List<Name> extractedNames = ParserUtil.extractValuesFromMultimap(
                PREFIX_NAME, argMultimap, ParserUtil::parseName);
        List<Phone> extractedPhones = ParserUtil.extractValuesFromMultimap(
                PREFIX_PHONE, argMultimap, ParserUtil::parsePhone);
        List<Email> extractedEmails = ParserUtil.extractValuesFromMultimap(
                PREFIX_EMAIL, argMultimap, ParserUtil::parseEmail);
        List<Address> extractedAddresses = ParserUtil.extractValuesFromMultimap(
                PREFIX_ADDRESS, argMultimap, ParserUtil::parseAddress);

        Recruit recruit = new RecruitBuilder()
                .withNames(extractedNames)
                .withPrimaryName(extractedNames == null || extractedNames.isEmpty() ? null : extractedNames.get(0))
                .withPhones(extractedPhones)
                .withPrimaryPhone(extractedPhones == null || extractedPhones.isEmpty() ? null : extractedPhones.get(0))
                .withEmails(extractedEmails)
                .withPrimaryEmail(extractedEmails == null || extractedEmails.isEmpty() ? null : extractedEmails.get(0))
                .withAddresses(extractedAddresses)
                .withPrimaryAddress(extractedAddresses == null || extractedAddresses.isEmpty()
                        ? null : extractedAddresses.get(0))
                .withDescription(ParserUtil.extractValueFromMultimap(PREFIX_DESCRIPTION, argMultimap,
                        ParserUtil::parseDescription))
                .withTags(ParserUtil.extractValuesFromMultimap(PREFIX_TAG, argMultimap, ParserUtil::parseTag))
                .build();

        return new AddCommand(recruit);
    }


    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
