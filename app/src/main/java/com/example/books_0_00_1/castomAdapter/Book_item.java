package com.example.books_0_00_1.castomAdapter;

public class Book_item {

    private String name, author, genr, description;
    private Integer year, id;



    public Book_item(String _name, String _author, String _genr, String _description, Integer _id, Integer _year){
        this.name = _name;
        this.author = _author;
        this.genr = _genr;
        this.description = _description;
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

    public Integer getId() {
        return id;
    }

    public Integer getYear() {
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

    public void setId(Integer id) {
        this.id = id;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
