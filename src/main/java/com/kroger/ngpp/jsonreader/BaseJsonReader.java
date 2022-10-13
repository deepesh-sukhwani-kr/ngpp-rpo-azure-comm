package com.kroger.ngpp.jsonreader;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

public abstract class BaseJsonReader<E> implements JsonReader<E> {

    private final JsonExtractor<E> jsonExtractor;
    private       E                nextObject;
    private       boolean          isInitialized;

    public BaseJsonReader(
            final InputStream inputStream,
            final ObjectMapper objectMapper,
            final Class<E> clazz) {
        this.jsonExtractor = new JsonExtractor<>(inputStream, objectMapper, clazz);
        this.isInitialized = false;
        this.nextObject = null;
    }

    @Override
    public boolean hasNext() {
        if (!this.isInitialized) {
            this.init();
        }
        return this.nextObject != null;
    }

    @Override
    public E next() {
        if (!this.isInitialized) {
            this.init();
        }
        final E returnValue = this.nextObject;
        this.bufferNext();
        return returnValue;
    }

    private void bufferNext() {
        this.nextObject = this.jsonExtractor.extractNextJsonObject();
    }

    private void init() {
        this.jsonExtractor.init();
        this.bufferNext();
        this.isInitialized = true;
    }

    @Override
    public void close() throws IOException {
        this.jsonExtractor.close();
    }
    
}
