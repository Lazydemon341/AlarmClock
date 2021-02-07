package com.example.myalarmclock.fragment;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
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

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CreateAlarmFragment extends Fragment {

    private SharedViewModel sharedViewModel;

    private CreateAlarmFragmentArgs args;

    private EditText alarmName;
    private TimePicker timePicker;
    private CheckBox isRecurringAlarm;
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
        findViews(view);

        args = CreateAlarmFragmentArgs.fromBundle(getArguments());
        if (!args.getIsCreate()){
            bindAlarm();
        }
    }

    private void findViews(@NotNull View view){
        alarmName = view.findViewById(R.id.edittext_alarm_name);

        timePicker = view.findViewById(R.id.create_alarm_timePicker);
        timePicker.setIs24HourView(true);

        isRecurringAlarm = view.findViewById(R.id.checkbox_is_recurring_alarm);

        mon = view.findViewById(R.id.checkbox_monday);
        tue = view.findViewById(R.id.checkbox_tuesday);
        wed = view.findViewById(R.id.checkbox_wednesday);
        thu = view.findViewById(R.id.checkbox_thursday);
        fri = view.findViewById(R.id.checkbox_friday);
        sat = view.findViewById(R.id.checkbox_saturday);
        sun = view.findViewById(R.id.checkbox_sunday);
    }

    private void bindAlarm(){
        alarmName.setText(args.getAlarmName());
        timePicker.setHour(args.getAlarmHour());
        timePicker.setMinute(args.getAlarmMinute());
        isRecurringAlarm.setChecked(args.getAlarmIsRecurring());
        if (isRecurringAlarm.isChecked()){
            mon.setChecked(args.getAlarmIsMon());
            tue.setChecked(args.getAlarmIsTue());
            wed.setChecked(args.getAlarmIsWed());
            thu.setChecked(args.getAlarmIsThu());
            fri.setChecked(args.getAlarmIsFri());
            sat.setChecked(args.getAlarmIsSat());
            sun.setChecked(args.getAlarmIsSun());
        }
        Log.d("args", args.toString());
    }

    /**
     * Insert the created alarm in a database or update an existing one.
     */
    private void scheduleAlarm() {
        Alarm alarm = new Alarm(
                alarmName.getText().toString(),
                timePicker.getHour(),
                timePicker.getMinute(),
                true,
                isRecurringAlarm.isChecked(),
                mon.isChecked(),
                tue.isChecked(),
                wed.isChecked(),
                thu.isChecked(),
                fri.isChecked(),
                sat.isChecked(),
                sun.isChecked());

        if (args.getIsCreate()) {
            sharedViewModel.insert(alarm);
        } else {
            // Update an existing alarm using passed alarmId as a key.
            alarm.setId(args.getAlarmId());
            sharedViewModel.update(alarm);
        }
    }
}