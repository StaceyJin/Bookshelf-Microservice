package com.jin.api.core.portal;

public class Portal {

    private int bookId;
    private int portalId;
    private String link;
    private String userName;
    private String serviceAddress;

    public Portal() {
        this.bookId = 0;
        this.portalId = 0;
        this.link = null;
        this.userName = null;
        this.serviceAddress = null;
    }

    public Portal(int bookId, int portalId, String link, String userName, String serviceAddress) {
        this.bookId = bookId;
        this.portalId = portalId;
        this.link = link;
        this.userName = userName;
        this.serviceAddress = serviceAddress;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getPortalId() {
        return portalId;
    }

    public void setPortalId(int portalId) {
        this.portalId = portalId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getServiceAddress() {
        return serviceAddress;
    }

    public void setServiceAddress(String serviceAddress) {
        this.serviceAddress = serviceAddress;
    }

}
