package com.exchangeoffice.exchange.model;

public class CurrencyList {

    private String currency;
    private String currencyLabel;

    @Override
    public String toString() {
        return "CurrencyList{" +
                "currency='" + currency + '\'' +
                ", currencyLabel='" + currencyLabel + '\'' +
                '}';
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrencyLabel() {
        return currencyLabel;
    }

    public void setCurrencyLabel(String currencyLabel) {
        this.currencyLabel = currencyLabel;
    }
}
