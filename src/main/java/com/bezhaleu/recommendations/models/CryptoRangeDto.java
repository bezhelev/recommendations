package com.bezhaleu.recommendations.models;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CryptoRangeDto {
    private String symbol;
    private Double normalizeRange;
}
