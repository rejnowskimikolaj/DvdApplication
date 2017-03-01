package com.example.rent.dvdapplication.model;

/**
 * Created by RENT on 2017-02-14.
 */

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable (tableName ="clients")
public class ClientEntity {

    @DatabaseField
    private String firstName;

    @DatabaseField
    private String lastName;

    @DatabaseField
    private String dateOfBirth;

    @DatabaseField(generatedId = true)
    private int id;

    public ClientEntity(String firstName, String lastName, String dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }

    public ClientEntity() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return firstName+" "+lastName;
    }
}
