package com.example.myalarmclock.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AlarmDao {
    @Insert
    void insert(Alarm alarm);

    @Delete
    void delete(Alarm alarm);

    @Query("SELECT * FROM alarms ORDER BY hour, minute ASC")
    LiveData<List<Alarm>> getAlarms();

    @Update
    void update(Alarm alarm);
}
