package com.kroger.ngpp.jsonreader;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

class JsonExtractor<E> implements Closeable {

    private final InputStream  inputStream;
    private final ObjectMapper objectMapper;
    private final Class<E>     clazz;

    private JsonParser jsonParser;

    public JsonExtractor(
            final InputStream inputStream,
            final ObjectMapper objectMapper,
            final Class<E> clazz) {
        this.inputStream = inputStream;
        this.objectMapper = objectMapper;
        this.clazz = clazz;
    }

    public void init() {
        final JsonFactory jsonFactory = objectMapper.getFactory();
        try {
            this.jsonParser = jsonFactory.createParser(inputStream);
        }
        catch (IOException e) {
            throw new JsonReaderInitException("Error while initializing JsonParser", e);
        }
    }

    public E extractNextJsonObject() {
        final JsonToken jsonToken = moveToToken(JsonToken.START_OBJECT);
        if (jsonToken != null) {
            return extractJsonObject();
        }
        return null;
    }

    private JsonToken moveToToken(final JsonToken expectedToken) {
        JsonToken nextToken;
        do {
            try {
                nextToken = this.jsonParser.nextToken();
            }
            catch (IOException e) {
                throw new JsonReaderException("Error while parsing a json token", e);
            }
        }
        while (nextToken != expectedToken && nextToken != null);
        return nextToken;
    }

    private E extractJsonObject() {
        try {
            return this.jsonParser.readValueAs(clazz);
        }
        catch (IOException e) {
            throw new JsonReaderException("Error while parsing a json object", e);
        }
    }

    @Override
    public void close() throws IOException {
        if (this.jsonParser != null) {
            this.jsonParser.close();
        }
        if (this.inputStream != null) {
            this.inputStream.close();
        }
    }

}
