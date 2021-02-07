package com.example.myalarmclock.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myalarmclock.MainActivity;
import com.example.myalarmclock.R;
import com.example.myalarmclock.adapter.AlarmsRecyclerViewAdapter;
import com.example.myalarmclock.model.Alarm;
import com.example.myalarmclock.viewmodel.SharedViewModel;

import java.util.Objects;

public class AlarmsListFragment extends Fragment
        implements AlarmsRecyclerViewAdapter.OnAlarmClickListener, AlarmsRecyclerViewAdapter.OnAlarmSwitchListener {

    private SharedViewModel sharedViewModel;

    private AlarmsRecyclerViewAdapter alarmsRecyclerViewAdapter;
    private RecyclerView alarmsRecyclerView;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.alarms_list_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);
        sharedViewModel.getAlarmsLiveData().observe(getViewLifecycleOwner(), alarms -> {
            if (alarms != null) {
                alarmsRecyclerViewAdapter.setAlarms(alarms);
            }
        });
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Objects.requireNonNull(((MainActivity) requireActivity()).getSupportActionBar()).setTitle("My Alarms");

        view.findViewById(R.id.fab_add_alarm).setOnClickListener(view1 ->
            NavHostFragment.findNavController(AlarmsListFragment.this)
                    .navigate(AlarmsListFragmentDirections.actionAlarmsListFragmentToCreateAlarmFragment()
                            .setIsCreate(true)));

        alarmsRecyclerView = view.findViewById(R.id.alarms_recycler_view);
        initRecyclerView();
    }

    /**
     * Set the RecyclerView adapter and layout manager
     */
    private void initRecyclerView() {
        alarmsRecyclerViewAdapter = new AlarmsRecyclerViewAdapter(this, this);
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        alarmsRecyclerView.setLayoutManager(linearLayoutManager);
        alarmsRecyclerView.setAdapter(alarmsRecyclerViewAdapter);
    }

    @Override
    public void onAlarmClick(int position) {
        Alarm alarm = alarmsRecyclerViewAdapter.getAlarmAt(position);
        new AlertDialog.Builder(getActivity())
                .setMessage(alarmsRecyclerViewAdapter.getAlarmAt(position).getName())
                .setPositiveButton("EDIT", (dialog, which) -> {

                    // Indicate that CreateAlarmFragment should edit existing alarm and not create a new one
                    // and pass all the needed info about the alarm.

                    AlarmsListFragmentDirections.ActionAlarmsListFragmentToCreateAlarmFragment action =
                            AlarmsListFragmentDirections.actionAlarmsListFragmentToCreateAlarmFragment();

                    action.setIsCreate(false).setAlarmId(alarm.getId()).setAlarmName(alarm.getName())
                            .setAlarmHour(alarm.getHour()).setAlarmMinute(alarm.getMinute())
                            .setAlarmIsRecurring(alarm.isRecurring());
                    if (alarm.isRecurring()){
                        action.setAlarmIsMon(alarm.isMon()).setAlarmIsTue(alarm.isTue())
                                .setAlarmIsWed(alarm.isWed()).setAlarmIsThu(alarm.isThu())
                                .setAlarmIsFri(alarm.isFri()).setAlarmIsSat(alarm.isSat())
                                .setAlarmIsSun(alarm.isSun());
                    }
                    NavHostFragment.findNavController(AlarmsListFragment.this)
                            .navigate(action);
                })
                .setNegativeButton("DELETE", (dialog, which) ->
                        sharedViewModel.delete(alarm))
                .show();
    }

    @Override
    public void onAlarmSwitch(int position) {
        Alarm alarm = alarmsRecyclerViewAdapter.getAlarmAt(position);
        if (alarm.isStarted()) {
            alarm.cancel();
        } else {
            alarm.schedule();
        }
        sharedViewModel.update(alarm);
    }
}