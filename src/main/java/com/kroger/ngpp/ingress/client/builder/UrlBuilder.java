package com.kroger.ngpp.ingress.client.builder;

import com.kroger.desp.events.effo.data.ready.EffoDataReady;
import com.kroger.ngpp.common.utils.OptimizedPriceConstants;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Component
public class UrlBuilder {

    public String buildIngresControllerURL(MultiValueMap<String, String> map, String urlString) {
        return UriComponentsBuilder
                .fromUriString(decode(urlString))
                .queryParams(map)
                .build().toString();
    }

    public String decode(String urlString) throws NullPointerException {
        if (StringUtils.isEmpty(urlString))
            throw new NullPointerException("Input url String is Null or Empty");
        return URLDecoder.decode(urlString, StandardCharsets.UTF_8);
    }

    public MultiValueMap<String, String> mapQueryParameters(EffoDataReady model) {
        if (ObjectUtils.isEmpty(model))
            return null;
        MultiValueMap<String, String> queryMap = new LinkedMultiValueMap<>();
        queryMap.add(OptimizedPriceConstants.OPTIMIZATION_PRICE_TYPE_LABEL, model.getPayload().getScience());
        queryMap.add(OptimizedPriceConstants.OPTIMIZATION_BATCH_ID_LABEL, model.getPayload().getBatchId());
        return queryMap;
    }

}
