package com.kroger.ngpp.ingress.client.builder;

import com.kroger.ngpp.common.utils.OptimizedPriceConstants;
import com.kroger.ngpp.ingress.model.RpoSmsParam;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Component
public class SimpleUrlBuilder {

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

    public MultiValueMap<String, String> mapQueryParameters(RpoSmsParam model) {
        if (ObjectUtils.isEmpty(model))
            return null;
        MultiValueMap<String, String> queryMap = new LinkedMultiValueMap<>();
        queryMap.add(OptimizedPriceConstants.RPO_EXECUTION_DATE, model.getExecutionDate());
        queryMap.add(OptimizedPriceConstants.RPO_RUN_TYPE, model.getRunType());
        queryMap.add(OptimizedPriceConstants.RPO_SNAPSHOT, model.getSnapshot());
        return queryMap;
    }

}
