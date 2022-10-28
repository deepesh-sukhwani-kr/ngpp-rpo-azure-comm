package com.kroger.ngpp.ingress.mapper;

import org.springframework.http.ResponseEntity;

public interface HttpResponseValidator<T> {
    boolean validateResponseIsOk(ResponseEntity<T> response);
    boolean validateResponseIsCreated(ResponseEntity<T> response);
    boolean validateResponseHasNoContent(ResponseEntity<T> response);
    boolean validateResponseIsAccepted(ResponseEntity<T> response);
}
