package com.example.rent.dvdapplication.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by RENT on 2017-02-13.
 */

@DatabaseTable(tableName = "tasks")
public class DvdEntity {

    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField
    private String title;

    @DatabaseField
    private String photoUrl;
    @DatabaseField
    private String genre;

    @DatabaseField
    private int year;

    @DatabaseField
    private int amount;

    @DatabaseField
    private String description;

    public DvdEntity( String title,int year, String genre, String photoUrl,int amount,String description ) {
        this.title = title;
        this.photoUrl = photoUrl;
        this.genre = genre;
        this.year = year;
        this.amount = amount;
        this.description=description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DvdEntity() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
