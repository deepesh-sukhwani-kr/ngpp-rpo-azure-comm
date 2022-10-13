package com.kroger.ngpp.jsonreader;

/**
 * Indicates there's an issue while connecting to the closeable source
 */
public class JsonReaderInitException extends RuntimeException {

    public JsonReaderInitException(String message, Throwable cause) {
        super(message, cause);
    }
}
