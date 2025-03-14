package com.baladjinator.uvservice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import org.springframework.cache.annotation.Cacheable;
import java.util.Map;

@RestController("/uv")
class UvIndexController {

    @Value("${openweathermap.api.url}")
    private String apiUrl;

    @Value("${openweathermap.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public UvIndexController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Cacheable(value = "uvIndex", key = "#city + '-current'", unless = "#result == null")
    @GetMapping("/current")
    public String getCurrentUvIndex(@RequestParam Double lat, @RequestParam Double lon) {
        var url = String.format("%s?lat=%f&lon=%f&exclude=minutely,hourly,daily,alerts&appid=%s", apiUrl, lat, lon, apiKey);
        var response = restTemplate.getForObject(url, Map.class);
        return response != null ? response.get("current").toString() : "UV data not found";
    }

    @Cacheable(value = "uvIndex", key = "#city + '-forecast'", unless = "#result == null")
    @GetMapping("/forecast")
    public String getForecastUvIndex(@RequestParam Double lat, @RequestParam Double lon) {
        var url = String.format("%s?lat=%f&lon=%f&exclude=current,minutely,hourly,alerts&appid=%s", apiUrl, lat, lon, apiKey);
        var response = restTemplate.getForObject(url, Map.class);
        return response != null ? response.get("daily").toString() : "UV forecast data not found";
    }
}



