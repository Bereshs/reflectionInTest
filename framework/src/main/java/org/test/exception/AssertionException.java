package org.test.exception;

public class AssertionException extends Exception {
    public <T> AssertionException(String message) {
        super(message);
    }
}
