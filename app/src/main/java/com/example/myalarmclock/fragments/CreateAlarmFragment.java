package com.example.myalarmclock.fragments;

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

import com.example.myalarmclock.activities.MainActivity;
import com.example.myalarmclock.R;
import com.example.myalarmclock.model.Alarm;
import com.example.myalarmclock.viewmodel.SharedViewModel;

import java.util.Objects;

public class CreateAlarmFragment extends Fragment {

    private SharedViewModel mViewModel;

    EditText alarmName;
    TimePicker timePicker;
    CheckBox mon, tue, wed, thu, fri, sat, sun;

    public static CreateAlarmFragment newInstance() {
        return new CreateAlarmFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.create_alarm_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SharedViewModel.class);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Objects.requireNonNull(((MainActivity) requireActivity()).getSupportActionBar()).setTitle("Create alarm");

        view.findViewById(R.id.fab_finish_creating_an_alarm).setOnClickListener(view1 ->
                NavHostFragment.findNavController(CreateAlarmFragment.this)
                        .navigate(R.id.action_CreateAlarmFragment_to_AlarmsListFragment));

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

        mon = view.findViewById(R.id.checkbox_monday);
        tue = view.findViewById(R.id.checkbox_tuesday);
        wed = view.findViewById(R.id.checkbox_wednesday);
        thu = view.findViewById(R.id.checkbox_thursday);
        fri = view.findViewById(R.id.checkbox_friday);
        sat = view.findViewById(R.id.checkbox_saturday);
        sun = view.findViewById(R.id.checkbox_sunday);
    }

    private void scheduleAlarm() {
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

        mViewModel.insert(alarm);
    }

}