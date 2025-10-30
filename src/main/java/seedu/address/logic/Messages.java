package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.recruit.Recruit;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_RECRUIT_DISPLAYED_INDEX = "The recruit index provided is invalid";
    public static final String MESSAGE_INVALID_RECRUIT_ID = "The recruit id provided is invalid";
    public static final String MESSAGE_RECRUITS_LISTED_OVERVIEW = "%1$d recruits listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_NON_VALUE_ACCEPTING_FLAGS =
                "The following flag(s) do not accept any argument: ";
    public static final String MESSAGE_NO_ID_OR_INDEX = "Either UUID or index must be provided.";
    public static final String MESSAGE_PREAMBLE_NOT_ACCEPTED = "This command does not accept any preamble.";


    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForNonValueAcceptingPrefixes(Prefix... prefixes) {
        assert prefixes.length > 0;

        Set<String> fields =
                Stream.of(prefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_NON_VALUE_ACCEPTING_FLAGS + String.join(" ", fields);
    }

    /**
     * Formats the {@code person} for display to the user.
     */
    public static String format(Recruit recruit) {
        final StringBuilder builder = new StringBuilder();
        builder.append(" ID: ").append(recruit.getID());

        builder.append("\n Name: [")
                .append(recruit.getNames().stream()
                        .map(Object::toString)
                        .collect(Collectors.joining(", ")))
                .append("]");

        builder.append("\n Phone: [")
                .append(recruit.getPhones().stream()
                        .map(Object::toString)
                        .collect(Collectors.joining(", ")))
                .append("]");

        builder.append("\n Email: [")
                .append(recruit.getEmails().stream()
                        .map(Object::toString)
                        .collect(Collectors.joining(", ")))
                .append("]");

        builder.append("\n Address: [")
                .append(recruit.getAddresses().stream()
                        .map(Object::toString)
                        .collect(Collectors.joining(", ")))
                .append("]");

        if (!recruit.getDescription().isEmptyDescription()) {
            builder.append("\n Description: ").append(recruit.getDescription());
        }

        builder.append("\n Tags: ")
                .append(recruit.getTags().stream()
                        .map(Object::toString)
                        .collect(Collectors.joining(", ")));

        return builder.toString();
    }

}
