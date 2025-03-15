package com.fmicodesproj.fmicodesprojuvservice.service.impl;

import com.fmicodesproj.fmicodesprojuvservice.domain.*;
import com.fmicodesproj.fmicodesprojuvservice.service.UvService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UvServiceImpl implements UvService {
    private final String apiUrl;

    private final String apiKey;

    private final long DAYS = 7;

    public UvServiceImpl(@Value("${openweathermap.api.url}") String apiUrl, @Value("${openweathermap.api.key}") String apiKey) {
        this.apiUrl = apiUrl;
        this.apiKey = apiKey;
    }

    @Override
    public UvDataResponse getUvData(Double lat, Double lon) {
        return UvDataResponse.builder()
                .currentUvData(getCurrentUvData(lat, lon))
                .uvIdxForecast(getUvIdxForecast(lat, lon))
                .uvIdxHistory(getUvIdxHistory(lat, lon))
                .build();
    }

    private List<UvIdxTimeData> getUvIdxForecast(Double lat, Double lon) {
        long currentTime = Instant.now().getEpochSecond();
        long futureTime = currentTime + (DAYS * 86400);
        String url = String.format(
                "%s?lat=%f&lon=%f&start=%d&end=%d&exclude=current,minutely,hourly,alerts&appid=%s",
                apiUrl, lat, lon, currentTime, futureTime, apiKey
        );

        return getUvIdxTimeData(url);
    }

    private List<UvIdxTimeData> getUvIdxHistory(Double lat, Double lon) {
        long pastTime = Instant.now().getEpochSecond() - (DAYS * 86400);
        long currentTime = Instant.now().getEpochSecond();
        String url = String.format(
                "%s?lat=%f&lon=%f&start=%d&end=%d&exclude=current,minutely,hourly,alerts,summary&appid=%s",
                apiUrl, lat, lon, pastTime, currentTime, apiKey
        );

        return getUvIdxTimeData(url);
    }

    private List<UvIdxTimeData> getUvIdxTimeData(String url) {
        RestTemplate restTemplate = new RestTemplate();
        InstanceOpenWeatherResponse[] weatherDataArray = restTemplate.getForObject(url, InstanceOpenWeatherResponse[].class);

        List<UvIdxTimeData> uvDataList = new ArrayList<>();
        if (weatherDataArray != null) {
            for (InstanceOpenWeatherResponse data : weatherDataArray) {
                UvIdxTimeData uvData = UvIdxTimeData.builder()
                        .date(Timestamp.from(Instant.ofEpochSecond(data.getDt())))
                        .uvIndex(data.getUvi())
                        .build();

                uvDataList.add(uvData);
            }
        }
        return uvDataList;

    }

    private CurrentUvData getCurrentUvData(Double lat, Double lon) {
        var url = String.format("%s?lat=%f&lon=%f&exclude=minutely,hourly,alerts,daily&appid=%s", apiUrl, lat, lon, apiKey);

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        ResponseEntity<CurrentOpenWeatherResponse> result =
                restTemplate.exchange(url, HttpMethod.GET, entity, CurrentOpenWeatherResponse.class);

        CurrentOpenWeatherResponse response = result.getBody();

        if (response != null) {
            return CurrentUvData.builder()
                    .uvIndex(response.getCurrent().getUvi())
                    .sunset(Timestamp.from(Instant.ofEpochSecond(response.getCurrent().getSunset())))
                    .sunrise(Timestamp.from(Instant.ofEpochSecond(response.getCurrent().getSunrise())))
                    .temperatureC(response.getCurrent().getTemp() - 273.15)
                    .build();
        }

        return null;
    }
}
