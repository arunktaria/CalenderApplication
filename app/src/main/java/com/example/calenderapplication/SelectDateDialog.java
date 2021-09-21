package com.example.calenderapplication;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SelectDateDialog extends Dialog {
    EditText eventname, eventdes;
    Button submitbtn;
    CalenderData data;
    Context context;
    String fulldate;
    DaoInterface database;

    public SelectDateDialog(@NonNull Context context, String fulldate) {
        super(context);
        this.context = context;
        this.fulldate = fulldate;
        database = RoomDataBase.instance.getDao();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_select_date_dialog);
        eventdes = findViewById(R.id.eventdes);
        eventname = findViewById(R.id.eventnameedittxt);
        submitbtn = findViewById(R.id.btnsubmit);

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        data = new CalenderData();
                        data.setDate(fulldate);
                        data.setEventName(eventname.getText().toString());
                        data.setDes(eventdes.getText().toString());
                        database.setData(data);
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, "Inserted!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).start();
            }
        });

    }


}