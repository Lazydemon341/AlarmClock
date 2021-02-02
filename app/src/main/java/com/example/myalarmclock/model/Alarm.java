package com.example.myalarmclock.model;

public class Alarm {
    String name;
    private int hour, minute;
    private boolean monday, tuesday, wednesday, thursday, friday, saturday, sunday;

    public Alarm(String name, int hour, int minute, boolean monday, boolean tuesday, boolean wednesday, boolean thursday, boolean friday, boolean saturday, boolean sunday) {
        this.name = name;
        this.hour = hour;
        this.minute = minute;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.sunday = sunday;
    }
}
