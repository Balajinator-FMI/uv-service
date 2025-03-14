package com.baladjinator.uvservice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import org.springframework.cache.annotation.Cacheable;
import java.util.Map;

@RestController
class UvIndexController {

    @Value("${openweathermap.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public UvIndexController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Cacheable(value = "uvIndex", key = "#city + '-current'", unless = "#result == null")
    @GetMapping("/uv/current/{city}")
    public String getCurrentUvIndex(@PathVariable String city) {
        var coordinates = getCoordinates(city);
        var url = String.format("${openweathermap.api.url}?lat=%f&lon=%f&exclude=minutely,hourly,daily,alerts&appid=%s", coordinates.get("lat"), coordinates.get("lon"), apiKey);
        var response = restTemplate.getForObject(url, Map.class);
        return response != null ? response.get("current").toString() : "UV data not found";
    }

    @Cacheable(value = "uvIndex", key = "#city + '-forecast'", unless = "#result == null")
    @GetMapping("/uv/forecast/{city}")
    public String getForecastUvIndex(@PathVariable String city) {
        var coordinates = getCoordinates(city);
        var url = String.format("${openweathermap.api.url}?lat=%f&lon=%f&exclude=current,minutely,hourly,alerts&appid=%s", coordinates.get("lat"), coordinates.get("lon"), apiKey);
        var response = restTemplate.getForObject(url, Map.class);
        return response != null ? response.get("daily").toString() : "UV forecast data not found";
    }

    private Map<String, Double> getCoordinates(String city) {
        // Placeholder for Google Maps Geolocation API integration
        // Example response: { "lat": 25.276987, "lon": 55.296249 } for Dubai
        return Map.of("lat", 25.276987, "lon", 55.296249); // Replace with actual API call
    }
}



