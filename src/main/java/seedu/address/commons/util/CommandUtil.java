package seedu.address.commons.util;

import seedu.address.logic.parser.ArgumentMultimap;

/**
 * Utility functions to verify command syntax
 */
public class CommandUtil {
    public static boolean verifyValuesOfAllPrefixesAreEmpty(ArgumentMultimap argMultimap) {
        return argMultimap.getAllPrefixes().stream().allMatch(p -> argMultimap.getValue(p).isEmpty());
    }
}
