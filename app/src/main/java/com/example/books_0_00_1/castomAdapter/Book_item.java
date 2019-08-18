package com.example.books_0_00_1.castomAdapter;

public class Book_item {

    private String name, author, genr, description;
    private int year, id;
    private boolean like;



    public Book_item(String _name, String _author, String _genr, String _description, boolean _like, int _id, int _year){
        this.name = _name;
        this.author = _author;
        this.genr = _genr;
        this.description = _description;
        this.like = _like;
        this.id = _id;
        this.year = _year;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenr() {
        return genr;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getLike() {
        return like;
    }

    public int getId() {
        return id;
    }

    public int getYear() {
        return year;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setGenr(String genr) {
        this.genr = genr;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLike(Boolean like) {
        this.like = like;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
