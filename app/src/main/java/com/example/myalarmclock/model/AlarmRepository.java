package com.example.myalarmclock.model;

import androidx.lifecycle.LiveData;

import java.util.List;

public class AlarmRepository {
    private static AlarmRepository instance;
    private LiveData<List<Alarm>> alarmsLiveData;

    public static AlarmRepository getInstance() {
        if (instance == null) {
            instance = new AlarmRepository();
        }
        return instance;
    }

    public void insert(Alarm alarm) {

    }

    public LiveData<List<Alarm>> getAlarmsLiveData(){
        return alarmsLivedata;
    }
}
