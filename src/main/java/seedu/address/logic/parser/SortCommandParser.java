package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.SORT_PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.SORT_PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.SORT_PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.SORT_PREFIX_PHONE;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.SortCommand.SortCriterion;
import seedu.address.logic.commands.SortCommand.SortOrder;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SortCommand object.
 * Supports sorting by multiple fields with ascending or descending order.
 * The order of the prefixes determines the priority of sorting (left to right).
 */
public class SortCommandParser implements Parser<SortCommand> {

    private static final String ASCENDING = "asc";
    private static final String DESCENDING = "desc";

    /**
     * Parses the given argument and returns a SortCommand object for execution.
     *
     * @param args The input arguments containing field/order pairs (e.g., "-n asc -p desc")
     * @return A SortCommand object with the parsed sort criteria
     * @throws ParseException if the user input does not conform to the expected format
     */
    @Override
    public SortCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(
                        args,
                        SORT_PREFIX_NAME,
                        SORT_PREFIX_PHONE,
                        SORT_PREFIX_EMAIL,
                        SORT_PREFIX_ADDRESS
                );

        boolean isInvalidCommand = (
                !arePrefixesPresent(argMultimap, SORT_PREFIX_NAME)
                        && !arePrefixesPresent(argMultimap, SORT_PREFIX_PHONE)
                        && !arePrefixesPresent(argMultimap, SORT_PREFIX_EMAIL)
                        && !arePrefixesPresent(argMultimap, SORT_PREFIX_ADDRESS)
                        && argMultimap.getPreamble().isEmpty()
        );

        if (isInvalidCommand) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(
                SORT_PREFIX_NAME, SORT_PREFIX_PHONE, SORT_PREFIX_EMAIL, SORT_PREFIX_ADDRESS);

        List<SortCriterion> sortCriteria = new ArrayList<>();

        List<PrefixOrderPair> pairs = extractPrefixOrderPairs(args.trim());

        for (PrefixOrderPair pair : pairs) {
            Prefix prefix = parseSortField(pair.prefixString);
            SortOrder order = parseOrder(pair.order);
            sortCriteria.add(new SortCriterion(prefix, order));
        }

        return new SortCommand(sortCriteria);
    }

    /**
     * ArgumentTokenizer does not preserve ordering,
     * manual parsing required to extract prefix-order pairs in order of prefix appearance.
     */
    private List<PrefixOrderPair> extractPrefixOrderPairs(String args) throws ParseException {
        Pattern wholePattern = Pattern.compile(
                "^(?:\\s*-[npea]\\s+(?i:asc|desc)\\s*)+$"
        );

        Pattern pairPattern = Pattern.compile(
                "-(?<prefix>[npea])\\s+(?<order>(?i:asc|desc))"
        );

        if (args == null || args.isBlank()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        // 1) Validate the entire input first
        if (!wholePattern.matcher(args).matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        // 2) Extract pairs
        List<PrefixOrderPair> pairs = new ArrayList<>();
        List<String> seenPrefixes = new ArrayList<>();
        Matcher m = pairPattern.matcher(args);

        while (m.find()) {
            String prefix = m.group("prefix"); // "n" | "p" | "e" | "a"
            String order = m.group("order").toLowerCase(Locale.ROOT); // normalize order

            // duplicate detection. For some reason testing ArgumentMultimap's verifyNoDuplicate does not work.
            if (seenPrefixes.contains(prefix)) {
                Prefix duplicatePrefix = parseSortField(prefix);
                throw new ParseException(
                        seedu.address.logic.Messages.getErrorMessageForDuplicatePrefixes(duplicatePrefix));
            }
            seenPrefixes.add(prefix);

            pairs.add(new PrefixOrderPair(prefix, order));
        }

        return pairs;
    }

    /**
     * Parses the given prefix string (e.g., "n", "p", "e", "a") and returns the corresponding Prefix.
     */
    private Prefix parseSortField(String prefixStr) throws ParseException {
        switch (prefixStr) {
        case "n":
            return SORT_PREFIX_NAME;
        case "p":
            return SORT_PREFIX_PHONE;
        case "e":
            return SORT_PREFIX_EMAIL;
        case "a":
            return SORT_PREFIX_ADDRESS;
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
    }

    private SortOrder parseOrder(String orderStr) throws ParseException {
        if (orderStr.equalsIgnoreCase(ASCENDING)) {
            return SortOrder.ASCENDING;
        } else if (orderStr.equalsIgnoreCase(DESCENDING)) {
            return SortOrder.DESCENDING;
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Helper pair class to store a prefix and its associated order (asc / desc).
     */
    private static class PrefixOrderPair {
        private final String prefixString;
        private final String order;

        PrefixOrderPair(String prefixString, String order) {
            this.prefixString = prefixString;
            this.order = order;
        }
    }

    /**
     * Returns true if all the specified prefixes are present in the given ArgumentMultimap.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
