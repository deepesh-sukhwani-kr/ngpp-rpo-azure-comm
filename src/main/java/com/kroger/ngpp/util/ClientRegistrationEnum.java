package com.kroger.ngpp.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ClientRegistrationEnum {

    OPTIMIZATION_SERVICE("optimization-service"),
    SIMPLICITY_SERVICE("simplicity-service");


    private final String id;
}
