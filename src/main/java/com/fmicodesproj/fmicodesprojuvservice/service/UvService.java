package com.fmicodesproj.fmicodesprojuvservice.service;

import com.fmicodesproj.fmicodesprojuvservice.domain.UvDataResponse;

public interface UvService {
    UvDataResponse getUvData(Double lat, Double lon);
}
