package com.fmicodesproj.fmicodesprojuvservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeelsLike {
    private double day;
    private double night;
    private double eve;
    private double morn;
    // Getters and Setters
}
