package com.kroger.ngpp.ingress.model.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.kroger.ngpp.common.utils.OptimizedPriceConstants;
import com.kroger.ngpp.ingress.model.OptimizedDeliveryModel;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class OptimizedDeliveryDeserializer extends JsonDeserializer<OptimizedDeliveryModel> {

    @Override
    public OptimizedDeliveryModel deserialize(JsonParser jsonParser,
            DeserializationContext deserializationContext)
            throws IOException {
        JsonNode root = jsonParser.readValueAsTree();
        JsonNode urlNodes = root.get(OptimizedPriceConstants.JSON_ELEMENT_URL);
        List<String> urls = new ArrayList<>();
        urlNodes.forEach(url -> {
            urls.add(URLDecoder.decode(url.asText(), StandardCharsets.UTF_8));
        });

        return OptimizedDeliveryModel.builder()
                .blobUrlList(urls)
                .build();
    }

}
