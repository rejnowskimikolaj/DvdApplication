package com.example.rent.dvdapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.rent.dvdapplication.db.DvdDbHelper;
import com.example.rent.dvdapplication.model.DvdEntity;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DvdAdapter.OnDvdItemClickedListener {


    private Dao<DvdEntity, Long> dao;
    Button addButton;
    Button goToClientsButton;
    public final static String DVD_ID="dvdId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addButton = (Button) findViewById(R.id.activity_main_addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddButtonClicked();
            }
        });

        goToClientsButton = (Button) findViewById(R.id.activity_main_goToUsersButton);
        goToClientsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onGoToClientsButtonClicked();
            }
        });

        loadDbIntoViewholder();
    }

    private void onGoToClientsButtonClicked() {

        Intent i = new Intent(this,ClientsActivity.class);
        startActivity(i);
    }

    private void onAddButtonClicked() {

        AlertDialog dialog=null;
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setTitle("Create new");

        final List<EditText> editTexts = new LinkedList<>();
        final EditText titleEt = new EditText(this);
        titleEt.setHint("title");
        editTexts.add(titleEt);

        final EditText genreEt = new EditText(this);
        genreEt.setHint("genre");
        editTexts.add(genreEt);

        final EditText descriptionEt = new EditText(this);
        descriptionEt.setHint("description");
        editTexts.add(descriptionEt);

        final EditText yearEt = new EditText(this);
        yearEt.setHint("year");
        editTexts.add(yearEt);

        final EditText urlET = new EditText(this);
        urlET.setHint("photoUrl: if no url, leave it empty");
        editTexts.add(urlET);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);


        for(EditText et:editTexts){
            layout.addView(et);
        }

        ab.setView(layout);
        ab.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                String title = editTexts.get(0).getText().toString();
                String genre = editTexts.get(1).getText().toString();
                String description = editTexts.get(2).getText().toString();
                int year = Integer.parseInt(editTexts.get(3).getText().toString());
                String url = editTexts.get(4).getText().toString();
                if(url==null||url.equals("")){
                    url = "http://vignette2.wikia.nocookie.net/ideas/images/e/e4/Movie_night.jpg/revision/latest?cb=20141222232947";
                }

                onDialogAddButtonClicked(new DvdEntity(title,year,genre,url,0,description));

            }
        });

        ab.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        dialog=ab.create();
        dialog.show();
    }

    private void onDialogAddButtonClicked(DvdEntity entity) {
        try {
            dao.create(entity);
            loadDbIntoViewholder();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    private void loadDbIntoViewholder(){
        DvdDbHelper dvdDbHelper = new DvdDbHelper(this);
        dao = dvdDbHelper.getDvdEntityDao();
        try {
            List<DvdEntity> dvds = dao.queryForAll();
            setAdapter(dvds);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDbIntoViewholder();
    }

    @Override
    public void onDvdItemClicker(DvdEntity entity) {

        Intent i  = new Intent(this,DvdDetailActivity.class);
        i.putExtra(DVD_ID,entity.getId());
        startActivity(i);
    }

    public void setAdapter(List<DvdEntity> list){
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.activity_main_recyclerView);
        recyclerView.setHasFixedSize(false);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        DvdAdapter adapter = new DvdAdapter(list,this,this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }
}
