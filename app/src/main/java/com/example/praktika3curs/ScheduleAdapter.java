package com.example.praktika3curs;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder> {
    private List<WorkShift> shifts;

    public ScheduleAdapter(List<WorkShift> shifts) {
        this.shifts = shifts;
    }

    @NonNull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_schedule, parent, false);
        return new ScheduleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder holder, int position) {
        WorkShift shift = shifts.get(position);
        holder.textViewEmployeeName.setText(shift.getEmployee().getName());
        holder.textViewTime.setText(shift.getStartTime() + " - " + shift.getEndTime() + "\n" + shift.getDate());
        holder.textViewStation.setText(shift.getStation().getName());
        holder.textViewStatus.setText(shift.getEmployee().getStatus());
        if ("Чрезвычайная ситуация".equals(shift.getEmployee().getStatus())) {
            holder.textViewEmployeeName.setTextColor(0xFF800000); // бордовый
            holder.textViewStatus.setTextColor(0xFF800000);
            holder.itemView.setBackgroundColor(0xFFFFE5E5); // светло-бордовый фон
        } else {
            holder.textViewEmployeeName.setTextColor(0xFF000000);
            holder.textViewStatus.setTextColor(0xFF000000);
            holder.itemView.setBackgroundColor(0xFFFFFFFF);
        }
    }

    @Override
    public int getItemCount() {
        return shifts.size();
    }

    public static class ScheduleViewHolder extends RecyclerView.ViewHolder {
        TextView textViewEmployeeName, textViewTime, textViewStation, textViewStatus;
        public ScheduleViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewEmployeeName = itemView.findViewById(R.id.textViewScheduleEmployeeName);
            textViewTime = itemView.findViewById(R.id.textViewScheduleTime);
            textViewStation = itemView.findViewById(R.id.textViewScheduleStation);
            textViewStatus = itemView.findViewById(R.id.textViewScheduleStatus);
        }
    }
} 