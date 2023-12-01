package com.kroger.ngpp.testutils;

import com.kroger.ngpp.ingress.model.RpoSmsParam;

public class SimpleUtils {

    public static RpoSmsParam getRPOparam() {
        return RpoSmsParam.builder()
                .executionDate("2023-01-01")
                .runType("objective")
                .snapshot(Boolean.TRUE.toString())
                .build();
    }

}
