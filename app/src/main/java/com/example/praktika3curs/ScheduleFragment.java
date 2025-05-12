package com.example.praktika3curs;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ScheduleFragment extends Fragment {
    private RecyclerView recyclerView;
    private ScheduleAdapter adapter;
    private List<WorkShift> shifts = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewSchedule);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        loadShifts();
        adapter = new ScheduleAdapter(shifts);
        recyclerView.setAdapter(adapter);
        return view;
    }

    private void loadShifts() {
        shifts.clear();
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery("SELECT * FROM WorkShift", null);
        if (cursor.moveToFirst()) {
            do {
                String employeeId = cursor.getString(cursor.getColumnIndexOrThrow("employeeId"));
                Employee employee = getEmployeeById(employeeId, dbHelper);
                WorkShift shift = new WorkShift(
                        cursor.getString(cursor.getColumnIndexOrThrow("id")),
                        employee,
                        java.time.LocalDate.parse(cursor.getString(cursor.getColumnIndexOrThrow("date"))),
                        java.time.LocalTime.parse(cursor.getString(cursor.getColumnIndexOrThrow("startTime"))),
                        java.time.LocalTime.parse(cursor.getString(cursor.getColumnIndexOrThrow("endTime"))),
                        new Station(cursor.getString(cursor.getColumnIndexOrThrow("station")), "", "", "")
                );
                shifts.add(shift);
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    private Employee getEmployeeById(String id, DatabaseHelper dbHelper) {
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery("SELECT * FROM Employee WHERE id=?", new String[]{id});
        Employee emp = null;
        if (cursor.moveToFirst()) {
            emp = new Employee(
                    cursor.getString(cursor.getColumnIndexOrThrow("id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("name")),
                    cursor.getString(cursor.getColumnIndexOrThrow("phone")),
                    cursor.getString(cursor.getColumnIndexOrThrow("email"))
            );
            emp.status = cursor.getString(cursor.getColumnIndexOrThrow("status"));
        }
        cursor.close();
        return emp;
    }
} 