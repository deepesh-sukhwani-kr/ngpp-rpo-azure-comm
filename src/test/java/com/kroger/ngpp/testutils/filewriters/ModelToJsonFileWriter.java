package com.kroger.ngpp.testutils.filewriters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kroger.ngpp.testutils.exceptions.TestSetupException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

public class ModelToJsonFileWriter<E> {

    private final ObjectMapper objectMapper;

    public ModelToJsonFileWriter(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void writeRandomJsonsTo(final String fileName, final List<E> models)
            throws IOException {

        try (final OutputStream out = new FileOutputStream(fileName);
             final PrintWriter writer = new PrintWriter(out)) {

            models.forEach(elem -> {
                writer.println(convertObjectToJson(elem));
            });

            writer.flush();
        }
    }

    private String convertObjectToJson(final E model) {
        try {
            return objectMapper.writeValueAsString(model);
        }
        catch (JsonProcessingException e) {
            throw new TestSetupException("Failed to write random jsons to a new file", e);
        }
    }
}
