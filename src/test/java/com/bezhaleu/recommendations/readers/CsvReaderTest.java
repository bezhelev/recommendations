package com.bezhaleu.recommendations.readers;

import com.bezhaleu.recommendations.models.Crypto;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.List;

class CsvReaderTest {

    private CsvReader csvReader = new CsvReader();

    @Test
    void testReadCryptosByPath() {
        List<Crypto> cryptos = csvReader.readCryptosByPath("prices\\BTC_values.csv");

        assertEquals(100, cryptos.size());
    }


    @Test
    void testReadCryptosByPathFailScenario() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            csvReader.readCryptosByPath("prices\\Bgd_values.csv");
        });

        assertEquals("something went wrong", exception.getMessage());
    }
}