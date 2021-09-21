package com.example.calenderapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Dao;
import androidx.room.RoomDatabase;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    CalendarView calendarView;
    ImageButton btnedit;
    DaoInterface database;
    RecyclerAdapter adapter;
    SearchView searchView;
    RecyclerView recyclerView;
    List<CalenderData> list;
    String fulldate="";
    ViewModelclss viewmodel;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calendarView = findViewById(R.id.calenderview);
        searchView = findViewById(R.id.searchitem);
        btnedit = findViewById(R.id.addeventbtn);
        database = RoomDataBase.getInstance(this).getDao();

        linearLayout = findViewById(R.id.linearmain);
        Snackbar.make(linearLayout, "long press to delete item", BaseTransientBottomBar.LENGTH_LONG).show();

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        list = new ArrayList<>();


        getSupportActionBar().setSubtitle("Calender App");

        //viewmodel and livedata for getting live results.
        viewmodel = ViewModelProviders.of(this).get(ViewModelclss.class);
        viewmodel.getData().observe(this, new Observer<List<CalenderData>>() {
            @Override
            public void onChanged(List<CalenderData> calenderData) {
                adapter = new RecyclerAdapter(calenderData, MainActivity.this);
                recyclerView.setAdapter(adapter);
            }
        });

        //for inserting new events using dialog
        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(fulldate.isEmpty())
               {
                   Toast.makeText(MainActivity.this, "select date first...", Toast.LENGTH_SHORT).show();
               }
               else {

                   SelectDateDialog dateDialog = new SelectDateDialog(MainActivity.this, fulldate);
                   dateDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                   dateDialog.show();
               }
            }
        });




        //getting calender date
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year1, int month1, int dayOfMonth) {

                btnedit.setVisibility(View.VISIBLE);
                fulldate = String.valueOf(dayOfMonth + "-" + (month1 + 1) + "-" + year1);

            }
        });


    }
    //menu for clearing data and for search (searchview)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.deleteentries, menu);

        MenuItem item = menu.findItem(R.id.searchitem);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setQueryHint("Search Events");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.deleteall:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                        .setMessage("are you sure!")
                        .setIcon(R.drawable.deleteimg)
                        .setPositiveButton("delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        database.deleteallentry();
                                    }
                                }).start();
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this, "cancel!", Toast.LENGTH_SHORT).show();
                            }
                        });
                builder.create();
                builder.show();
                break;
            case R.id.searchitem:


        }


        return super.onOptionsItemSelected(item);
    }
}