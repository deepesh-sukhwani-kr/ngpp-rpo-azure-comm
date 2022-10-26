package com.kroger.ngpp.jsonreader;

/**
 * Indicates there's an issue while reading an element from the source
 */
public class JsonReaderException extends RuntimeException {

    public JsonReaderException(String message, Throwable cause) {
        super(message, cause);
    }
}
