package com.jin.api.composite.book;

public class AccountingSummary {

    private int accountId;
    private double listedPrice;
    private double paidPrice;
    private double savings;
    private String method;

    public AccountingSummary() {
        this.accountId = 0;
        this.listedPrice = 0;
        this.paidPrice = 0;
        this.savings = 0;
        this.method = null;
    }

    public AccountingSummary(int accountId, double listedPrice, double paidPrice, double savings, String method) {
        this.accountId = accountId;
        this.listedPrice = listedPrice;
        this.paidPrice = paidPrice;
        this.savings = savings;
        this.method = method;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public double getPaidPrice() {
        return paidPrice;
    }

    public void setPaidPrice(double paidPrice) {
        this.paidPrice = paidPrice;
    }

    public double getSavings() {
        return savings;
    }

    public void setSavings(double savings) {
        this.savings = savings;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public double getListedPrice() {
        return listedPrice;
    }

    public void setListedPrice(double listedPrice) {
        this.listedPrice = listedPrice;
    }
}
