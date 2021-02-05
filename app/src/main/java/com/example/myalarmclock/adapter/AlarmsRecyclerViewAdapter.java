package com.example.myalarmclock.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myalarmclock.R;
import com.example.myalarmclock.model.Alarm;

import java.util.ArrayList;
import java.util.List;

public class AlarmsRecyclerViewAdapter extends RecyclerView.Adapter<AlarmsRecyclerViewAdapter.AlarmViewHolder> {

    private List<Alarm> alarms;
    private final OnAlarmClickListener onAlarmClickListener;

    public AlarmsRecyclerViewAdapter(OnAlarmClickListener onAlarmClickListener) {
        this.onAlarmClickListener = onAlarmClickListener;
        alarms = new ArrayList<>();
    }

    public static class AlarmViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView alarmTime;
        private final TextView alarmName;
        private final OnAlarmClickListener onAlarmClickListener;

        public AlarmViewHolder(@NonNull View itemView, OnAlarmClickListener onAlarmClickListener) {
            super(itemView);

            alarmTime = itemView.findViewById(R.id.textview_alarm_time);
            alarmName = itemView.findViewById(R.id.textview_alarm_name);

            this.onAlarmClickListener = onAlarmClickListener;
            itemView.setOnClickListener(this);
        }

        public void bind(Alarm alarm) {
            alarmTime.setText(String.format("%02d:%02d", alarm.getHour(), alarm.getMinute()));
            alarmName.setText(alarm.getName());
        }

        @Override
        public void onClick(View v) {
            onAlarmClickListener.onAlarmClick(getAdapterPosition());
        }
    }

    public interface OnAlarmClickListener {
        void onAlarmClick(int position);
    }

    @NonNull
    @Override
    public AlarmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.alarm_list_item, parent, false);

        return new AlarmViewHolder(view, onAlarmClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmViewHolder holder, int position) {
        Alarm alarm = alarms.get(position);
        holder.bind(alarm);
    }

    @Override
    public int getItemCount() {
        return alarms.size();
    }

    public void setAlarms(List<Alarm> alarms) {
        this.alarms = alarms;
        notifyDataSetChanged();
    }

    public Alarm getAlarmAt(int position) {
        return alarms.get(position);
    }
}
