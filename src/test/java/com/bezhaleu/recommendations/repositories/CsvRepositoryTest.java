package com.bezhaleu.recommendations.repositories;

import com.bezhaleu.recommendations.models.Crypto;
import com.bezhaleu.recommendations.models.CryptoSource;
import com.bezhaleu.recommendations.readers.CsvReader;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
class CsvRepositoryTest {

    @Mock
    private CsvReader csvReader;

    @InjectMocks
    private CsvRepository csvRepository;

    @BeforeEach
    void setUp() {
        csvRepository.setCryptoNames(List.of("BTC"));
        Crypto crypto = new Crypto(new Date(), "BTC", 0.4444);
        CryptoSource cryptoSource = new CryptoSource(List.of(crypto));

        csvRepository.getCryptos().put("BTC", cryptoSource);
    }

    @Test
    void testFillCryptos() {
        Crypto crypto = new Crypto(new Date(), "BTC", 0.4444);
        when(csvReader.readCryptosByPath(anyString())).thenReturn(List.of(crypto));

        csvRepository.fillCryptos();

        verify(csvReader, times(1)).readCryptosByPath(anyString());
    }

    @Test
    void testGetCryptosByName() {
        CryptoSource cryptosByName = csvRepository.getCryptosByName("BTC");

        assertEquals(1, cryptosByName.getCryptos().size());
    }

    @Test
    void testGetCryptos() {

        Map<String, CryptoSource> cryptos = csvRepository.getCryptos();

        assertEquals(1, cryptos.size());
        assertNotNull(cryptos.get("BTC"));

    }
}