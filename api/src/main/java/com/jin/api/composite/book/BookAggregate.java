package com.jin.api.composite.book;

import java.util.List;

public class BookAggregate {

    private int bookId;
    private String title;
    private String author;
    private String artist;
    private String subject;
    private String publisher;
    private String type;
    private String app;
    private String status;
    private List<AccountingSummary> accountings;
    private List<PortalSummary> portals;
    private ServiceAddresses serviceAddress;

    public BookAggregate() {
        this.bookId = 0;
        this.title = null;
        this.author = null;
        this.artist = null;
        this.subject = null;
        this.publisher = null;
        this.type = null;
        this.app = null;
        this.status = null;
        this.accountings = null;
        this.portals = null;
        this.serviceAddress = null;
    }

    public BookAggregate(int bookId, String title, String author, String artist, String subject, String publisher,
                         String type, String app, String status, List<AccountingSummary> accountings,
                         List<PortalSummary> portals, ServiceAddresses serviceAddress) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.artist = artist;
        this.subject = subject;
        this.publisher = publisher;
        this.type = type;
        this.app = app;
        this.status = status;
        this.accountings = accountings;
        this.portals = portals;
        this.serviceAddress = serviceAddress;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<AccountingSummary> getAccountings() {
        return accountings;
    }

    public void setAccountings(List<AccountingSummary> accountings) {
        this.accountings = accountings;
    }

    public List<PortalSummary> getPortals() {
        return portals;
    }

    public void setPortals(List<PortalSummary> portals) {
        this.portals = portals;
    }

    public ServiceAddresses getServiceAddress() {
        return serviceAddress;
    }

    public void setServiceAddress(ServiceAddresses serviceAddress) {
        this.serviceAddress = serviceAddress;
    }
}
