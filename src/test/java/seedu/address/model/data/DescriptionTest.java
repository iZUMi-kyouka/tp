package seedu.address.model.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.recruit.data.Description;

public class DescriptionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Description((String) null));
    }



    @Test
    public void isValidDescription() {
        // null address
        assertThrows(NullPointerException.class, () -> Description.isValidDescription(null));

        // valid descriptions
        assertTrue(Description.isValidDescription("")); // empty string
        assertTrue(Description.isValidDescription(" ")); // blank string
        assertTrue(Description.isValidDescription("-")); // use of symbols

        // use of symbols in proper description
        assertTrue(Description.isValidDescription(
                "Experienced full-stack developer with 5 years in Java and React."
                     + " Specializes in UI/UX design and accessibility best practices."));
        // use of multi-lines
        assertTrue(Description.isValidDescription("""
                List of past projects:
                1. Developed an internal HR dashboard for employee tracking.
                2. Built an automated candidate screening tool using Python.
                3. Deployed a microservice-based recruitment analytics system on AWS.
                """));
    }

    @Test
    void appendDescription_normalCombination_combinesText() {
        Description d1 = new Description("First part");
        Description d2 = new Description("second part");

        Description combined = d1.appendDescription(d2);

        assertEquals("First part second part", combined.toString());
    }

    @Test
    void appendDescription_withWhitespace_stripsProperly() {
        Description d1 = new Description("  Hello  ");
        Description d2 = new Description("  World  ");

        Description combined = d1.appendDescription(d2);

        assertEquals("Hello World", combined.toString());
    }

    @Test
    void appendDescription_withEmptyOther_returnsCopyOfOriginal() {
        Description d1 = new Description("Something");
        Description empty = Description.createEmptyDescription();

        Description result = d1.appendDescription(empty);

        assertEquals(d1, result);
        assertNotSame(d1, result, "Should return a new instance, not same object");
    }

    @Test
    void appendDescription_emptyAppendsToNonEmpty_returnsOtherValue() {
        Description empty = Description.createEmptyDescription();
        Description d1 = new Description("Content");

        Description result = empty.appendDescription(d1);

        assertEquals("Content", result.toString());
    }

    @Test
    void isEmptyDescription_onlySingletonReturnsTrue() {
        Description empty = Description.createEmptyDescription();
        Description anotherEmpty = new Description("");

        assertTrue(empty.isEmptyDescription());
        assertFalse(anotherEmpty.isEmptyDescription(),
                "New Description(\"\") should not be treated as singleton empty");
    }

    @Test
    void emptyDescriptionEquality_valueBasedEqualityHolds() {
        Description empty = Description.createEmptyDescription();
        Description other = new Description("");

        // They are equal because both have the same value
        assertEquals(empty, other);

        // But only singleton reports itself as empty
        assertTrue(empty.isEmptyDescription());
        assertFalse(other.isEmptyDescription());
    }

    @Test
    void equals_sameValue_returnsTrue() {
        Description d1 = new Description("foo");
        Description d2 = new Description("foo");
        assertEquals(d1, d2);
        assertEquals(d1.hashCode(), d2.hashCode());
    }

    @Test
    void equals_differentValues_returnsFalse() {
        Description d1 = new Description("foo");
        Description d2 = new Description("bar");
        assertNotEquals(d1, d2);
    }

    @Test
    void equals_self_returnsTrue() {
        Description d = new Description("test");
        assertEquals(d, d);
    }

    @Test
    void equals_nullOrDifferentType_returnsFalse() {
        Description d = new Description("test");
        assertNotEquals(null, d);
        assertNotEquals("test", d);
    }

    @Test
    void copyConstructor_createsEqualButDistinctInstance() {
        Description d1 = new Description("original");
        Description d2 = new Description(d1);

        assertEquals(d1, d2);
        assertNotSame(d1, d2);
    }

    @Test
    void createEmptyDescription_returnsSingleton() {
        Description empty1 = Description.createEmptyDescription();
        Description empty2 = Description.createEmptyDescription();

        assertSame(empty1, empty2, "EmptyDescription should be a singleton instance");
        assertTrue(empty1.isEmptyDescription(), "Singleton empty description should report empty");
        assertEquals("", empty1.toString(), "EmptyDescription string should be blank");
    }

    @Test
    public void equals() {
        Description description = new Description("Valid Description");

        // same values -> returns true
        assertTrue(description.equals(new Description("Valid Description")));

        // same object -> returns true
        assertTrue(description.equals(description));

        // null -> returns false
        assertFalse(description.equals(null));

        // different types -> returns false
        assertFalse(description.equals(5.0f));

        // different values -> returns false
        assertFalse(description.equals(new Description("Other Valid Description")));
    }
}
