package com.example.calenderapplication;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Index;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DaoInterface {

    @Insert
    void setData(CalenderData data);

    @Query("select * from Calender_tbl order by id DESC")
    LiveData<List<CalenderData>> getData();

    @Query("select * from Calender_tbl")
    List<CalenderData> getDataList();

    @Delete
    void deleteItem(CalenderData calenderData);

    @Query("delete from calender_tbl")
    public void deleteallentry();

}
