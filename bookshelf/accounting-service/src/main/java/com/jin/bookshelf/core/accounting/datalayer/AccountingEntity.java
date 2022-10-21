package com.jin.bookshelf.core.accounting.datalayer;

import javax.persistence.*;

@Entity
@Table(name = "accounting", indexes = { @Index(name = "accounting_unique_idx",
unique = true, columnList = "bookId,accountId")})

public class AccountingEntity {

    @Id @GeneratedValue
    private int id;

    @Version
    private int version;

    private int bookId;
    private int accountId;
    private double listedPrice;
    private  double paidPrice;
    private double savings;
    private String method;

    public AccountingEntity() {
    }

    public AccountingEntity(int bookId, int accountId, double listedPrice, double paidPrice, double savings, String method) {
        this.bookId = bookId;
        this.accountId = accountId;
        this.listedPrice = listedPrice;
        this.paidPrice = paidPrice;
        this.savings = savings;
        this.method = method;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
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
}
