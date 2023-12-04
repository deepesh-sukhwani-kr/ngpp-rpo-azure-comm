package com.kroger.ngpp.ingress.client;

import com.kroger.ngpp.common.exception.RestTemplateResponseErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SimpleOauthRestClient {

    private OAuth2RestTemplate oAuth2RestTemplate;

    @Autowired
    public SimpleOauthRestClient(@Qualifier("simpleOauth2RestTemplateConfig") OAuth2RestTemplate oAuth2RestTemplate) {
        this.oAuth2RestTemplate = oAuth2RestTemplate;
    }

    public <T> ResponseEntity<T> invokeGetOperation(String urlString, Class<T> clazz) {
        setClientProperties();
        return oAuth2RestTemplate.getForEntity(urlString, clazz);
    }

    private void setClientProperties() {
        oAuth2RestTemplate.setErrorHandler(new RestTemplateResponseErrorHandler(new RestTemplate().getMessageConverters()));
    }

}