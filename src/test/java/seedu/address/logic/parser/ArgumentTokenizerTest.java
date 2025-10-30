package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;


public class ArgumentTokenizerTest {

    private final Prefix unknownPrefix = new Prefix("--u");
    private final Prefix pSlash = new Prefix("p/");
    private final Prefix dashT = new Prefix("-t");
    private final Prefix hatQ = new Prefix("^Q");

    @Test
    public void tokenize_emptyArgsString_noValues() {
        String argsString = "  ";
        ArgumentMultimap argMultimap = tokenizeWithoutError(argsString, pSlash);

        assertPreambleEmpty(argMultimap);
        assertArgumentAbsent(argMultimap, pSlash);
    }

    private void assertPreamblePresent(ArgumentMultimap argMultimap, String expectedPreamble) {
        assertEquals(expectedPreamble, argMultimap.getPreamble());
    }

    private void assertPreambleEmpty(ArgumentMultimap argMultimap) {
        assertTrue(argMultimap.getPreamble().isEmpty());
    }

    /**
     * Asserts all the arguments in {@code argMultimap} with {@code prefix} match the {@code expectedValues}
     * and only the last value is returned upon calling {@code ArgumentMultimap#getValue(Prefix)}.
     */
    private void assertArgumentPresent(ArgumentMultimap argMultimap, Prefix prefix, String... expectedValues) {

        // Verify the last value is returned
        assertEquals(expectedValues[expectedValues.length - 1], argMultimap.getValue(prefix).get());

        // Verify the number of values returned is as expected
        assertEquals(expectedValues.length, argMultimap.getAllValues(prefix).size());

        // Verify all values returned are as expected and in order
        for (int i = 0; i < expectedValues.length; i++) {
            assertEquals(expectedValues[i], argMultimap.getAllValues(prefix).get(i));
        }
    }

    private void assertArgumentAbsent(ArgumentMultimap argMultimap, Prefix prefix) {
        assertFalse(argMultimap.getValue(prefix).isPresent());
    }

    @Test
    public void tokenize_noPrefixes_allTakenAsPreamble() {
        String argsString = "  some random string /t tag with leading and trailing spaces ";
        ArgumentMultimap argMultimap = tokenizeWithoutError(argsString);

        // Same string expected as preamble, but leading/trailing spaces should be trimmed
        assertPreamblePresent(argMultimap, argsString.trim());

    }

    @Test
    public void tokenize_oneArgument() {
        // Preamble present
        String argsString = "  Some preamble string p/ Argument value ";
        ArgumentMultimap argMultimap = tokenizeWithoutError(argsString, pSlash);
        assertPreamblePresent(argMultimap, "Some preamble string");
        assertArgumentPresent(argMultimap, pSlash, "Argument value");

        // No preamble
        argsString = " p/   Argument value ";
        argMultimap = tokenizeWithoutError(argsString, pSlash);
        assertPreambleEmpty(argMultimap);
        assertArgumentPresent(argMultimap, pSlash, "Argument value");

    }

    @Test
    public void tokenize_multipleArguments() {
        // Only two arguments are present
        String argsString = "SomePreambleString -t dashT-Value p/pSlash value";
        ArgumentMultimap argMultimap = tokenizeWithoutError(argsString, pSlash, dashT, hatQ);
        assertPreamblePresent(argMultimap, "SomePreambleString");
        assertArgumentPresent(argMultimap, pSlash, "pSlash value");
        assertArgumentPresent(argMultimap, dashT, "dashT-Value");
        assertArgumentAbsent(argMultimap, hatQ);

        // All three arguments are present
        argsString = "Different Preamble String ^Q111 -t dashT-Value p/pSlash value";
        argMultimap = tokenizeWithoutError(argsString, pSlash, dashT, hatQ);
        assertPreamblePresent(argMultimap, "Different Preamble String");
        assertArgumentPresent(argMultimap, pSlash, "pSlash value");
        assertArgumentPresent(argMultimap, dashT, "dashT-Value");
        assertArgumentPresent(argMultimap, hatQ, "111");

        /* Also covers: Reusing of the tokenizer multiple times */

        // Reuse tokenizer on an empty string to ensure ArgumentMultimap is correctly reset
        // (i.e. no stale values from the previous tokenizing remain)
        argsString = "";
        argMultimap = tokenizeWithoutError(argsString, pSlash, dashT, hatQ);
        assertPreambleEmpty(argMultimap);
        assertArgumentAbsent(argMultimap, pSlash);

        /* Also covers: testing for prefixes not specified as a prefix */

        // Prefixes not previously given to the tokenizer should not return any values
        argsString = unknownPrefix + "some value";
        argMultimap = tokenizeWithoutError(argsString, pSlash, dashT, hatQ);
        assertArgumentAbsent(argMultimap, unknownPrefix);
        assertPreamblePresent(argMultimap, argsString); // Unknown prefix is taken as part of preamble
    }

    @Test
    public void tokenize_multipleArgumentsWithRepeats() {
        // Two arguments repeated, some have empty values
        String argsString = "SomePreambleString -t dashT-Value ^Q ^Q -t another dashT value p/ pSlash value -t";
        ArgumentMultimap argMultimap = tokenizeWithoutError(argsString, pSlash, dashT, hatQ);
        assertPreamblePresent(argMultimap, "SomePreambleString");
        assertArgumentPresent(argMultimap, pSlash, "pSlash value");
        assertArgumentPresent(argMultimap, dashT, "dashT-Value", "another dashT value", "");
        assertArgumentPresent(argMultimap, hatQ, "", "");
    }

