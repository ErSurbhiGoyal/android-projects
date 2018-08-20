package com.suroid.android_sqlite.model;

public class Book {


    private int id;
    private String title;
    private String author;
    private String createdDate;

    public Book(){}

    public Book(String title, String author) {
        super();
        this.title = title;
        this.author = author;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "Book [id=" + id + ", title=" + title + ", author=" + author
                + "]";
    }


}
