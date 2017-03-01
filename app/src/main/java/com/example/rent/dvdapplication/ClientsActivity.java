package com.example.rent.dvdapplication;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.rent.dvdapplication.db.DvdDbHelper;
import com.example.rent.dvdapplication.model.ClientDvdEntity;
import com.example.rent.dvdapplication.model.ClientEntity;
import com.example.rent.dvdapplication.model.DvdEntity;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientsActivity extends AppCompatActivity  implements DvdAdapter.OnDvdItemClickedListener{

    Spinner clientSpinner;
    Button getDataButton;
    ClientEntity clientEntity;
    Dao<ClientEntity,Long> clientDao;
    private Dao<ClientDvdEntity, Long> clientDvdDao;
    private List<DvdEntity> dvdList = new ArrayList<>();

    DvdDbHelper dbHelper = new DvdDbHelper(this);
    TextView nameTV;
    TextView dateOfBirtTV;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        nameTV = (TextView) findViewById(R.id.activity_client_name);
        dateOfBirtTV = (TextView) findViewById(R.id.activity_client_dateOfBirth);

        clientSpinner = (Spinner) findViewById(R.id.activity_client_spinner);
        getDataButton = (Button) findViewById(R.id.activity_client_getUserDataButton);
        getDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onGetDataButtonClicked();
            }
        });

        setSpinner();

        clientDvdDao = dbHelper.getClientDvdEntityDao();

    }

    private void onGetDataButtonClicked() {
        dvdList.clear();
        clientEntity=(ClientEntity) clientSpinner.getSelectedItem();
        loadViews();
    }

    private void loadViews(){
        if(clientEntity!=null){
            nameTV.setText(clientEntity.toString());
            dateOfBirtTV.setText(clientEntity.getDateOfBirth());
        }

        getClientsDvds();
        setAdapter(dvdList);
    }

    private void setSpinner() {
        clientDao = dbHelper.getClientEntityDao();
        List<ClientEntity> clientList = null;
        try {
            clientList =  clientDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        ArrayAdapter<ClientEntity> clientAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, clientList);
        clientAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        clientSpinner.setAdapter(clientAdapter);
    }

    public void getClientsDvds(){
        try {

            List<ClientDvdEntity> list = clientDvdDao.queryBuilder().where()
                    .eq("client_id",clientEntity.getId())
                    .and()
                    .eq("isReturned",false)
                    .query();
            for(ClientDvdEntity cde:list){
                dvdList.add(cde.getDvd());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setAdapter(List<DvdEntity> list){


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.activity_client_recyclerView);
        recyclerView.setHasFixedSize(false);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        DvdAdapter adapter = new DvdAdapter(list,this,this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onDvdItemClicker(final DvdEntity entity) {

        AlertDialog dialog=null;
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setTitle("Returned?");
        ab.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                onReturnedButtonClicked(entity);
            }
        });

        dialog=ab.create();
        dialog.show();
    }

    private void onReturnedButtonClicked(DvdEntity dvdEntity) {
        try {
           // clientDvdDao.deleteBuilder().where().eq("dvd_id",dvdEntity.getId());
            DeleteBuilder<ClientDvdEntity,Long> builder = clientDvdDao.deleteBuilder();
            builder.where().eq("dvd_id",dvdEntity.getId());
            builder.delete();
            onGetDataButtonClicked();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
