package com.kroger.ngpp.ingress.client.invoker;

import com.kroger.ngpp.ingress.client.SimpleOauthRestClient;
import com.kroger.ngpp.ingress.client.builder.SimpleUrlBuilder;
import com.kroger.ngpp.ingress.model.RpoSmsParam;
import com.kroger.ngpp.ingress.model.SimpleDeliveryModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class SimplicityServiceInvoker implements SimpleServiceInvoker {

    @Value("${rpo.simplicity.url: test}")
    private String urlString;

    private final SimpleOauthRestClient restClient;

    private final SimpleUrlBuilder builder;


    public SimplicityServiceInvoker(SimpleOauthRestClient restClient, SimpleUrlBuilder builder) {
        this.restClient = restClient;
        this.builder = builder;
    }

    @Override
    public ResponseEntity<SimpleDeliveryModel> getSimplicityResponse(RpoSmsParam model) {
        log.info("Inside SimplicityServiceInvoker::getSimplicityResponse");
        String qualifiedUrl = builder.buildIngresControllerURL(
                builder.mapQueryParameters(model),
                urlString
        );
        log.info("SimplicityServiceInvoker::qualifiedUrl = " + qualifiedUrl);
        return restClient.invokeGetOperation(
                qualifiedUrl, SimpleDeliveryModel.class);
    }

}