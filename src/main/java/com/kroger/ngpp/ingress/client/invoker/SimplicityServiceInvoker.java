package com.kroger.ngpp.ingress.client.invoker;

import com.kroger.ngpp.boot.autoconfigure.security.oauth.WebClientWrapper;
import com.kroger.ngpp.ingress.client.SimpleOauthWebClient;
import com.kroger.ngpp.ingress.client.builder.SimpleUrlBuilder;
import com.kroger.ngpp.ingress.model.RpoSmsParam;
import com.kroger.ngpp.ingress.model.SimpleDeliveryModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import static com.kroger.ngpp.util.ClientRegistrationEnum.SIMPLICITY_SERVICE;


@Slf4j
@Component
public class SimplicityServiceInvoker implements SimpleServiceInvoker {

    @Value("${rpo.simplicity.url: test}")
    private String urlString;

    @Autowired
    private WebClientWrapper webClient;

    private final SimpleUrlBuilder builder;


    public SimplicityServiceInvoker(SimpleUrlBuilder builder) {

        this.builder = builder;
    }

    @Override
    public SimpleDeliveryModel getSimplicityResponse(RpoSmsParam model) {
        log.info("Inside SimplicityServiceInvoker::getSimplicityResponse");
        String qualifiedUrl = builder.buildIngresControllerURL(
                builder.mapQueryParameters(model),
                urlString
        );
        log.info("SimplicityServiceInvoker::qualifiedUrl = " + qualifiedUrl);
        return webClient.GET(SIMPLICITY_SERVICE.getId(),
                qualifiedUrl, SimpleDeliveryModel.class).block();
    }

}
