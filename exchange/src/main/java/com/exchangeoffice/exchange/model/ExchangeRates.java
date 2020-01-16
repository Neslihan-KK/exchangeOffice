package com.exchangeoffice.exchange.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class ExchangeRates {

    private String fromCurrency;
    private String toCurrency;
    private Double exchangeRate;
    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss")
    private Date exchangeDate;

    @Override
    public String toString() {
        return "ExchangeRates{" +
                "fromCurrency='" + fromCurrency + '\'' +
                ", toCurrency='" + toCurrency + '\'' +
                ", exchangeRate=" + exchangeRate +
                ", exchangeDate=" + exchangeDate +
                '}';
    }

    public String getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(String fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    public void setToCurrency(String toCurrency) {
        this.toCurrency = toCurrency;
    }

    public Double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(Double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public Date getExchangeDate() {
        return exchangeDate;
    }

    public void setExchangeDate(Date exchangeDate) {
        this.exchangeDate = exchangeDate;
    }
}
