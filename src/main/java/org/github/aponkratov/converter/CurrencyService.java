package org.github.aponkratov.converter;

import java.util.Date;
import java.util.HashMap;

public class CurrencyService {

    private HashMap<String, Double> rates;
    private String base;
    private Date date;

    public CurrencyService(HashMap<String, Double> rates, String base, Date date) {
        this.rates = rates;
        this.base = base;
        this.date = date;
    }

    public HashMap<String, Double> getRates() {
        return rates;
    }

    public Double getRateByCode(String currencyCode) {
        return getRates().get(currencyCode);
    }
}
