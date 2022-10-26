package com.kroger.ngpp.testutils.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDate;

@Value
@Builder
@Jacksonized
public class TestModel {
    @JsonProperty("differentName")
    String name;
    Integer    age;
    Occupation occupation;


    @Value
    @Builder
    @Jacksonized
    public static class Occupation {
        String description;
        Double salary;
        @JsonProperty("start_week")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd")
        LocalDate localDate;
    }
}