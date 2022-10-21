package com.jin.bookshelf.core.portal.datalayer;

import javax.persistence.*;

@Entity
@Table(name = "portals", indexes = { @Index(name = "portals_unique_idx",
unique = true, columnList = "bookId, portalId")})

public class PortalEntity {

    @Id @GeneratedValue
    private int id;

    @Version
    private int version;

    private int bookId;
    private int portalId;
    private String link;
    private String userName;

    public PortalEntity() {
    }

    public PortalEntity(int bookId, int portalId, String link, String userName) {
        this.bookId = bookId;
        this.portalId = portalId;
        this.link = link;
        this.userName = userName;
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
