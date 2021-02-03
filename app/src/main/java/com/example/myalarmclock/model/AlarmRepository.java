package com.example.myalarmclock.model;

import android.util.Log;

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
    private final LiveData<List<Alarm>> alarmsLiveData;
    private final AlarmDatabase alarmDatabase;
    private final AlarmDao alarmDao;

    private AlarmRepository(){
        alarmDatabase = App.getInstance().getDatabase();
        alarmDao = alarmDatabase.getAlarmDao();
        alarmsLiveData = alarmDao.getAlarms();
    }

    public static AlarmRepository getInstance() {
        if (instance == null) {
            instance = new AlarmRepository();
        }
        return instance;
    }

    public void insert(Alarm alarm) {
        alarmDatabase.getExecutor()
                .execute(()->alarmDao.insert(alarm));
    }

    public void update(Alarm alarm){
        alarmDatabase.getExecutor()
                .execute(()->alarmDao.update(alarm));
    }

    public void delete(Alarm alarm){
        alarmDatabase.getExecutor()
                .execute(()->alarmDao.delete(alarm));
    }

    public LiveData<List<Alarm>> getAlarmsLiveData(){
        return alarmsLiveData;
    }
}
