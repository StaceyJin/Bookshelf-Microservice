package com.jin.api.core.accounting;

public class Accounting {

    private int bookId;
    private int accountId;
    private double listedPrice;
    private double paidPrice;
    private double savings;
    private String method;
    private String serviceAddress;

    public Accounting() {
        this.bookId = 0;
        this.accountId = 0;
        this.listedPrice = 0;
        this.paidPrice = 0;
        this.savings = 0;
        this.method = null;
        this.serviceAddress = null;
    }

    public Accounting(int bookId, int accountId, double listedPrice, double paidPrice, double savings, String method, String serviceAddress) {
        this.bookId = bookId;
        this.accountId = accountId;
        this.listedPrice = listedPrice;
        this.paidPrice = paidPrice;
        this.savings = savings;
        this.method = method;
        this.serviceAddress = serviceAddress;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public double getListedPrice() {
        return listedPrice;
    }

    public void setListedPrice(double listedPrice) {
        this.listedPrice = listedPrice;
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

    public String getServiceAddress() {
        return serviceAddress;
    }

    public void setServiceAddress(String serviceAddress) {
        this.serviceAddress = serviceAddress;
    }

}
