package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.util.CollectionUtil.requireAllNonBlankString;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.commons.util.CollectionUtil.requireNonEmpty;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class CollectionUtilTest {
    @Test
    public void requireAllNonNullVarargs() {
        // no arguments
        assertNullPointerExceptionNotThrown();

        // any non-empty argument list
        assertNullPointerExceptionNotThrown(new Object(), new Object());
        assertNullPointerExceptionNotThrown("test");
        assertNullPointerExceptionNotThrown("");

        // argument lists with just one null at the beginning
        assertNullPointerExceptionThrown((Object) null);
        assertNullPointerExceptionThrown(null, "", new Object());
        assertNullPointerExceptionThrown(null, new Object(), new Object());

        // argument lists with nulls in the middle
        assertNullPointerExceptionThrown(new Object(), null, null, "test");
        assertNullPointerExceptionThrown("", null, new Object());

        // argument lists with one null as the last argument
        assertNullPointerExceptionThrown("", new Object(), null);
        assertNullPointerExceptionThrown(new Object(), new Object(), null);

        // null reference
        assertNullPointerExceptionThrown((Object[]) null);

        // confirms nulls inside lists in the argument list are not considered
        List<Object> containingNull = Arrays.asList((Object) null);
        assertNullPointerExceptionNotThrown(containingNull, new Object());
    }

    @Test
    public void requireAllNonNullCollection() {
        // lists containing nulls in the front
        assertNullPointerExceptionThrown(Arrays.asList((Object) null));
        assertNullPointerExceptionThrown(Arrays.asList(null, new Object(), ""));

        // lists containing nulls in the middle
        assertNullPointerExceptionThrown(Arrays.asList("spam", null, new Object()));
        assertNullPointerExceptionThrown(Arrays.asList("spam", null, "eggs", null, new Object()));

        // lists containing nulls at the end
        assertNullPointerExceptionThrown(Arrays.asList("spam", new Object(), null));
        assertNullPointerExceptionThrown(Arrays.asList(new Object(), null));

        // null reference
        assertNullPointerExceptionThrown((Collection<Object>) null);

        // empty list
        assertNullPointerExceptionNotThrown(Collections.emptyList());

        // list with all non-null elements
        assertNullPointerExceptionNotThrown(Arrays.asList(new Object(), "ham", Integer.valueOf(1)));
        assertNullPointerExceptionNotThrown(Arrays.asList(new Object()));

        // confirms nulls inside nested lists are not considered
        List<Object> containingNull = Arrays.asList((Object) null);
        assertNullPointerExceptionNotThrown(Arrays.asList(containingNull, new Object()));
    }

    @Test
    public void requireNonEmptyCollection() {
        // non-empty lists
        assertDoesNotThrow(() -> requireNonEmpty(Arrays.asList("spam", null, new Object())));
        assertDoesNotThrow(() -> requireNonEmpty(Arrays.asList("spam", null, "eggs", null, new Object())));

        // empty list
        assertIllegalArgumentExceptionThrown(() -> requireNonEmpty(Collections.emptyList()));

        // confirms empty nested lists are not considered
        List<Object> containingEmptyList = Arrays.asList(Collections.emptyList());
        assertDoesNotThrow(() -> requireNonEmpty(Arrays.asList(containingEmptyList, new Object())));
    }

    @Test
    public void requireAllNonBlankStringCollection() {
        // all strings non-blank
        assertDoesNotThrow(() -> requireAllNonBlankString(Arrays.asList("spam", "ham", "cam")));
        assertDoesNotThrow(() -> requireAllNonBlankString(Arrays.asList("spam", "ham", "eggs", "ok", "x")));
        assertDoesNotThrow(() -> requireAllNonBlankString(Arrays.asList()));

        // all strings blank
        assertIllegalArgumentExceptionThrown(() -> requireAllNonBlankString(Arrays.asList("", "       ",
                "\n\n", "\t  \n \t\t\n")));
        assertIllegalArgumentExceptionThrown(() -> requireAllNonBlankString(Arrays.asList("\t\t \r\n \t\r",
                "\n\n", "\t  \n \t\t\n")));
    }

    @Test
    public void combine() {
        // test with set
        Collection<Object> input1 = new HashSet<>(List.of(1, 3, 5, 7));
        Collection<Object> input2 = new HashSet<>(List.of(2, 4, 6, 8));
        Collection<Object> expectedOutput = List.of(1, 3, 5, 7, 2, 4, 6, 8);
        assertEquals(expectedOutput, CollectionUtil.combine(List.of(input1, input2)));

        // test with list
        input1 = List.of("a", "b", "c");
        input2 = List.of("x", "y", "z");
        expectedOutput = List.of("a", "b", "c", "x", "y", "z");
        assertEquals(expectedOutput, CollectionUtil.combine(List.of(input1, input2)));
    }

    @Test
    public void requireOnlyOneIsTrue() {
        // more than one, or less than one is true
        assertIllegalArgumentExceptionThrown(() -> CollectionUtil.requireExactlyOneIsTrue(List.of(false, true, true)));
        assertIllegalArgumentExceptionThrown(() -> CollectionUtil.requireExactlyOneIsTrue(List.of(true, true, true)));
        assertIllegalArgumentExceptionThrown(() -> CollectionUtil.requireExactlyOneIsTrue(
                List.of(false, false, false)));

        // exactly one is true
        assertDoesNotThrow(() -> CollectionUtil.requireExactlyOneIsTrue(List.of(false, true, false, false)));
        assertDoesNotThrow(() -> CollectionUtil.requireExactlyOneIsTrue(List.of(true)));
    }

    @Test
    public void isAnyNonNull() {
        assertFalse(CollectionUtil.isAnyNonNull());
        assertFalse(CollectionUtil.isAnyNonNull((Object) null));
        assertFalse(CollectionUtil.isAnyNonNull((Object[]) null));
        assertTrue(CollectionUtil.isAnyNonNull(new Object()));
        assertTrue(CollectionUtil.isAnyNonNull(new Object(), null));
    }

    /**
     * Asserts that {@code CollectionUtil#requireAllNonNull(Object...)} throw {@code NullPointerException}
     * if {@code objects} or any element of {@code objects} is null.
     */
    private void assertNullPointerExceptionThrown(Object... objects) {
        assertThrows(NullPointerException.class, () -> requireAllNonNull(objects));
    }

    /**
     * Asserts that {@code CollectionUtil#requireAllNonNull(Collection<?>)} throw {@code NullPointerException}
     * if {@code collection} or any element of {@code collection} is null.
     */
    private void assertNullPointerExceptionThrown(Collection<?> collection) {
        assertThrows(NullPointerException.class, () -> requireAllNonNull(collection));
    }

    private void assertNullPointerExceptionNotThrown(Object... objects) {
        requireAllNonNull(objects);
    }

    private void assertNullPointerExceptionNotThrown(Collection<?> collection) {
        requireAllNonNull(collection);
    }

    /**
     * Asserts that {@code CollectionUtil#requireNonEmpty(Collection<?>)} throw {@code IllegalArgumentException}
     * if {@code collection} is empty.
     */
    private void assertIllegalArgumentExceptionThrown(Executable f) {
        assertThrows(IllegalArgumentException.class, f);
    }
}
