package com.example.myalarmclock.application;

import android.app.Application;

import androidx.room.Room;

import com.example.myalarmclock.model.AlarmDatabase;

public class App extends Application {
    public static App instance;

    private static AlarmDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, AlarmDatabase.class, "database").build();
    }

    public static App getInstance(){
        return instance;
    }

    public static AlarmDatabase getDatabase(){
        return database;
    }
}
