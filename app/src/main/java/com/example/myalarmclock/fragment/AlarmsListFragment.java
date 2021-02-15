package com.example.myalarmclock.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
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
        implements AlarmsRecyclerViewAdapter.OnAlarmClickListener,
        AlarmsRecyclerViewAdapter.OnAlarmSwitchListener {

    private SharedViewModel sharedViewModel;

    private AlarmsRecyclerViewAdapter alarmsRecyclerViewAdapter;
    private RecyclerView alarmsRecyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Quit to home screen when back button is pressed.
        requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().moveTaskToBack(true);
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.alarms_list_fragment, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Objects.requireNonNull(((MainActivity) requireActivity()).getSupportActionBar()).setTitle("My Alarms");

        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);
        sharedViewModel.getAlarmsLiveData().observe(getViewLifecycleOwner(), alarms -> {
            if (alarms != null) {
                alarmsRecyclerViewAdapter.submitList(alarms);
            }
        });

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
        alarmsRecyclerViewAdapter =
                new AlarmsRecyclerViewAdapter(this, this);
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        alarmsRecyclerView.setLayoutManager(linearLayoutManager);
        alarmsRecyclerView.setAdapter(alarmsRecyclerViewAdapter);
    }

    @Override
    public void onAlarmClick(int position) {
        Alarm alarm = alarmsRecyclerViewAdapter.getAlarmAt(position);
        new AlertDialog.Builder(requireActivity())
                .setMessage(String.format("Delete alarm \"%s\" or edit it?",
                        alarmsRecyclerViewAdapter.getAlarmAt(position).getName()))
                .setPositiveButton("Edit", (dialog, which) -> {

                    // Indicate that CreateAlarmFragment should edit existing alarm and not create a new one
                    // and pass all the needed info about the alarm.

                    AlarmsListFragmentDirections.ActionAlarmsListFragmentToCreateAlarmFragment action =
                            AlarmsListFragmentDirections.actionAlarmsListFragmentToCreateAlarmFragment();
                    action.setIsCreate(false).setAlarmIndex(position);

                    NavHostFragment.findNavController(AlarmsListFragment.this)
                            .navigate(action);
                })
                .setNegativeButton("Delete", (dialog, which) ->
                        sharedViewModel.delete(alarm))
                .show();
    }

    @Override
    public void onAlarmSwitch(Alarm alarm) {
        if (alarm.isStarted()) {
            alarm.cancel(requireActivity());
        } else {
            alarm.schedule(requireActivity());
        }
        sharedViewModel.update(alarm);
    }
}