package com.fmicodesproj.fmicodesprojuvservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UvDataResponse {
    private List<UvIdxTimeData> uvIdxForecast;
    private List<UvIdxTimeData> uvIdxHistory;
    private CurrentUvData currentUvData;
}
