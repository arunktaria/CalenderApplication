package com.example.calenderapplication;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class OpenedDialog extends Dialog {

TextView eventname,des,date;
String nametxt,destxt,datetxt;
    public OpenedDialog(@NonNull Context context,String name,String des,String date) {
        super(context);
        this.nametxt=name;
        this.destxt=des;
        this.datetxt=date;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_opened_dialog);
        eventname=findViewById(R.id.openeditemtitle);
        des=findViewById(R.id.openitemdes);
        date=findViewById(R.id.openitemdate);

        eventname.setText(nametxt);
        des.setText(destxt);
        date.setText(datetxt);


    }
}