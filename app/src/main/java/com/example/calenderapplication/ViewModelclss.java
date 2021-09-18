package com.example.calenderapplication;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class ViewModelclss extends ViewModel {
    DaoInterface database;

    public ViewModelclss() {
        database=RoomDataBase.instance.getDao();
    }

    public LiveData<List<CalenderData>> getData()
    {
        return database.getData();
    }

}
