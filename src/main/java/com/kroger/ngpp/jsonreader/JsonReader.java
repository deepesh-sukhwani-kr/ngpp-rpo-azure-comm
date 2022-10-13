package com.kroger.ngpp.jsonreader;

import java.io.Closeable;
import java.io.IOException;
import java.util.Iterator;

/**
 * Reads and maps serialized json elements iteratively from a closeable source
 *
 * @param <E> Type that will be read from the closeable source
 */
public interface JsonReader<E> extends Iterator<E>, Closeable {

    /**
     * @return the next mapped element
     * @throws JsonReaderInitException when there's a problem while connecting to the source
     * @throws JsonReaderException     when an element can't be mapped to the specified type
     */
    @Override
    E next();

    /**
     * Closes the underlying closable source
     *
     * @throws IOException – if an I/O error occurs
     */
    @Override
    void close() throws IOException;
}
