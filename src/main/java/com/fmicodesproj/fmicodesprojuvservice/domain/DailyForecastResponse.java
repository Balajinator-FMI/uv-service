package com.fmicodesproj.fmicodesprojuvservice.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class DailyForecastResponse {
    @JsonProperty("daily")
    private List<InstanceOpenWeatherResponse> daily;
}
