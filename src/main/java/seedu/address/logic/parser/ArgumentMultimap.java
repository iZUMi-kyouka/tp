package seedu.address.logic.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.Messages;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Stores mapping of prefixes to their respective arguments.
 * Each key may be associated with multiple argument values.
 * Values for a given key are stored in a list, and the insertion ordering is maintained.
 * Keys are unique, but the list of argument values may contain duplicate argument values, i.e. the same argument value
 * can be inserted multiple times for the same prefix.
 */
public class ArgumentMultimap {

    /** Prefixes mapped to their respective arguments**/
    private final Map<Prefix, List<String>> argMultimap = new HashMap<>();

    /**
     * Associates the specified argument value with {@code prefix} key in this map.
     * If the map previously contained a mapping for the key, the new value is appended to the list of existing values.
     *
     * @param prefix   Prefix key with which the specified argument value is to be associated
     * @param argValue Argument value to be associated with the specified prefix key
     */
    public void put(Prefix prefix, String argValue) {
        if (!argMultimap.containsKey(prefix)) {
            argMultimap.put(prefix, new ArrayList<>());
        }
        argMultimap.get(prefix).add(argValue);
    }

    /**
     * Returns the last value of {@code prefix}.
     */
    public boolean hasValue(Prefix prefix) {
        return this.argMultimap.containsKey(prefix);
    }

    /**
     * Returns the last value of {@code prefix}.
     */
    public Optional<String> getValue(Prefix prefix) {
        return Optional.ofNullable(this.getAllValues(prefix)).map(vs -> vs.get(vs.size() - 1));
    }

    /**
     * Returns all values of {@code prefix}.
     * If the prefix does not exist it will return null.
     * If the prefix has no values, it will return an empty List.
     * Modifying the returned list will not affect the underlying data structure of the ArgumentMultimap.
     */
    public List<String> getAllValues(Prefix prefix) {
        return argMultimap.get(prefix);
    }

    /**
     * Returns the preamble (text before the first valid prefix). Trims any leading/trailing spaces.
     */
    public String getPreamble() {
        return getValue(new Prefix("")).orElse("");
    }

    /**
     * Throws a {@code ParseException} if any of the prefixes given in {@code prefixes} appeared more than
     * once among the arguments.
     */
    public void verifyNoDuplicatePrefixesFor(Prefix... prefixes) throws ParseException {
        Prefix[] duplicatedPrefixes = Stream.of(prefixes).distinct()
                .filter(prefix -> argMultimap.containsKey(prefix) && argMultimap.get(prefix).size() > 1)
                .toArray(Prefix[]::new);

        if (duplicatedPrefixes.length > 0) {
            throw new ParseException(Messages.getErrorMessageForDuplicatePrefixes(duplicatedPrefixes));
        }
    }

    /**
     * Throws a {@code ParseException} if any of the prefixes present has a non-blank
     * string value. Note that this does not verify that all the prefixes have at most one value.
     */
    public void verifyValuesOfAllPrefixesAreEmpty() throws ParseException {
        // Remove the empty string prefix to indicate start and end of command
        List<Prefix> prefixes = argMultimap.keySet().stream().filter(p -> !p.getPrefix().isEmpty()).toList();

        if (!prefixes.stream().allMatch(p -> {
            List<String> values = argMultimap.get(p);
            return values.stream().allMatch(String::isEmpty);
        })) {
            throw new ParseException(
                    Messages.getErrorMessageForNonValueAcceptingPrefixes(prefixes.toArray(Prefix[]::new)));
        }
    }

    /**
     * Returns all {@code Prefix} that are present in a {@code Set}.
     */
    public Set<Prefix> getAllPrefixes() {
        return argMultimap.keySet();
    }

    /**
     * Checks if there is only 1 blank entry for that particular prefix.
     *
     * @param prefix the prefix to check
     * @return true if there is only 1 blank entry for that particular prefix
     */
    public boolean hasSingleBlankValue(Prefix prefix) {
        if (this.argMultimap.containsKey(prefix)) {
            List<String> values = this.argMultimap.get(prefix);
            return values.size() == 1 && values.get(0).isBlank();
        }
        return false;
    }
}
