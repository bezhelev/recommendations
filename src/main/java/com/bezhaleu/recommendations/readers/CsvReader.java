package com.bezhaleu.recommendations.readers;

import com.bezhaleu.recommendations.models.Crypto;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CsvReader {

    private final static String HEADER = "timestamp,symbol,price";

    public List<Crypto> readCryptosByPath(String path) {

        try {
            List<Crypto> cryptos = new ArrayList<>();

            CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                    .setHeader(HEADER)
                    .setSkipHeaderRecord(true)
                    .build();

            Iterable<CSVRecord> records = csvFormat.parse(new FileReader(new ClassPathResource(path).getFile()));

            for (CSVRecord record : records) {
                Date columnOne = new Date(Long.parseLong(record.get(0)));
                String columnTwo = record.get(1);
                Double columnThree = Double.parseDouble(record.get(2));
                cryptos.add(new Crypto(columnOne, columnTwo, columnThree));
            }

            return cryptos;
        } catch (IOException e) {
            //todo add logging
            throw new RuntimeException("something went wrong");
        }
    }
}
