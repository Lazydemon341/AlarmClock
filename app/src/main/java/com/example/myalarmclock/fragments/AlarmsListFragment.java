package com.example.myalarmclock.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myalarmclock.activities.MainActivity;
import com.example.myalarmclock.R;
import com.example.myalarmclock.viewmodel.SharedViewModel;

import java.util.Objects;

public class AlarmsListFragment extends Fragment {

    SharedViewModel mViewModel;

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
        mViewModel = new ViewModelProvider(this).get(SharedViewModel.class);
        // TODO: Use the ViewModel
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Objects.requireNonNull(((MainActivity) requireActivity()).getSupportActionBar()).setTitle("Alarms list");

        view.findViewById(R.id.fab_add_alarm).setOnClickListener(view1 ->
                NavHostFragment.findNavController(AlarmsListFragment.this)
                        .navigate(R.id.action_AlarmsListFragment_to_CreateAlarmFragment));
    }
}