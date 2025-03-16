package com.fmicodesproj.fmicodesprojuvservice.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoricalOpenWeatherResponse {
    private double lat;
    private double lon;
    private String timezone;
    @JsonProperty("timezone_offset")
    private int timezoneOffset;
    private List<WeatherData> data;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class WeatherData {
        private long dt;
        private long sunrise;
        private long sunset;
        private double temp;
        @JsonProperty("feels_like")
        private double feelsLike;
        private int pressure;
        private int humidity;
        @JsonProperty("dew_point")
        private double dewPoint;
        private double uvi;
        private int clouds;
        private int visibility;
        @JsonProperty("wind_speed")
        private double windSpeed;
        @JsonProperty("wind_deg")
        private int windDeg;
        private List<Weather> weather;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Weather {
        private int id;
        private String main;
        private String description;
        private String icon;
    }
}