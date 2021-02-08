package com.example.myalarmclock.model;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.myalarmclock.broadcastreceiver.AlarmReceiver;

import java.util.Calendar;

@Entity(tableName = "alarms")
public class Alarm {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private int hour, minute;
    private boolean started, recurring;
    private boolean mon, tue, wed, thu, fri, sat, sun;

    public Alarm(String name, int hour, int minute, boolean started, boolean recurring,
                 boolean mon, boolean tue, boolean wed, boolean thu, boolean fri, boolean sat, boolean sun) {
        this.name = name;
        this.hour = hour;
        this.minute = minute;
        this.started = started;
        this.recurring = recurring;
        this.mon = mon;
        this.tue = tue;
        this.wed = wed;
        this.thu = thu;
        this.fri = fri;
        this.sat = sat;
        this.sun = sun;
    }

    public void schedule(Context context) {
        // TODO: run AlarmManager
//        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
//
//        Intent intent = new Intent(context, AlarmReceiver.class);
//        intent.putExtra(AlarmReceiver.RECURRING, recurring);
//        intent.putExtra(AlarmReceiver.MONDAY, mon);
//        intent.putExtra(AlarmReceiver.TUESDAY, tue);
//        intent.putExtra(AlarmReceiver.WEDNESDAY, wed);
//        intent.putExtra(AlarmReceiver.THURSDAY, thu);
//        intent.putExtra(AlarmReceiver.FRIDAY, fri);
//        intent.putExtra(AlarmReceiver.SATURDAY, sat);
//        intent.putExtra(AlarmReceiver.SUNDAY, sun);
//        intent.putExtra(AlarmReceiver.NAME, name);
//
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent, 0);
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(System.currentTimeMillis());
//        calendar.set(Calendar.HOUR_OF_DAY, hour);
//        calendar.set(Calendar.MINUTE, minute);
//        calendar.set(Calendar.SECOND, 0);
//        calendar.set(Calendar.MILLISECOND, 0);
//
//        if (calendar.getTimeInMillis() < System.currentTimeMillis()){
//            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
//        }
//
//        if (!recurring){
//            alarmManager.setExact(
//                    AlarmManager.RTC_WAKEUP,
//                    calendar.getTimeInMillis(),
//                    pendingIntent);
//        }
//        else{
//            // TODO.
//        }

        started = true;
    }

    public void cancel(Context context) {
        // TODO: cancel AlarmManager
        started = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public boolean isRecurring() {
        return recurring;
    }

    public void setRecurring(boolean recurring) {
        this.recurring = recurring;
    }

    public boolean isMon() {
        return mon;
    }

    public void setMon(boolean mon) {
        this.mon = mon;
    }

    public boolean isTue() {
        return tue;
    }

    public void setTue(boolean tue) {
        this.tue = tue;
    }

    public boolean isWed() {
        return wed;
    }

    public void setWed(boolean wed) {
        this.wed = wed;
    }

    public boolean isThu() {
        return thu;
    }

    public void setThu(boolean thu) {
        this.thu = thu;
    }

    public boolean isFri() {
        return fri;
    }

    public void setFri(boolean fri) {
        this.fri = fri;
    }

    public boolean isSat() {
        return sat;
    }

    public void setSat(boolean sat) {
        this.sat = sat;
    }

    public boolean isSun() {
        return sun;
    }

    public void setSun(boolean sun) {
        this.sun = sun;
    }
}
