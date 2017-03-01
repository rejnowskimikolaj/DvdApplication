package com.example.rent.dvdapplication.model;

import com.example.rent.dvdapplication.model.ClientEntity;
import com.example.rent.dvdapplication.model.DvdEntity;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Collection;

/**
 * Created by RENT on 2017-02-14.
 */

@DatabaseTable(tableName = "client_dvd")
public class ClientDvdEntity {

    @DatabaseField(foreign = true,foreignAutoRefresh = true)
    private ClientEntity client;


    @DatabaseField(foreign = true,foreignAutoRefresh = true)
    private DvdEntity dvd;

    @DatabaseField(defaultValue = "0")
    private boolean isReturned;



    public ClientDvdEntity() {
    }

    public ClientDvdEntity(ClientEntity client, DvdEntity dvd) {

        this.client = client;
        this.dvd = dvd;

    }

    public ClientEntity getClient() {
        return client;
    }

    public void setClient(ClientEntity client) {
        this.client = client;
    }

    public DvdEntity getDvd() {
        return dvd;
    }

    public void setDvd(DvdEntity dvd) {
        this.dvd = dvd;
    }
}
