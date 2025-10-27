package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class IntervalTest {

    @Test
    public void constructor() {
        // Integer interval
        Interval<Integer> i1 = new Interval<>(-10, 10);

        // Double interval
        Interval<Double> i2 = new Interval<>(-10.99, 9.99);

        // Char interval
        Interval<Character> i3 = new Interval<>('a', 'z');
    }

    @Test
    public void contains() {
        // Integer interval
        Interval<Integer> i1 = new Interval<>(-10, 10);
        assertTrue(i1 instanceof Interval);
        assertTrue(i1.contains(0));
        assertTrue(i1.contains(10));
        assertTrue(i1.contains(-10));
        assertFalse(i1.contains(-11));
        assertFalse(i1.contains(11));

        // Double interval
        Interval<Double> i2 = new Interval<>(-10.99, 9.99);
        assertTrue(i1 instanceof Interval);
        assertTrue(i2.contains(0.001));
        assertTrue(i2.contains(-10.99));
        assertTrue(i2.contains(9.99));
        assertFalse(i2.contains(10.99));
    }
}
