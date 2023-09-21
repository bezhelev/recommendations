package com.bezhaleu.recommendations.services;

import com.bezhaleu.recommendations.models.Crypto;
import com.bezhaleu.recommendations.models.CryptoSource;
import com.bezhaleu.recommendations.repositories.CsvRepository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
class CryptoServiceTest {

    @Mock
    private CsvRepository csvRepository;

    @InjectMocks
    private CryptoService cryptoService;

    @Test
    void testGetCryptoNamesDescByNormalizeRange() {
        Map<String, CryptoSource> cryptos = getCryptoSourceMap();

        when(csvRepository.getCryptos()).thenReturn(cryptos);

        List<String> cryptoNamesDescByNormalizeRange = cryptoService.getCryptoNamesDescByNormalizeRange();

        verify(csvRepository, times(1)).getCryptos();
        assertEquals(2, cryptoNamesDescByNormalizeRange.size());
        assertEquals("AMC", cryptoNamesDescByNormalizeRange.get(0));
        assertEquals("BTC", cryptoNamesDescByNormalizeRange.get(1));
    }

    @Test
    void testGetCryptoNameWithHighestNormalizeRangeByDate() {
        Map<String, CryptoSource> cryptos = getCryptoSourceMap();
        Crypto emptyCrypto = new Crypto(new Date(), "test", 0D);
        cryptos.put("test",new CryptoSource(List.of(emptyCrypto)));

        when(csvRepository.getCryptos()).thenReturn(cryptos);

        String cryptoNameWithHighestNormalizeRangeByDate = cryptoService.getCryptoNameWithHighestNormalizeRangeByDate(new Date(1641546000000L));

        verify(csvRepository, times(1)).getCryptos();
        assertEquals("AMC", cryptoNameWithHighestNormalizeRangeByDate);
    }

    @Test
    void testGetCriticalCryptoValuesByCryptoName() {
        CryptoSource cryptoSourceBTC = getBtcCryptoSource();

        when(csvRepository.getCryptosByName(anyString())).thenReturn(cryptoSourceBTC);

        List<Double> btc = cryptoService.getCriticalCryptoValuesByCryptoName("BTC");

        verify(csvRepository, times(1)).getCryptosByName("BTC");
        assertEquals(4, btc.size());
    }

    private static Map<String, CryptoSource> getCryptoSourceMap() {
        Map<String, CryptoSource> cryptos = new HashMap<>();
        CryptoSource cryptoSourceBTC = getBtcCryptoSource();
        cryptos.put("BTC", cryptoSourceBTC);

        Crypto cryptoAMCMax = new Crypto(new Date(1641546000000L), "AMC", 129.44D);
        Crypto cryptoAMCMin = new Crypto(new Date(1641546000000L), "AMC", 107.11D);
        CryptoSource cryptoSourceAMC = new CryptoSource(List.of(cryptoAMCMax,cryptoAMCMin));
        cryptos.put("AMC", cryptoSourceAMC);
        return cryptos;
    }

    private static CryptoSource getBtcCryptoSource() {
        Crypto cryptoBTCMax = new Crypto(new Date(1641546000000L), "BTC", 123.44D);
        Crypto cryptoBTCMin = new Crypto(new Date(1641546000000L), "BTC", 111.11D);
        return new CryptoSource(List.of(cryptoBTCMin,cryptoBTCMax));
    }
}