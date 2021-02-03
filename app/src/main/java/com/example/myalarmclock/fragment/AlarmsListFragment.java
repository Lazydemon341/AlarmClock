package com.example.myalarmclock.fragment;

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
import com.example.myalarmclock.viewmodel.SharedViewModel;

import java.util.Objects;

public class AlarmsListFragment extends Fragment implements AlarmsRecyclerViewAdapter.OnAlarmClickListener{

    SharedViewModel sharedViewModel;

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

        Objects.requireNonNull(((MainActivity) requireActivity()).getSupportActionBar()).setTitle("Alarms list");

        view.findViewById(R.id.fab_add_alarm).setOnClickListener(view1 ->
                NavHostFragment.findNavController(AlarmsListFragment.this)
                        .navigate(R.id.action_AlarmsListFragment_to_CreateAlarmFragment));

        alarmsRecyclerView = view.findViewById(R.id.alarms_recycler_view);
        initRecyclerView();
    }

    /**
     * Set the RecyclerView adapter and layout manager
     */
    private void initRecyclerView(){
        alarmsRecyclerViewAdapter = new AlarmsRecyclerViewAdapter(this);
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        alarmsRecyclerView.setLayoutManager(linearLayoutManager);
        alarmsRecyclerView.setAdapter(alarmsRecyclerViewAdapter);
    }

    @Override
    public void onAlarmClick(int position) {
        sharedViewModel.delete(alarmsRecyclerViewAdapter.getAlarmAt(position));
    }
}