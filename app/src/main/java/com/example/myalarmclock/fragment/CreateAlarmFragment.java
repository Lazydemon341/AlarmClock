package com.example.myalarmclock.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myalarmclock.MainActivity;
import com.example.myalarmclock.R;
import com.example.myalarmclock.model.Alarm;
import com.example.myalarmclock.viewmodel.SharedViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class CreateAlarmFragment extends Fragment {

    private SharedViewModel sharedViewModel;

    private CreateAlarmFragmentArgs args;

    private EditText alarmName;
    private TimePicker timePicker;
    private CheckBox isRecurringAlarm;
    private CheckBox mon, tue, wed, thu, fri, sat, sun;

    private Alarm alarm;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.create_alarm_fragment, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Objects.requireNonNull(((MainActivity) requireActivity()).getSupportActionBar()).setTitle("Create an Alarm");

        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);

        view.findViewById(R.id.fab_finish_creating_an_alarm).setOnClickListener(v -> {
            scheduleAlarm();
            NavHostFragment.findNavController(CreateAlarmFragment.this)
                    .navigate(R.id.action_CreateAlarmFragment_to_AlarmsListFragment);
        });

        ConstraintLayout recurringOptions = view.findViewById(R.id.createalarm_recurring_options);
        ((CheckBox) view.findViewById(R.id.checkbox_is_recurring_alarm)).setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                recurringOptions.setVisibility(View.VISIBLE);
            } else {
                recurringOptions.setVisibility(View.GONE);
            }
        });
        findViews(view);

        args = CreateAlarmFragmentArgs.fromBundle(getArguments());
        Log.d("createFragment", Boolean.toString(args.getIsCreate()));
        if (!args.getIsCreate()) {
            bindAlarm();
        }

        setOnBackPressedCallback();
    }

    private void findViews(@NotNull View view) {
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

    private void bindAlarm() {
        Log.d("createFragment", args.toString());
        alarm = Objects.requireNonNull(sharedViewModel.getAlarmsLiveData().getValue()).get(args.getAlarmIndex());

        if (alarm.isStarted()) {
            // So that the alarm won't
            alarm.cancel(getActivity());
            alarm.setStarted(true);
            Log.d("createFragment", "cancel");
        }

        alarmName.setText(alarm.getName());
        timePicker.setHour(alarm.getHour());
        timePicker.setMinute(alarm.getMinute());
        isRecurringAlarm.setChecked(alarm.isRecurring());
        if (alarm.isRecurring()) {
            mon.setChecked(alarm.isMon());
            tue.setChecked(alarm.isTue());
            wed.setChecked(alarm.isWed());
            thu.setChecked(alarm.isThu());
            fri.setChecked(alarm.isFri());
            sat.setChecked(alarm.isSat());
            sun.setChecked(alarm.isSun());
        }
    }

    private void setOnBackPressedCallback(){
        requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Save or discard changes dialog.
                new AlertDialog.Builder(requireActivity())
                        .setMessage("Save changes or discard them?")
                        .setPositiveButton("Save", (dialog, which) -> {
                            scheduleAlarm();
                            // Navigate to previous alarm list fragment.
                            NavHostFragment.findNavController(CreateAlarmFragment.this)
                                    .navigate(R.id.action_CreateAlarmFragment_to_AlarmsListFragment);
                        })
                        .setNegativeButton("Discard", (dialog, which) -> {
                            if (alarm != null && alarm.isStarted())
                                alarm.schedule(requireActivity());
                            NavHostFragment.findNavController(CreateAlarmFragment.this)
                                    .navigate(R.id.action_CreateAlarmFragment_to_AlarmsListFragment);
                        })
                        .show();
            }
        });
    }

    @Override
    public void onDetach() {
        alarm = null;
        super.onDetach();
    }

    /**
     * Insert the created alarm in a database or update an existing one.
     */
    private void scheduleAlarm() {
        if (args.getIsCreate()) {
            alarm = new Alarm(
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

            sharedViewModel.insert(alarm);
        } else {
            alarm.setName(alarmName.getText().toString());
            alarm.setHour(timePicker.getHour());
            alarm.setMinute(timePicker.getMinute());
            alarm.setStarted(true);
            alarm.setRecurring(isRecurringAlarm.isChecked());
            alarm.setMon(mon.isChecked());
            alarm.setTue(tue.isChecked());
            alarm.setWed(wed.isChecked());
            alarm.setThu(thu.isChecked());
            alarm.setFri(fri.isChecked());
            alarm.setSat(sat.isChecked());
            alarm.setSun(sun.isChecked());

            sharedViewModel.update(alarm);
        }
        alarm.schedule(requireActivity());
    }
}