package com.example.myalarmclock.model;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Alarm.class}, version = 1, exportSchema = false)
public abstract class AlarmDatabase extends RoomDatabase {
    public abstract AlarmDao getAlarmDao();
}
