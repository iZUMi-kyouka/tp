package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.recruit.Name;

public class TagTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tag(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTagName = "";
        assertThrows(IllegalArgumentException.class, () -> new Tag(invalidTagName));
    }

    @Test
    public void isValidTagName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Tag.isValidTagName(null));

        // invalid tag
        assertFalse(Tag.isValidTagName("")); // empty string
        assertFalse(Tag.isValidTagName(" ")); // spaces only
        assertFalse(Tag.isValidTagName("^")); // non-alphanumeric characters
        assertFalse(Tag.isValidTagName("quick*")); // contains non-alphanumeric characters


        // valid tag
        assertTrue(Name.isValidName("friend")); // alphabets only
        assertTrue(Name.isValidName("12345")); // numbers only
        assertTrue(Name.isValidName("no1")); // alphanumeric characters
        assertTrue(Name.isValidName("Polish")); // with capital letters
        assertTrue(Tag.isValidTagName("fast friends")); // contains space
        assertTrue(Name.isValidName("天才")); // non-English tag
    }

}
