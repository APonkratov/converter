package org.github.aponkratov.converter;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Currency;

public class CurrencyConverter {

    private Double amount;
    private Double result;
    private String from;
    private String to;
    private CurrencyService currencyService;

    private static CurrencyConverter instance = new CurrencyConverter();

    private static final String API_URL = "https://api.exchangeratesapi.io/";

    private CurrencyConverter() {

    }

    public void setProperties(Double amount, Currency from, Currency to) {
        this.amount = amount;
        this.from = from.getCurrencyCode();
        this.to = to.getCurrencyCode();
    }

    public static CurrencyConverter getInstance() {
        return instance;
    }

    public Double Convert() throws IOException {

        if(currencyService != null) {
            return currencyService.getRateByCode(this.to);
        }

        String urlSb = API_URL +
                "latest?base=" +
                this.from;
        URL url = new URL(urlSb);

        try {
            InputStream inputStream = url.openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String jsonText = readAll(rd);
            Gson gson = new Gson();
            currencyService = gson.fromJson(jsonText, CurrencyService.class);
            return currencyService.getRateByCode(this.to) * this.amount;
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
            return 0.0;
        }
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            stringBuilder.append((char) cp);
        }
        return stringBuilder.toString();
    }

    public Double getResult() {
        return result;
    }
}