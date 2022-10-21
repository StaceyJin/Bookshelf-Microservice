package com.jin.api.composite.book;

public class PortalSummary {

    private int portalId;
    private String link;
    private String userName;

    public PortalSummary() {
        this.portalId = 0;
        this.link = null;
        this.userName = null;
    }

    public PortalSummary(int portalId, String link, String userName) {
        this.portalId = portalId;
        this.link = link;
        this.userName = userName;
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
}
