package com.exchangeoffice.exchange.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import java.util.Date;

public class ExchangeRatesSearch {

    private String fromCurrency;
    private String toCurrency;
    private Double exchangeRate;
    private Double fromAmount;
    private Double toAmount;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date exchangeDate;

    @Override
    public String toString() {
        return "ExchangeRatesSearch{" +
                "fromCurrency='" + fromCurrency + '\'' +
                ", toCurrency='" + toCurrency + '\'' +
                ", exchangeRate=" + exchangeRate +
                ", fromAmount=" + fromAmount +
                ", toAmount=" + toAmount +
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

    public Double getFromAmount() {
        return fromAmount;
    }

    public void setFromAmount(Double fromAmount) {
        this.fromAmount = fromAmount;
    }

    public Double getToAmount() {
        return toAmount;
    }

    public void setToAmount(Double toAmount) {
        this.toAmount = toAmount;
    }

    public Date getExchangeDate() {
        return exchangeDate;
    }

    public void setExchangeDate(Date exchangeDate) {
        this.exchangeDate = exchangeDate;
    }
}
