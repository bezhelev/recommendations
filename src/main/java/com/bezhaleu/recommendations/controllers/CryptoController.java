package com.bezhaleu.recommendations.controllers;

import com.bezhaleu.recommendations.services.CryptoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class CryptoController {

    @Value("#{'${cryptos.names}'.split(',')}")
    private List<String> cryptoNames;

    private final CryptoService cryptoService;

    public CryptoController(CryptoService cryptoService) {
        this.cryptoService = cryptoService;
    }

    @GetMapping("/")
    public ResponseEntity<List<String>> getCryptoNamesDescByNormalizeRange() {
        return ResponseEntity.ok(cryptoService.getCryptoNamesDescByNormalizeRange());
    }

    @GetMapping("/{date}")
    public ResponseEntity<String> getCryptoNameWithHighestNormalizeRangeByDate(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        return ResponseEntity.ok(cryptoService.getCryptoNameWithHighestNormalizeRangeByDate(date));
    }

    @GetMapping("/{cryptoName}/critical")
    public ResponseEntity<List<Double>> getCriticalCryptoValuesByCryptoName(@PathVariable String cryptoName) {
        if (!cryptoNames.contains(cryptoName)) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }

        return ResponseEntity.ok(cryptoService.getCriticalCryptoValuesByCryptoName(cryptoName));
    }

}
