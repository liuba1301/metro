package com.example.praktika3curs;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class EmployeesAdapter extends RecyclerView.Adapter<EmployeesAdapter.EmployeeViewHolder> {
    private List<Employee> employees;
    private OnStatusClickListener statusClickListener;

    public interface OnStatusClickListener {
        void onStatusClick(Employee employee);
    }

    public EmployeesAdapter(List<Employee> employees, OnStatusClickListener listener) {
        this.employees = employees;
        this.statusClickListener = listener;
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_employee, parent, false);
        return new EmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        Employee emp = employees.get(position);
        holder.textViewName.setText(emp.name);
        holder.textViewStatus.setText(emp.status);
        holder.buttonChangeStatus.setOnClickListener(v -> {
            if (statusClickListener != null) statusClickListener.onStatusClick(emp);
        });
    }

    @Override
    public int getItemCount() {
        return employees.size();
    }

    public static class EmployeeViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewStatus;
        Button buttonChangeStatus;
        public EmployeeViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewEmployeeName);
            textViewStatus = itemView.findViewById(R.id.textViewEmployeeStatus);
            buttonChangeStatus = itemView.findViewById(R.id.buttonChangeStatus);
        }
    }
} 