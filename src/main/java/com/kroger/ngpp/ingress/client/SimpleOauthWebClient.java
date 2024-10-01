package com.kroger.ngpp.ingress.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class SimpleOauthWebClient  {


    private final WebClient webClient;

    @Autowired
    public SimpleOauthWebClient(@Qualifier("webClientBuilder") WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }
    public <T> Mono<ResponseEntity<T>> invokeGetOperation(String urlString, Class<T> clazz) {
        return webClient
                .get()
                .uri(urlString)
                .retrieve()
                .toEntity(clazz);
    }


}
