package com.example.praktika3curs;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ApplicationsAdapter extends RecyclerView.Adapter<ApplicationsAdapter.ApplicationViewHolder> {
    private List<Application> applications;
    private OnAssignClickListener assignClickListener;

    public interface OnAssignClickListener {
        void onAssignClick(Application application);
    }

    public ApplicationsAdapter(List<Application> applications, OnAssignClickListener listener) {
        this.applications = applications;
        this.assignClickListener = listener;
    }

    @NonNull
    @Override
    public ApplicationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_application, parent, false);
        return new ApplicationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ApplicationViewHolder holder, int position) {
        Application app = applications.get(position);
        holder.textViewId.setText(app.id);
        holder.textViewPassengerName.setText(app.passengerName);
        holder.textViewStatus.setText(app.status);
        holder.buttonAssign.setOnClickListener(v -> {
            if (assignClickListener != null) assignClickListener.onAssignClick(app);
        });
    }

    @Override
    public int getItemCount() {
        return applications.size();
    }

    public static class ApplicationViewHolder extends RecyclerView.ViewHolder {
        TextView textViewId, textViewPassengerName, textViewStatus;
        Button buttonAssign;
        public ApplicationViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewId = itemView.findViewById(R.id.textViewApplicationId);
            textViewPassengerName = itemView.findViewById(R.id.textViewPassengerName);
            textViewStatus = itemView.findViewById(R.id.textViewStatus);
            buttonAssign = itemView.findViewById(R.id.buttonAssign);
        }
    }
} 