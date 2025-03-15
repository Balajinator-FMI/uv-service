package com.fmicodesproj.fmicodesprojuvservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class CurrentUvData {
    private Double uvIndex;
    private Double temperatureC;
    private Timestamp sunrise;
    private Timestamp sunset;
}
