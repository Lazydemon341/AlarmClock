package com.example.myalarmclock.model;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.myalarmclock.application.App;

import java.util.List;

public class AlarmRepository {
    private static AlarmRepository instance;
    private LiveData<List<Alarm>> alarmsLiveData;

    AlarmDao alarmDao;

    private AlarmRepository(){
        alarmDao = App.getDatabase().getAlarmDao();
        alarmsLiveData = alarmDao.getAlarms();
    }

    public static AlarmRepository getInstance() {
        if (instance == null) {
            instance = new AlarmRepository();
        }
        return instance;
    }

    public void insert(Alarm alarm) {
        alarmDao.insert(alarm);
    }

    public void update(Alarm alarm){
        alarmDao.update(alarm);
    }

    public void delete(Alarm alarm){
        alarmDao.delete(alarm);
    }

    public LiveData<List<Alarm>> getAlarmsLiveData(){
        return alarmsLiveData;
    }
}
