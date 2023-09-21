package com.bezhaleu.recommendations.models;

import lombok.Data;

import java.util.List;

@Data
public class CryptoSource {
    private final List<Crypto> cryptos;
    private Crypto min;
    private Crypto max;
    private Crypto newest;
    private Crypto oldest;

    public CryptoSource(List<Crypto> cryptos) {
        this.cryptos = cryptos;
        fillValues(cryptos);
    }

    private void fillValues(List<Crypto> cryptosByName) {
        if (cryptosByName == null || cryptosByName.size() == 0) {
            return;
        }
        min = cryptosByName.get(0);
        max = cryptosByName.get(0);
        newest = cryptosByName.get(0);
        oldest = cryptosByName.get(0);

        for (Crypto crypto : cryptosByName) {
            if (crypto.getPrice() > max.getPrice()) {
                max = crypto;
            }

            if (crypto.getPrice() < min.getPrice()) {
                min = crypto;
            }

            if (crypto.getTimestamp().before(oldest.getTimestamp())) {
                oldest = crypto;
            }

            if (crypto.getTimestamp().after(newest.getTimestamp())) {
                newest = crypto;
            }
        }
    }

}
