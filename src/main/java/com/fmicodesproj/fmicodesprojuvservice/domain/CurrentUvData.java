package com.fmicodesproj.fmicodesprojuvservice.domain;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class CurrentUvData {
    private Double uvIndex;
    private Double temperatureC;
    private String weather;
    private Timestamp sunrise;
    private Timestamp sunset;
}
