package com.fmicodesproj.fmicodesprojuvservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class UvIdxTimeData {
    private Timestamp date;
    private Double uvIndex;
}
