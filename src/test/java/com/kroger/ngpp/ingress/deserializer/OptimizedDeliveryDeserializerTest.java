package com.kroger.ngpp.ingress.deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kroger.ngpp.ingress.model.OptimizedDeliveryModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OptimizedDeliveryDeserializerTest {

    private String json;

    @BeforeEach
    public void setUp() {
        json = "{ \"urls\" : [\"First message\"," +
                "\"Second message\"," +
                "\"Third message\"]}";
    }

    @Test
    public void deserialize() throws IOException {
        OptimizedDeliveryModel model = new ObjectMapper().readValue(json,
                OptimizedDeliveryModel.class);
        List<String> arrayList = Arrays.asList("First message", "Second message", "Third message");
        assertEquals(arrayList, model.getBlobUrlList());
    }

}
