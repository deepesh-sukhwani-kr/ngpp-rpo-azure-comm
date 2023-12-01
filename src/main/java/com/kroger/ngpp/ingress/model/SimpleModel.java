package com.kroger.ngpp.ingress.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SimpleModel {

    @JsonProperty(value = "primary_zone")
    private String primaryZone;

    @JsonProperty(value = "premium_zone_exempt_programs")
    private String premiumZoneExemptPrograms;

    @JsonProperty(value = "premium_zones")
    private String premiumZones;

    @JsonProperty(value = "cats")
    private String categories;

}
