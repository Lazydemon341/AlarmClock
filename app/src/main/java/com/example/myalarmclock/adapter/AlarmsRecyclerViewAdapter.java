package com.example.myalarmclock.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myalarmclock.R;
import com.example.myalarmclock.model.Alarm;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.ArrayList;
import java.util.List;

// TODO: implement ItemAnimator
public class AlarmsRecyclerViewAdapter extends ListAdapter<Alarm, AlarmsRecyclerViewAdapter.AlarmViewHolder> {

    private final OnAlarmClickListener onAlarmClickListener;
    private final OnAlarmSwitchListener onAlarmSwitchListener;


    public AlarmsRecyclerViewAdapter(OnAlarmClickListener onAlarmClickListener,
                                     OnAlarmSwitchListener onAlarmSwitchListener) {
        super(new DiffCallback());
        this.onAlarmClickListener = onAlarmClickListener;
        this.onAlarmSwitchListener = onAlarmSwitchListener;
    }

    @NonNull
    @Override
    public AlarmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.alarm_list_item, parent, false);
        return new AlarmViewHolder(view, onAlarmClickListener, onAlarmSwitchListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    @Override
    public void onViewRecycled(@NonNull AlarmViewHolder holder) {
        super.onViewRecycled(holder);
        holder.alarmSwitch.setOnCheckedChangeListener(null);
    }

    public Alarm getAlarmAt(int position) {
        return getItem(position);
    }

    public interface OnAlarmClickListener {
        void onAlarmClick(int position);
    }

    public interface OnAlarmSwitchListener {
        void onAlarmSwitch(Alarm alarm);
    }

    public static class AlarmViewHolder extends RecyclerView.ViewHolder {
        private final TextView alarmTime;
        private final TextView alarmName;
        private final SwitchMaterial alarmSwitch;

        private final OnAlarmClickListener onAlarmClickListener;
        private final OnAlarmSwitchListener onAlarmSwitchListener;

        public AlarmViewHolder(@NonNull View itemView,
                               OnAlarmClickListener onAlarmClickListener,
                               OnAlarmSwitchListener onAlarmSwitchListener) {
            super(itemView);

            alarmTime = itemView.findViewById(R.id.textview_alarm_time);
            alarmName = itemView.findViewById(R.id.textview_alarm_name);
            alarmSwitch = itemView.findViewById(R.id.switch_activate_alarm);

            this.onAlarmClickListener = onAlarmClickListener;
            this.onAlarmSwitchListener = onAlarmSwitchListener;
        }

        public void bind(Alarm alarm) {
            alarmTime.setText(String.format("%02d:%02d", alarm.getHour(), alarm.getMinute()));
            alarmName.setText(alarm.getName());
            alarmSwitch.setChecked(alarm.isStarted());

            alarmSwitch.setOnCheckedChangeListener((buttonView, isChecked) ->
                    onAlarmSwitchListener.onAlarmSwitch(alarm));

            itemView.setOnClickListener(v ->
                    onAlarmClickListener.onAlarmClick(getAdapterPosition()));
        }
    }

    private static class DiffCallback extends DiffUtil.ItemCallback<Alarm> {
        @Override public boolean areItemsTheSame(@NonNull Alarm oldItem,
                @NonNull Alarm newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override public boolean areContentsTheSame(@NonNull Alarm oldItem,
                @NonNull Alarm newItem) {
            return oldItem.equals(newItem);
        }
    }
}
