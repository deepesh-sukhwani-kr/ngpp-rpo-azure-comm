package com.kroger.ngpp.testutils.exceptions;

public class MockException extends RuntimeException {
    public MockException(String message) {
        super(message);
    }

    public MockException(String message, Throwable cause) {
        super(message, cause);
    }
}
