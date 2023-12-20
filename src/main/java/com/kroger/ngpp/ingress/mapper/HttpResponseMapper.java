package com.kroger.ngpp.ingress.mapper;

import com.kroger.ngpp.common.logging.IRegularPriceOptimizationLogger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;

import static com.kroger.ngpp.common.logging.RegularPriceOptimizationLogFieldType.RESPONSE_META_DATA;
import static com.kroger.ngpp.common.logging.RegularPriceOptimizationServiceLogKeys.CONSUMER_SERVICE_MAPPED;

@Component
public class HttpResponseMapper<T> implements HttpResponseValidator<T> {
    private IRegularPriceOptimizationLogger logger;

    public HttpResponseMapper(IRegularPriceOptimizationLogger logger) {
        this.logger = logger;
    }

    public T mapResponseBody(ResponseEntity<T> response) {
        return (T) response.getBody();
    }

    public boolean validateResponseIsOk(ResponseEntity<T> response) {
        if (response.getStatusCode() == HttpStatus.OK) {
            return true;
        } else {
            logger.sendMessage("ERROR",
                    "Something went wrong in Rest service invocation: Service returned status code: " + response.getStatusCode(),
                    HttpResponseMapper.class.getSimpleName(), null
            );
        }
        return false;
    }

    @Override
    public boolean validateResponseIsCreated(ResponseEntity<T> response) {
        if (response.getStatusCode() == HttpStatus.CREATED) {
            return true;
        } else {
            logger.sendMessage("ERROR",
                    "Something went wrong in Rest service invocation: Service returned status code: " + response.getStatusCode(),
                    HttpResponseMapper.class.getSimpleName(), null
            );
        }
        return false;
    }

    @Override
    public boolean validateResponseHasNoContent(ResponseEntity<T> response) {
        if (response.getStatusCode() == HttpStatus.NO_CONTENT) {
            return true;
        } else {
            logger.sendMessage("ERROR",
                    "Something went wrong in Rest service invocation: Service returned status code: " + response.getStatusCode(),
                    HttpResponseMapper.class.getSimpleName(), null
            );
        }
        return false;
    }

    @Override
    public boolean validateResponseIsAccepted(ResponseEntity<T> response) {
        if (response.getStatusCode() == HttpStatus.ACCEPTED) {
            return true;
        } else {
            logger.sendMessage("ERROR",
                    "Something went wrong in Rest service invocation: Service returned status code: " + response.getStatusCode(),
                    HttpResponseMapper.class.getSimpleName(), null
            );
        }
        return false;
    }

}
