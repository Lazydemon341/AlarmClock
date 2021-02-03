package com.example.myalarmclock.application;

import android.app.Application;

import androidx.room.Room;

import com.example.myalarmclock.model.AlarmDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AlarmClockApp extends Application {
    public static AlarmClockApp instance;
    private static AlarmDatabase database;
    private static final ExecutorService executorService = Executors.newFixedThreadPool(4);

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, AlarmDatabase.class, "alarms_database")
                .setQueryExecutor(executorService)
                .build();
    }

    public static AlarmClockApp getInstance() {
        return instance;
    }

    public AlarmDatabase getDatabase() {
        return database;
    }
}
