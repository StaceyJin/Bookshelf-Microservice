package com.jin.api.composite.book;

public class ServiceAddresses {

    private String compositeAddress;
    private String bookAddress;
    private String accountingAddress;
    private String portalAddress;

    public ServiceAddresses() {
        this.compositeAddress = null;
        this.bookAddress = null;
        this.accountingAddress = null;
        this.portalAddress = null;
    }

    public ServiceAddresses(String compositeAddress, String bookAddress, String accountingAddress, String portalAddress) {
        this.compositeAddress = compositeAddress;
        this.bookAddress = bookAddress;
        this.accountingAddress = accountingAddress;
        this.portalAddress = portalAddress;
    }

    public String getCompositeAddress() {
        return compositeAddress;
    }

    public void setCompositeAddress(String compositeAddress) {
        this.compositeAddress = compositeAddress;
    }

    public String getBookAddress() {
        return bookAddress;
    }

    public void setBookAddress(String bookAddress) {
        this.bookAddress = bookAddress;
    }

    public String getAccountingAddress() {
        return accountingAddress;
    }

    public void setAccountingAddress(String accountingAddress) {
        this.accountingAddress = accountingAddress;
    }

    public String getPortalAddress() {
        return portalAddress;
    }

    public void setPortalAddress(String portalAddress) {
        this.portalAddress = portalAddress;
    }
}
