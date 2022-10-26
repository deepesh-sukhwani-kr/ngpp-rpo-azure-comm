package com.kroger.ngpp.jsonreader;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;

public interface JsonReaderFactory<E> {

    JsonReader<E> createJsonReader(InputStream inputStream, ObjectMapper objectMapper);
}
