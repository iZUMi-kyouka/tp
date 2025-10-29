package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Utility methods related to Collections
 */
public class CollectionUtil {

    public static <T> List<T> combine(Collection<Collection<T>> c) {
        return c.stream().flatMap(Collection::stream).toList();
    }

    /** @see #requireAllNonNull(Collection) */
    public static void requireAllNonNull(Object... items) {
        requireNonNull(items);
        Stream.of(items).forEach(Objects::requireNonNull);
    }

    /**
     * Throws NullPointerException if {@code items} or any element of {@code items} is null.
     */
    public static void requireAllNonNull(Collection<?> items) {
        requireNonNull(items);
        items.forEach(Objects::requireNonNull);
    }

    /**
     * Throws IllegalArgumentException if {@code items} is empty.
     */
    public static void requireNonEmpty(Collection<?> items) throws IllegalArgumentException {
        if (items.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Throws IllegalArgumentException if more than, or less than one item in {@code items} is true.
     */
    public static void requireExactlyOneIsTrue(Collection<Boolean> items) throws IllegalArgumentException {
        if (items.stream().filter(b -> b).count() != 1) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Throws IllegalArgumentException if any element of {@code items} is an empty string or
     * string containing only whitespace codepoints.
     */
    public static void requireAllNonBlankString(Collection<? extends String> items) throws IllegalArgumentException {
        if (items.stream().anyMatch(String::isBlank)) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Returns true if {@code items} contain any elements that are non-null.
     */
    public static boolean isAnyNonNull(Object... items) {
        return items != null && Arrays.stream(items).anyMatch(Objects::nonNull);
    }

    /**
     * Removes all elements in {@code toRemove} from the given set and returns a list of
     * elements that were not found in the set.
     *
     * @param collection the set to remove elements from
     * @param toRemove the list of elements to remove
     * @return a list of elements that were not found in the set
     * @throws IllegalArgumentException if {@code collection} is null
     */
    public static <T> List<T> removeListFromSetReturnMissing(Set<T> collection, Collection<? extends T> toRemove) {
        if (toRemove == null || toRemove.isEmpty()) {
            return Collections.emptyList();
        }

        if (collection == null) {
            throw new IllegalArgumentException("Cannot remove elements from an uninitialized Set!");
        }

        List<T> missing = new ArrayList<>();
        for (T item : toRemove) {
            if (!collection.remove(item)) {
                missing.add(item);
            }
        }

        return missing;
    }

    /**
     * Adds all elements in {@code toAdd} to the given set and returns a list of
     * elements that were already present.
     *
     * @param collection the set to add elements to
     * @param toAdd the list of elements to add
     * @return a list of elements that were already present in the set
     * @throws IllegalArgumentException if {@code collection} is null
     */
    public static <T> List<T> addListToSetReturnDuplicates(Set<T> collection, Collection<? extends T> toAdd) {
        if (toAdd == null || toAdd.isEmpty()) {
            return Collections.emptyList();
        }

        if (collection == null) {
            throw new IllegalArgumentException("Cannot add to an uninitialized Set!");
        }

        List<T> duplicates = new ArrayList<>();
        for (T item : toAdd) {
            if (!collection.add(item)) {
                duplicates.add(item);
            }
        }

        return duplicates;
    }
}
