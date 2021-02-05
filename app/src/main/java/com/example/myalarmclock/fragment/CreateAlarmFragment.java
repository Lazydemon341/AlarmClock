package com.example.myalarmclock.fragment;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import com.example.myalarmclock.MainActivity;
import com.example.myalarmclock.R;
import com.example.myalarmclock.model.Alarm;
import com.example.myalarmclock.viewmodel.SharedViewModel;

import java.util.Objects;

public class CreateAlarmFragment extends Fragment {
    boolean isCreate;

    private SharedViewModel sharedViewModel;

    private EditText alarmName;
    private TimePicker timePicker;
    private CheckBox mon, tue, wed, thu, fri, sat, sun;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.create_alarm_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);
        isCreate = getArguments().getBoolean("isCreate");
        // TODO.
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Objects.requireNonNull(((MainActivity) requireActivity()).getSupportActionBar()).setTitle("Create an Alarm");

        view.findViewById(R.id.fab_finish_creating_an_alarm).setOnClickListener(v -> {
            scheduleAlarm();
            NavHostFragment.findNavController(CreateAlarmFragment.this)
                    .navigate(R.id.action_CreateAlarmFragment_to_AlarmsListFragment);
        });

        LinearLayout recurringOptions = view.findViewById(R.id.createalarm_recurring_options);
        ((CheckBox) view.findViewById(R.id.checkbox_is_recurring_alarm)).setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                recurringOptions.setVisibility(View.VISIBLE);
            } else {
                recurringOptions.setVisibility(View.GONE);
            }
        });

        alarmName = view.findViewById(R.id.edittext_alarm_name);

        timePicker = view.findViewById(R.id.create_alarm_timePicker);
        timePicker.setIs24HourView(true);

        mon = view.findViewById(R.id.checkbox_monday);
        tue = view.findViewById(R.id.checkbox_tuesday);
        wed = view.findViewById(R.id.checkbox_wednesday);
        thu = view.findViewById(R.id.checkbox_thursday);
        fri = view.findViewById(R.id.checkbox_friday);
        sat = view.findViewById(R.id.checkbox_saturday);
        sun = view.findViewById(R.id.checkbox_sunday);
    }

    private void scheduleAlarm() {
        if (isCreate) {
            Alarm alarm = new Alarm(
                    alarmName.getText().toString(),
                    timePicker.getHour(),
                    timePicker.getMinute(),
                    mon.isChecked(),
                    tue.isChecked(),
                    wed.isChecked(),
                    thu.isChecked(),
                    fri.isChecked(),
                    sat.isChecked(),
                    sun.isChecked()
            );

            sharedViewModel.insert(alarm);
        }
//        else{
//            int alarmId = getArguments().getInt("alarmId");
//            Alarm alarm = new Alarm(
//                    alarmName.getText().toString(),
//                    timePicker.getHour(),
//                    timePicker.getMinute(),
//                    mon.isChecked(),
//                    tue.isChecked(),
//                    wed.isChecked(),
//                    thu.isChecked(),
//                    fri.isChecked(),
//                    sat.isChecked(),
//                    sun.isChecked()
//            );
//            alarm.setAlarmId(alarmId);
//
//            sharedViewModel.update(alarm);
//        }
        // TODO
    }

}