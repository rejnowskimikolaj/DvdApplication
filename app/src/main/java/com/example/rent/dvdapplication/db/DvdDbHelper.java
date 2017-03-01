package com.example.rent.dvdapplication.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.rent.dvdapplication.model.ClientDvdEntity;
import com.example.rent.dvdapplication.model.ClientEntity;
import com.example.rent.dvdapplication.model.DvdEntity;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by RENT on 2017-02-13.
 */

public class DvdDbHelper extends OrmLiteSqliteOpenHelper {

    private static final int DB_VERSION = 7;
    private static final String DB_NAME = "dvd_db.db";
    private Dao<DvdEntity,Long> dvdDao;
    private Dao<ClientEntity,Long> clientDao;
    private Dao<ClientDvdEntity,Long> clientDvdDao;




    private void addStartDataToDb(){

        DvdEntity d1 = new DvdEntity("Shawshank Redemption",1994,"drama","https://images-na.ssl-images-amazon.com/images/M/MV5BODU4MjU4NjIwNl5BMl5BanBnXkFtZTgwMDU2MjEyMDE@._V1_SY1000_CR0,0,672,1000_AL_.jpg",1,"bvfhjks bla bla vgfjsljfkdljsnlkjfd bla");
        DvdEntity d2 = new DvdEntity("Bladerunner",1982,"Sci-fi","http://film.org.pl/wp-content/uploads/2013/11/01090935.jpeg",1,"bvfhjks bla bla vgfjsljfkdljsnlkjfd bla");
        DvdEntity d3 = new DvdEntity("A Clockwork Orange",1971,"Sci-fi","https://i.guim.co.uk/img/media/ac707ddab36726ac4a185b5d64a8b7df867db9b5/0_0_1530_2025/master/1530.jpg?w=300&q=55&auto=format&usm=12&fit=max&s=2151f843879a76918c5488e1a1cd0493",3,"bvfhjks bla bla vgfjsljfkdljsnlkjfd bla");

        ClientEntity c1 = new ClientEntity("Jan","Kowalski","14-02-1998");
        ClientEntity c2 = new ClientEntity("Mikołaj","Rejnowski","09-02-1993");
        ClientEntity c3 = new ClientEntity("Ave","Satan","06-06-1666");
        ClientEntity c4 = new ClientEntity("Gabe","Steam","01-01-1967");



        try {
            getDvdEntityDao();
            dvdDao.create(d1);
            dvdDao.create(d2);
            dvdDao.create(d3);

            getClientEntityDao();
            clientDao.create(c1);
            clientDao.create(c2);
            clientDao.create(c3);
            clientDao.create(c4);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public DvdDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {

        try {
            TableUtils.createTable(connectionSource,DvdEntity.class);
            TableUtils.createTable(connectionSource, ClientEntity.class);
            TableUtils.createTable(connectionSource,ClientDvdEntity.class);

            addStartDataToDb();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

        try {
            TableUtils.dropTable(connectionSource,DvdEntity.class,true);
            TableUtils.dropTable(connectionSource,ClientEntity.class,true);
            TableUtils.dropTable(connectionSource,ClientDvdEntity.class,true);
            onCreate(database);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Dao<DvdEntity,Long> getDvdEntityDao(){
        if (dvdDao==null){
            try {
                dvdDao =getDao(DvdEntity.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return dvdDao;
    }

    public Dao<ClientEntity,Long> getClientEntityDao(){
        if (clientDao==null){
            try {
                clientDao =getDao(ClientEntity.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return clientDao;
    }

    public Dao<ClientDvdEntity,Long> getClientDvdEntityDao(){
        if (clientDvdDao==null){
            try {
                clientDvdDao =getDao(ClientDvdEntity.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return clientDvdDao;
    }
}
