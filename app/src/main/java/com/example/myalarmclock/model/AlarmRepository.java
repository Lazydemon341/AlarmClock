package com.example.myalarmclock.model;

import androidx.lifecycle.LiveData;

import com.example.myalarmclock.application.AlarmClockApp;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class AlarmRepository {
    private static AlarmRepository instance;
    private final LiveData<List<Alarm>> alarmsLiveData;
    private final AlarmDatabase alarmDatabase;
    private final AlarmDao alarmDao;

    private AlarmRepository() {
        alarmDatabase = AlarmClockApp.getInstance().getDatabase();
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
        alarmDatabase.getQueryExecutor()
                .execute(() -> alarmDao.insert(alarm));
    }

    public void update(Alarm alarm) {
        alarmDatabase.getQueryExecutor()
                .execute(() -> alarmDao.update(alarm));
    }

    public void delete(Alarm alarm) {
        alarmDatabase.getQueryExecutor()
                .execute(() -> alarmDao.delete(alarm));
    }

    public LiveData<List<Alarm>> getAlarmsLiveData() {
        return alarmsLiveData;
    }
}
