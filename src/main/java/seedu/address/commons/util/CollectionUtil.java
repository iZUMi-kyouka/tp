package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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
     * Removes a list of elements from the given collection and returns a list of elements that
     * could not be found in the collection.
     *
     * @param collection Collection to remove from
     * @param toRemove List from which elements should be removed
     * @return List of elements that could not be found in the collection.
     */
    public static <T> List<T> removeListFromCollection(Set<T> collection, List<T> toRemove) {
        if (toRemove == null || toRemove.isEmpty()) {
            return new ArrayList<>();
        }

        if (collection == null) {
            throw new NullPointerException("Cannot remove elements from an uninitialized collection");
        }

        List<T> missing = new ArrayList<>();
        for (T item : toRemove) {
            if (collection.contains(item)) {
                collection.remove(item);
            } else {
                missing.add(item);
            }
        }

        return missing;
    }
}
