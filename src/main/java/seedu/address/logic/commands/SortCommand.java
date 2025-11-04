package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.SORT_PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.SORT_PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.SORT_PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.SORT_PREFIX_PHONE;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;
import seedu.address.model.recruit.Recruit;

/**
 * Sorts the recruits in the address book by specified fields in ascending or descending order.
 * Supports multiple sort criteria with priority from left to right.
 */
public class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";
    public static final String OPERATION_DESCRIPTOR = "sorting of recruit by %s";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts all recruits in the address book by specified fields.\n"
            + "Parameters: "
            + "[" + SORT_PREFIX_NAME + " asc/desc] "
            + "[" + SORT_PREFIX_PHONE + " asc/desc] "
            + "[" + SORT_PREFIX_EMAIL + " asc/desc] "
            + "[" + SORT_PREFIX_ADDRESS + " asc/desc]\n"
            + "Example: " + COMMAND_WORD + " "
            + SORT_PREFIX_NAME + " asc "
            + SORT_PREFIX_PHONE + " desc\n"
            + "Shorthands: " + COMMAND_WORD + " (sorts by name ascending), "
            + COMMAND_WORD + " asc/desc (sorts by name asc/desc)\n";

    public static final String MESSAGE_SUCCESS = "Recruits sorted by: %s";

    private final List<SortCriterion> sortCriteria;

    /**
     * Creates a SortCommand to sort recruits by the specified sort criteria.
     */
    public SortCommand(List<SortCriterion> sortCriteria) {
        requireNonNull(sortCriteria);
        this.sortCriteria = sortCriteria;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        Comparator<Recruit> comparator = buildComparator();
        model.sortRecruits(comparator);

        // Build success message description
        String criteriaDescription = sortCriteria.stream()
                .map(SortCriterion::toString)
                .collect(Collectors.joining(", "));

        model.commitAddressBook(String.format(OPERATION_DESCRIPTOR, criteriaDescription));
        return new CommandResult(String.format(MESSAGE_SUCCESS, criteriaDescription));
    }

    /**
     * Builds a comparator based on the sort criteria, applied in order of priority (left to right).
     */
    private Comparator<Recruit> buildComparator() {
        return sortCriteria.stream()
                .map(SortCriterion::getComparator)
                .reduce(Comparator::thenComparing)
                .get();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof SortCommand)) {
            return false;
        }

        SortCommand otherCommand = (SortCommand) other;
        return sortCriteria.equals(otherCommand.sortCriteria);
    }

    /**
     * Represents a single sort criterion consisting of a field and order.
     */
    public static class SortCriterion {
        private final Prefix prefix;
        private final SortOrder order;

        /**
         * Constructs a SortCriterion, given a prefix and sort order (asc / desc).
         */
        public SortCriterion(Prefix prefix, SortOrder order) {
            this.prefix = prefix;
            this.order = order;
        }

        /**
         * Returns a comparator for this sort criterion, depending on prefix and order.
         */
        public Comparator<Recruit> getComparator() {
            Comparator<Recruit> comparator;
            if (prefix.equals(SORT_PREFIX_NAME)) {
                comparator = Comparator.comparing(recruit -> recruit.getName().value.toLowerCase());
                return order == SortOrder.DESCENDING ? comparator.reversed() : comparator;
            } else if (prefix.equals(SORT_PREFIX_PHONE)) {
                return getOptionalFieldComparator(recruit -> recruit.getPhone().map(p -> p.value));
            } else if (prefix.equals(SORT_PREFIX_EMAIL)) {
                return getOptionalFieldComparator(recruit -> recruit.getEmail().map(e -> e.value));
            } else if (prefix.equals(SORT_PREFIX_ADDRESS)) {
                return getOptionalFieldComparator(recruit -> recruit.getAddress().map(a -> a.value));
            } else {
                throw new IllegalArgumentException("Unknown sort prefix: " + prefix);
            }
        }

        /**
         * Returns a custom comparator for sorting by optional fields (phone, email, address).
         * Empty values are always placed at the bottom, regardless of sort order.
         */
        private Comparator<Recruit> getOptionalFieldComparator(
                Function<Recruit, Optional<String>> fieldExtractor) {
            return (r1, r2) -> {
                Optional<String> field1 = fieldExtractor.apply(r1);
                Optional<String> field2 = fieldExtractor.apply(r2);

                if (field1.isEmpty() && field2.isEmpty()) { // Both empty - they're equal
                    return 0;
                } else if (field1.isEmpty()) { // One is empty - empty one always goes to the bottom
                    return 1; // r1 goes after r2
                } else if (field2.isEmpty()) {
                    return -1; // r1 goes before r2
                }

                // Both have values - compare normally
                int comparison = field1.get().toLowerCase().compareTo(field2.get().toLowerCase());
                return order == SortOrder.DESCENDING ? -comparison : comparison;
            };
        }

        /**
         * Returns the full field name for the given prefix.
         */
        private String getFieldName() {
            if (prefix.equals(SORT_PREFIX_NAME)) {
                return "name";
            } else if (prefix.equals(SORT_PREFIX_PHONE)) {
                return "phone";
            } else if (prefix.equals(SORT_PREFIX_EMAIL)) {
                return "email";
            } else if (prefix.equals(SORT_PREFIX_ADDRESS)) {
                return "address";
            } else {
                return prefix.getPrefix();
            }
        }

        @Override
        public String toString() {
            return String.format("%s (%s)", getFieldName(), order.getDisplayName());
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if (!(other instanceof SortCriterion)) {
                return false;
            }

            SortCriterion otherCriterion = (SortCriterion) other;
            return prefix.equals(otherCriterion.prefix) && order == otherCriterion.order;
        }
    }

    /**
     * Enum representing the sort order (asc / desc)
     */
    public enum SortOrder {
        ASCENDING("ascending"),
        DESCENDING("descending");

        private final String displayName;

        SortOrder(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }
}

