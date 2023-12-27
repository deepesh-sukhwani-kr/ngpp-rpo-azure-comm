package com.kroger.ngpp.ingress.model.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.kroger.ngpp.ingress.model.SimpleDeliveryModel;
import com.kroger.ngpp.ingress.model.SimpleModel;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class SimpleDeliveryDeserializer extends JsonDeserializer<SimpleDeliveryModel> {

    @Override
    public SimpleDeliveryModel deserialize(JsonParser jsonParser,
            DeserializationContext deserializationContext)
            throws IOException {
        log.info("Inside SimpleDeliveryDeserializer::deserialize");
        List<SimpleModel> simpleModelList = new ArrayList<>();
        AtomicInteger incrementer = new AtomicInteger();

        JsonNode root = jsonParser.readValueAsTree();
        root.forEach(childNodes -> {
            log.info("SimpleDeliveryDeserializer:: childnodes = " + childNodes);
            incrementer.getAndIncrement();
            SimpleModel simpleModel = new SimpleModel();
            simpleModel.setPrimaryZone(getTextValuefromJsonNodes(childNodes,"primary_zone"));
            simpleModel.setPremiumZones(getStringValuefromJsonNodes(childNodes,"premium_zones"));
            simpleModel.setPremiumZoneExemptPrograms(getStringValuefromJsonNodes(childNodes,"premium_zone_exempt_programs"));
            simpleModel.setCategories(getStringValuefromJsonNodes(childNodes,"cats"));
            log.info("SimpleDeliveryDeserializer:: simpleModel = " + simpleModel);
            simpleModelList.add(simpleModel);
        });
        log.info("SimpleDeliveryDeserializer:: Total Number of records = " + incrementer.get());
        return SimpleDeliveryModel.builder()
                .resultJsonObject(simpleModelList)
                .build();
    }

    private String getStringValuefromJsonNodes(JsonNode childNodes,String keyString) {
        JsonNode node = getNode(childNodes,keyString);
        return (null != node) ? node.toString() : null;
    }

    /**
     * This method will get the value as "primary_zone": "026-0002-00-000",
     * instead of "primary_zone": "\"026-0002-00-000\"",
     * @param childNodes
     * @param keyString
     * @return
     */
    private String getTextValuefromJsonNodes(JsonNode childNodes,String keyString) {
        JsonNode node = getNode(childNodes,keyString);
        return (null != node) ? node.asText() : null;
    }

    private JsonNode getNode(JsonNode childNodes, String keyString) {
        return (null != childNodes) ? childNodes.get(keyString) : null;
    }


}
