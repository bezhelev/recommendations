package com.bezhaleu.recommendations.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class Crypto {
    private Date timestamp;
    //todo redo to enum
    private String symbol;
    private Double price;
}
