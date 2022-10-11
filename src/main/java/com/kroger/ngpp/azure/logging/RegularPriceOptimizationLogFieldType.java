package com.kroger.ngpp.common.logging;

/**
 * Enum for tracking logged fields that are in Echo under messageDetail field. Indicated fields are
 * indexed in Echo schema.
 */
public enum RegularPriceOptimizationLogFieldType {
    COMMODITY_CODE("commodityCode"),   // indexed in echo
    SERVICE_LOG_KEY("serviceLogKey");

    private String logFieldName; // this currently exists under messageDetail in Echo.

    RegularPriceOptimizationLogFieldType(String logFieldName) {
        this.logFieldName = logFieldName;
    }

    public String asString() {
        return logFieldName;
    }
}
