package com.kroger.ngpp.testutils.objectmappers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class ObjectMappersFactory {

    public static ObjectMapper defaultObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper = datesAsTimestampsMapper(objectMapper, false);
        objectMapper = failOnUnknownPropertiesMapper(objectMapper, false);
        return objectMapper;
    }

    public static ObjectMapper failOnUnknownProperties() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper = datesAsTimestampsMapper(objectMapper, false);
        objectMapper = failOnUnknownPropertiesMapper(objectMapper, true);
        return objectMapper;
    }

    private static ObjectMapper datesAsTimestampsMapper(ObjectMapper objectMapper, boolean value) {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, value);
        return objectMapper;
    }

    private static ObjectMapper failOnUnknownPropertiesMapper(ObjectMapper objectMapper,
            boolean value) {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, value);
        return objectMapper;
    }
}
