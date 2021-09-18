package com.example.calenderapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Dao;
import androidx.room.RoomDatabase;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    CalendarView calendarView;
    ImageButton btnedit;
    DaoInterface database;
    CalenderData calenderData;
    RecyclerAdapter adapter;
    RecyclerView recyclerView;
    List<CalenderData> list;
    Activity activity;
    String fulldate;
    ViewModelclss viewmodel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calendarView = findViewById(R.id.calenderview);
        btnedit = findViewById(R.id.addeventbtn);
        database =RoomDataBase.getInstance(this).getDao();
        getSupportActionBar().setSubtitle("Calender App");
       activity=this;
       viewmodel= ViewModelProviders.of(this).get(ViewModelclss.class);
       viewmodel.getData().observe(this, new Observer<List<CalenderData>>() {
           @Override
           public void onChanged(List<CalenderData> calenderData) {
               adapter=new RecyclerAdapter(calenderData,MainActivity.this);
               recyclerView.setAdapter(adapter);
           }
       });



        btnedit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               SelectDateDialog dateDialog=new SelectDateDialog(MainActivity.this,fulldate);
               dateDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
               dateDialog.show();
           }
       });


        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        list=new ArrayList<>();



                calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView view, int year1, int month1, int dayOfMonth) {

                        btnedit.setVisibility(View.VISIBLE);
                       fulldate=String.valueOf(dayOfMonth + "-" + (month1 + 1) + "-" + year1);

                    }
                });





    }
}