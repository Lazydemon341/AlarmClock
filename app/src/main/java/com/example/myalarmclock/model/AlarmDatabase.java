package com.example.myalarmclock.model;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Alarm.class}, version = 1, exportSchema = false)
public abstract class AlarmDatabase extends RoomDatabase {
    private static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(4);

    public abstract AlarmDao getAlarmDao();

    public Executor getExecutor(){
        return databaseExecutor;
    }
}
