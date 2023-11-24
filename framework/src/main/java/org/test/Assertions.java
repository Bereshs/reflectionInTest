package org.test;

import org.test.exception.AssertionException;

public class Assertions {
    public static void assertEquals(Object expected, Object actual, String message) throws AssertionException {
        // TODO: add implementations
        if(!expected.equals(actual)) {
            throw new AssertionException("Expected " + expected + " but was " + actual);
        }
    }

    public static void assertNotEquals(Object expected, Object actual, String message) throws AssertionException {
        // TODO: add implementations
        if(expected.equals(actual)) {
            throw new AssertionException("Expected " + expected + " but was " + actual);
        }
    }
}
