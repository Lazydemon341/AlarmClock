package com.example.myalarmclock.viewmodel;

import androidx.lifecycle.ViewModel;

import com.example.myalarmclock.model.Alarm;
import com.example.myalarmclock.model.AlarmRepository;

public class SharedViewModel extends ViewModel {
    private AlarmRepository alarmRepository;

    public SharedViewModel() {
        super();
        alarmRepository = AlarmRepository.getInstance();
    }

    public void insert(Alarm alarm){
        alarmRepository.insert(alarm);
    }
}