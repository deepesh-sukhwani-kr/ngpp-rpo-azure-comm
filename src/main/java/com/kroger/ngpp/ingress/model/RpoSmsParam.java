package com.kroger.ngpp.ingress.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RpoSmsParam {

    String executionDate;

    String runType;

    String snapshot;

}
