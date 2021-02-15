package com.example.myalarmclock.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myalarmclock.R;
import com.example.myalarmclock.model.Alarm;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.Arrays;
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
        private final TextView alarmTime = itemView.findViewById(R.id.textview_alarm_time);
        private final TextView alarmName = itemView.findViewById(R.id.textview_alarm_name);
        private final SwitchMaterial alarmSwitch = itemView.findViewById(R.id.switch_activate_alarm);

        private final List<TextView> alarmDays = Arrays.asList(
                itemView.findViewById(R.id.textview_alarm_mon),
                itemView.findViewById(R.id.textview_alarm_tue),
                itemView.findViewById(R.id.textview_alarm_wed),
                itemView.findViewById(R.id.textview_alarm_thu),
                itemView.findViewById(R.id.textview_alarm_fri),
                itemView.findViewById(R.id.textview_alarm_sat),
                itemView.findViewById(R.id.textview_alarm_sun)
        );

        private final OnAlarmClickListener onAlarmClickListener;
        private final OnAlarmSwitchListener onAlarmSwitchListener;

        public AlarmViewHolder(@NonNull View itemView,
                               OnAlarmClickListener onAlarmClickListener,
                               OnAlarmSwitchListener onAlarmSwitchListener) {
            super(itemView);

            this.onAlarmClickListener = onAlarmClickListener;
            this.onAlarmSwitchListener = onAlarmSwitchListener;
        }

        public void bind(Alarm alarm) {
            alarmSwitch.setClickable(false);
            alarmSwitch.setChecked(alarm.isStarted());
            alarmTime.setText(String.format("%02d:%02d", alarm.getHour(), alarm.getMinute()));
            alarmName.setText(alarm.getName());
            changeColors(alarm);

            alarmSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
                onAlarmSwitchListener.onAlarmSwitch(alarm);
                changeColors(alarm);
            });
            itemView.setOnClickListener(v ->
                    onAlarmClickListener.onAlarmClick(getAdapterPosition()));

            alarmSwitch.setClickable(true);
        }

        private void changeColors(Alarm alarm) {
            if (!alarm.isStarted()) {
                alarmTime.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.light_grey));
                for (TextView day : alarmDays)
                    day.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.light_grey));
            } else {
                alarmTime.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.black));
                if (alarm.isMon())
                    alarmDays.get(0).setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.teal_200));
                if (alarm.isTue())
                    alarmDays.get(1).setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.teal_200));
                if (alarm.isWed())
                    alarmDays.get(2).setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.teal_200));
                if (alarm.isThu())
                    alarmDays.get(3).setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.teal_200));
                if (alarm.isFri())
                    alarmDays.get(4).setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.teal_200));
                if (alarm.isSat())
                    alarmDays.get(5).setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.teal_200));
                if (alarm.isSun())
                    alarmDays.get(6).setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.teal_200));
            }
        }
    }

    private static class DiffCallback extends DiffUtil.ItemCallback<Alarm> {
        @Override
        public boolean areItemsTheSame(@NonNull Alarm oldItem,
                                       @NonNull Alarm newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Alarm oldItem,
                                          @NonNull Alarm newItem) {
            return oldItem.equals(newItem);
        }
    }
}
