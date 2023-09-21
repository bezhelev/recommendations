package com.bezhaleu.recommendations.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;

@SpringBootTest
class CryptoControllerTest {

    @Autowired
    private CryptoController cryptoController;

    @Test
    void testGetCryptoNamesDescByNormalizeRange() {
        ResponseEntity<List<String>> cryptoNamesDesc = cryptoController.getCryptoNamesDescByNormalizeRange();

        assertEquals(HttpStatus.OK, cryptoNamesDesc.getStatusCode());
        assertNotNull(cryptoNamesDesc.getBody());
        assertEquals(5, cryptoNamesDesc.getBody().size());
    }

    @Test
    void testGetCryptoNameWithHighestNormalizeRangeByDate() {
        ResponseEntity<String> cryptoName = cryptoController.getCryptoNameWithHighestNormalizeRangeByDate(new Date(1641546000000L));

        assertEquals(HttpStatus.OK, cryptoName.getStatusCode());
        assertEquals("DOGE", cryptoName.getBody());
    }

    @Test
    void testGetCriticalCryptoValuesByCryptoName() {
        ResponseEntity<List<Double>> doge = cryptoController.getCriticalCryptoValuesByCryptoName("DOGE");

        assertEquals(HttpStatus.OK, doge.getStatusCode());
        assertNotNull(doge.getBody());
        assertEquals(4, doge.getBody().size());
    }

    @Test
    void testGetCriticalCryptoValuesByCryptoNameFailScenario() {
        ResponseEntity<List<Double>> dogy = cryptoController.getCriticalCryptoValuesByCryptoName("DOGY");

        assertEquals(HttpStatus.NOT_ACCEPTABLE, dogy.getStatusCode());
    }
}