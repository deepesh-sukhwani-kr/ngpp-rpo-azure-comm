package com.kroger.ngpp.ingress.deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kroger.ngpp.ingress.model.SimpleDeliveryModel;
import com.kroger.ngpp.ingress.model.SimpleModel;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimpleDeliveryDeserializerTest {


    @Test
    public void deserialize() throws IOException {
        SimpleDeliveryModel model = new ObjectMapper().readValue(new File("src/test/resources/SimplicityTransform.json"),
                SimpleDeliveryModel.class);
        List<SimpleModel> arrayList = Collections.singletonList(
                SimpleModel.builder().premiumZones("{\"706-0003-00-000\":[\"PREMIUM EXAMPLE\",0.05]}")
                .primaryZone("026-0002-00-000")
                .premiumZoneExemptPrograms("[\"CENTER STORE SIGN POS\",\"Non-Program\"]")
                .categories("[\"023\"]").build());

        assertEquals(arrayList.get(0), model.getResultJsonObject().get(0));
    }

}
