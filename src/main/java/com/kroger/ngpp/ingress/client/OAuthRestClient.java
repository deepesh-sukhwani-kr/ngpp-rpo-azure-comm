package com.kroger.ngpp.ingress.client;

import com.kroger.ngpp.common.exception.RestTemplateResponseErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OAuthRestClient {

    private OAuth2RestTemplate oAuth2RestTemplate;

    @Autowired
    public OAuthRestClient(@Qualifier("oAuth2RestTemplateConfig") OAuth2RestTemplate oAuth2RestTemplate) {
        this.oAuth2RestTemplate = oAuth2RestTemplate;
    }

    public <T> ResponseEntity<T> invokeGetOperation(String urlString, Class<T> clazz) {
        setClientProperties();
        return oAuth2RestTemplate.getForEntity(urlString, clazz);
    }

    public <T> ResponseEntity<T> invokePutOperation(String urlString, HttpEntity<?> body, Class<T> clazz) {
        setClientProperties();
        return oAuth2RestTemplate.exchange(urlString, HttpMethod.PUT, body, clazz);
    }

    private void setClientProperties() {
        oAuth2RestTemplate.setErrorHandler(new RestTemplateResponseErrorHandler(new RestTemplate().getMessageConverters()));
    }

}
