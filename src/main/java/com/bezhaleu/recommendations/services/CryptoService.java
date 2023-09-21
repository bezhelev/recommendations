package com.bezhaleu.recommendations.services;

import com.bezhaleu.recommendations.models.Crypto;
import com.bezhaleu.recommendations.models.CryptoRangeDto;
import com.bezhaleu.recommendations.models.CryptoSource;
import com.bezhaleu.recommendations.repositories.CsvRepository;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CryptoService {

    private final CsvRepository csvRepository;

    public CryptoService(CsvRepository csvRepository) {
        this.csvRepository = csvRepository;
    }

    public List<String> getCryptoNamesDescByNormalizeRange() {
        Map<String, CryptoSource> cryptos = csvRepository.getCryptos();

        return cryptos.values().stream().sorted(Comparator.comparingDouble(o -> -1 * (o.getMax().getPrice() - o.getMin().getPrice()) / o.getMin().getPrice()))
                .map(cryptoSource -> cryptoSource.getMin().getSymbol()).collect(Collectors.toList());
    }

    public String getCryptoNameWithHighestNormalizeRangeByDate(Date date) {
        Map<String, CryptoSource> cryptos = csvRepository.getCryptos();

        return cryptos.values().stream()
                .map(cryptoSource -> {
                    List<Crypto> collect = getCryptosByDate(date, cryptoSource);
                    if (collect.size() == 0) {
                        return new CryptoRangeDto(cryptoSource.getMin().getSymbol(), 0D);
                    }

                    Crypto min = collect.get(0);
                    Crypto max = collect.get(collect.size() - 1);

                    return new CryptoRangeDto(max.getSymbol(), (max.getPrice() - min.getPrice()) / min.getPrice());
                })
                .max(Comparator.comparingDouble(CryptoRangeDto::getNormalizeRange))
                .map(CryptoRangeDto::getSymbol)
                .orElseThrow();
    }

    public List<Double> getCriticalCryptoValuesByCryptoName(String cryptoName) {
        CryptoSource cryptoSource = csvRepository.getCryptosByName(cryptoName);
        return List.of(
                cryptoSource.getMin().getPrice(),
                cryptoSource.getMax().getPrice(),
                cryptoSource.getNewest().getPrice(),
                cryptoSource.getOldest().getPrice()
        );
    }

    private List<Crypto> getCryptosByDate(Date date, CryptoSource cryptoSource) {
        return cryptoSource.getCryptos()
                .stream()
                .filter(
                        crypto -> {
                            Calendar calendar = new GregorianCalendar();
                            calendar.setTime(crypto.getTimestamp());
                            int unknownDay = calendar.get(Calendar.DAY_OF_YEAR);
                            calendar.setTime(date);
                            int necessaryDay = calendar.get(Calendar.DAY_OF_YEAR);

                            return unknownDay == necessaryDay;
                        })
                .sorted(Comparator.comparingDouble(Crypto::getPrice))
                .collect(Collectors.toList());
    }

}
