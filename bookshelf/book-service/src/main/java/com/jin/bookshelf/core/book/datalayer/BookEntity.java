package com.jin.bookshelf.core.book.datalayer;


import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="books")
public class BookEntity {

    @Id
    private String id;

    @Version
    private Integer version;

    @Indexed(unique=true)
    private int bookId; /*business key*/

    private String title;
    private String author;
    private String artist;
    private String subject;
    private String publisher;
    private String type;
    private String app;
    private String status;

    public BookEntity() {
    }

    public BookEntity(int bookId, String title, String author, String artist, String subject, String publisher, String type, String app, String status) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.artist = artist;
        this.subject = subject;
        this.publisher = publisher;
        this.type = type;
        this.app = app;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
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
}
