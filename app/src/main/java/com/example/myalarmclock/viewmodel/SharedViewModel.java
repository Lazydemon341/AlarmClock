package com.example.myalarmclock.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myalarmclock.model.Alarm;
import com.example.myalarmclock.model.AlarmRepository;

import java.util.List;

public class SharedViewModel extends ViewModel {
    private final AlarmRepository alarmRepository;
    private final LiveData<List<Alarm>> alarmsLiveData;

    public SharedViewModel() {
        super();
        alarmRepository = AlarmRepository.getInstance();
        alarmsLiveData = alarmRepository.getAlarmsLiveData();
    }

    public void insert(Alarm alarm) {
        alarmRepository.insert(alarm);
    }

    public void update(Alarm alarm) {
        alarmRepository.update(alarm);
    }

    public void delete(Alarm alarm) {
        alarmRepository.delete(alarm);
    }

    public LiveData<List<Alarm>> getAlarmsLiveData() {
        return alarmsLiveData;
    }
}