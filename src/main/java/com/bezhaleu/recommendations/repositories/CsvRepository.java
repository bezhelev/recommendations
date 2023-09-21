package com.bezhaleu.recommendations.repositories;

import com.bezhaleu.recommendations.models.CryptoSource;
import com.bezhaleu.recommendations.readers.CsvReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CsvRepository {
    private final static String FILE_EXTENSION = "_values.csv";
    private final static String FOLDER_PATH = "prices\\";

    @Value("#{'${cryptos.names}'.split(',')}")
    private List<String> cryptoNames;

    private final CsvReader csvReader;

    private final Map<String, CryptoSource> cryptos = new HashMap<>();

    public CsvRepository(CsvReader csvReader) {
        this.csvReader = csvReader;
    }

    public void fillCryptos() {
        cryptoNames.forEach(
                name ->
                        cryptos.put(
                                name,
                                new CryptoSource(this.csvReader.readCryptosByPath(buildFilenameWithPath(name)))
                        )
        );
    }

    public CryptoSource getCryptosByName(String cryptoName) {
        return cryptos.get(cryptoName);
    }

    public Map<String, CryptoSource> getCryptos() {
        return cryptos;
    }

    private String buildFilenameWithPath(String cryptoName) {
        return FOLDER_PATH + cryptoName + FILE_EXTENSION;
    }

    public void setCryptoNames(List<String> cryptoNames) {
        this.cryptoNames = cryptoNames;
    }
}