    @Test
    public void tokenize_multipleArgumentsJoined() {
        String argsString = "SomePreambleStringp/ pSlash joined-tjoined -t not joined^Qjoined";
        ArgumentMultimap argMultimap = tokenizeWithoutError(argsString, pSlash, dashT, hatQ);
        assertPreamblePresent(argMultimap, "SomePreambleStringp/ pSlash joined-tjoined");
        assertArgumentAbsent(argMultimap, pSlash);
        assertArgumentPresent(argMultimap, dashT, "not joined^Qjoined");
        assertArgumentAbsent(argMultimap, hatQ);
    }

    @Test
    public void tokenize_doublequoteUsedToEscapeParamProperly_success() {
        Prefix dashN = new Prefix("-n");
        Prefix sSlash = new Prefix("S/");
        Prefix dSlash = new Prefix("D/");

        // Single double-quoted arguments
        String argsString = "1 -n \"Noor-nahida Binte Afiq S/O Srinivasan D/O Ruyeet\" S/Software Engineer";
        ArgumentMultimap argMultimap = tokenizeWithoutError(argsString, dashN, sSlash);
        assertArgumentAbsent(argMultimap, dSlash);
        assertArgumentPresent(argMultimap, sSlash, "Software Engineer");
        assertArgumentPresent(argMultimap, dashN, "Noor-nahida Binte Afiq S/O Srinivasan D/O Ruyeet");

        // Multiple double-quoted arguments
        Prefix dashA = new Prefix("-A");
        Prefix atSymbol = new Prefix("@");
        Prefix plusSixTwo = new Prefix("+62");
        argsString = "2 -n \"Htet Aung Zaw @ Mac-Allister John Atkinson\" p/\"+62 878 000 000\"";
        argMultimap = tokenizeWithoutError(argsString, dashN, pSlash, dashA, atSymbol, plusSixTwo);

        assertArgumentAbsent(argMultimap, dashA);
        assertArgumentAbsent(argMultimap, atSymbol);
        assertArgumentAbsent(argMultimap, plusSixTwo);
        assertArgumentPresent(argMultimap, pSlash, "+62 878 000 000");
        assertArgumentPresent(argMultimap, dashN, "Htet Aung Zaw @ Mac-Allister John Atkinson");
    }

    @Test
    public void tokenize_escapedDoublequoteInsideEscapedParam_success() {
        Prefix dashN = new Prefix("-n");
        Prefix dashA = new Prefix("-A");
        String argsString = "2 -n \"Htet Aung Zaw @ Mac-Allister \\\"Yamamoto\\\" Atkinson\" p/\"78787878\"";
        ArgumentMultimap argMultimap = tokenizeWithoutError(argsString, dashN, pSlash, dashA);

        assertArgumentAbsent(argMultimap, dashA);
        assertArgumentPresent(argMultimap, pSlash, "78787878");
        assertArgumentPresent(argMultimap, dashN, "Htet Aung Zaw @ Mac-Allister \"Yamamoto\" Atkinson");
    }

    public void tokenize_escapingDoublequoteUsed_success() {
        Prefix dashN = new Prefix("-n");
        Prefix dashA = new Prefix("-A");
        Prefix dashF = new Prefix("-F");
        String argsString = "1 -A qwerty\\\"@ -n \"Tonald \\\"America-First\\\" Drump\" -A melon@\\\"musk";
        ArgumentMultimap argMultimap = tokenizeWithoutError(argsString, dashN, dashA, dashF);

        assertArgumentAbsent(argMultimap, dashN);
        assertArgumentAbsent(argMultimap, dashF);
        assertArgumentPresent(argMultimap, dashA, "qwerty\"@", "melon@\"musk");
        assertArgumentPresent(argMultimap, dashN, "Tonald \"America-First\" Drump");
    }

    public void tokenize_escapingDoublequoteNotUsed_unexpectedResult() {
        Prefix dashN = new Prefix("-n");
        Prefix dashA = new Prefix("-A");
        String argsString = "1 -A qwerty\"@ -n Ji Xinping -A ppc@\"party";
        ArgumentMultimap argMultimap = tokenizeWithoutError(argsString, dashN, dashA);

        assertArgumentAbsent(argMultimap, dashN);
        assertArgumentPresent(argMultimap, dashA, "qwerty\"@ -n Ji Xinping -A ppc@\"party");
    }

    @Test
    public void equalsMethod() {
        Prefix aaa = new Prefix("aaa");

        assertEquals(aaa, aaa);
        assertEquals(aaa, new Prefix("aaa"));

        assertNotEquals(aaa, "aaa");
        assertNotEquals(aaa, new Prefix("aab"));
    }

    @Test
    public void tokenize_unescapedQuote_throwsParseException() {
        String argsString = " -n This is an \"invalid\" string";

        String expectedMessage = ParserUtil.MESSAGE_ILLEGAL_QUOTATION;
        try {
            ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(argsString, new Prefix("-n"));
        } catch (ParseException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    public void tokenize_unclosedEscapeSequence_throwsParseException() {
        String argsString = " -n This is a backslash at the end\\";

        String expectedMessage = ParserUtil.MESSAGE_UNCLOSED_ESCAPE;
        try {
            ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(argsString, new Prefix("-n"));
        } catch (ParseException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    private static ArgumentMultimap tokenizeWithoutError(String argsString, Prefix... prefixes) {
        try {
            return ArgumentTokenizer.tokenize(argsString, prefixes);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
