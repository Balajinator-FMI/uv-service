package com.baladjinator.uvservice.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.Map;

@RestController
@RequestMapping("/uv")
class UvIndexController {

    @Value("${openweathermap.api.url}")
    private String apiUrl;

    @Value("${openweathermap.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public UvIndexController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/current")
    public String getCurrentUvIndex(@RequestParam Double lat, @RequestParam Double lon) {
        var url = String.format("%s?lat=%f&lon=%f&exclude=minutely,hourly,daily,alerts&appid=%s", apiUrl, lat, lon, apiKey);
        var response = restTemplate.getForObject(url, Map.class);
        return response != null ? response.get("current").toString() : "UV data not found";
    }

    @GetMapping("/previous-days")
    public String getPreviousDaysUvIndex(@RequestParam Double lat, @RequestParam Double lon, @RequestParam Short days) {
        long currentTime = Instant.now().getEpochSecond();
        long pastTime = currentTime - (days * 86400);

        var url = String.format(
                "%s?lat=%f&lon=%f&start=%d&end=%d&exclude=current,minutely,hourly,alerts&appid=%s",
                apiUrl, lat, lon, pastTime, currentTime, apiKey
        );

        var response = restTemplate.getForObject(url, Map.class);
        return response != null ? response.get("daily").toString() : "UV forecast data not found";
    }

    @GetMapping("/forecast")
    public String getForecastUvIndex(@RequestParam Double lat, @RequestParam Double lon) {
        var url = String.format("%s?lat=%f&lon=%f&exclude=current,minutely,hourly,alerts&appid=%s", apiUrl, lat, lon, apiKey);
        var response = restTemplate.getForObject(url, Map.class);
        return response != null ? response.get("daily").toString() : "UV forecast data not found";
    }
}



