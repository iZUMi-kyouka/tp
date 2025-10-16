package seedu.address.model.recruit;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.address.commons.util.ToStringBuilder;

/**
 * A predicate that ORs together multiple {@link FieldContainsKeywordsPredicate}.
 */
public class NestedOrPredicate implements Predicate<Recruit> {

    private final List<FieldContainsKeywordsPredicate> predicates;

    /**
     * Constructs a NestedOrPredicate with one or more FieldContainsKeywordsPredicate.
     *
     * @param predicates Varargs of FieldContainsKeywordsPredicate
     */
    public NestedOrPredicate(FieldContainsKeywordsPredicate... predicates) {
        this.predicates = Arrays.asList(predicates);
    }

    @Override
    public boolean test(Recruit recruit) {
        // Return true if any of the predicates match
        return predicates.stream().allMatch(p -> p.test(recruit));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof NestedOrPredicate)) {
            return false;
        }

        NestedOrPredicate otherPredicate = (NestedOrPredicate) other;
        return predicates.equals(otherPredicate.predicates);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicates", predicates.stream()
                        .map(FieldContainsKeywordsPredicate::toString)
                        .collect(Collectors.joining(", ")))
                .toString();
    }
}
