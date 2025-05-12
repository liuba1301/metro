package com.example.praktika3curs;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ApplicationsFragment extends Fragment {
    private RecyclerView recyclerView;
    private ApplicationsAdapter adapter;
    private List<Application> applications = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_applications, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewApplications);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        loadApplications();
        adapter = new ApplicationsAdapter(applications, app -> showAssignEmployeeDialog(app));
        recyclerView.setAdapter(adapter);
        return view;
    }

    private void loadApplications() {
        applications.clear();
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery("SELECT * FROM Application", null);
        if (cursor.moveToFirst()) {
            do {
                Application app = new Application(
                        cursor.getString(cursor.getColumnIndexOrThrow("id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("passengerName")),
                        cursor.getString(cursor.getColumnIndexOrThrow("passengerPhone")),
                        cursor.getString(cursor.getColumnIndexOrThrow("mobilityType")),
                        new Station(cursor.getString(cursor.getColumnIndexOrThrow("startStation")), "", "", ""),
                        new Station(cursor.getString(cursor.getColumnIndexOrThrow("endStation")), "", "", ""),
                        java.time.LocalDateTime.parse(cursor.getString(cursor.getColumnIndexOrThrow("meetingTime")))
                );
                app.status = cursor.getString(cursor.getColumnIndexOrThrow("status"));
                app.assignedEmployeeId = cursor.getString(cursor.getColumnIndexOrThrow("assignedEmployeeId"));
                applications.add(app);
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    private void showAssignEmployeeDialog(Application application) {
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery("SELECT id, name FROM Employee WHERE status = 'Работает'", null);
        List<String> employeeNames = new ArrayList<>();
        List<String> employeeIds = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                employeeIds.add(cursor.getString(cursor.getColumnIndexOrThrow("id")));
                employeeNames.add(cursor.getString(cursor.getColumnIndexOrThrow("name")));
            } while (cursor.moveToNext());
        }
        cursor.close();
        if (employeeNames.isEmpty()) {
            Toast.makeText(getContext(), "Нет доступных сотрудников", Toast.LENGTH_SHORT).show();
            return;
        }
        // Определяем лимит по типу маломобильности
        final int min, max;
        if (application.getMobilityType().equalsIgnoreCase("Слабовидящий")) {
            min = max = 1;
        } else if (application.getMobilityType().equalsIgnoreCase("Слабовидящий с багажом")) {
            min = max = 2;
        } else if (application.getMobilityType().equalsIgnoreCase("Колясочник")) {
            min = 2; max = 4;
        } else {
            min = max = 1;
        }
        boolean[] checked = new boolean[employeeNames.size()];
        new AlertDialog.Builder(getContext())
                .setTitle("Назначить сотрудников")
                .setMultiChoiceItems(employeeNames.toArray(new String[0]), checked, (dialog, which, isChecked) -> {})
                .setPositiveButton("Назначить", (dialog, which) -> {
                    List<String> selectedIds = new ArrayList<>();
                    for (int i = 0; i < checked.length; i++) {
                        if (checked[i]) selectedIds.add(employeeIds.get(i));
                    }
                    if (selectedIds.size() < min || selectedIds.size() > max) {
                        Toast.makeText(getContext(), "Выберите от " + min + " до " + max + " сотрудников", Toast.LENGTH_LONG).show();
                        return;
                    }
                    assignEmployeesToApplication(selectedIds, application.id);
                })
                .setNegativeButton("Отмена", null)
                .show();
    }

    private void assignEmployeesToApplication(List<String> employeeIds, String applicationId) {
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        // Удаляем старые связи
        dbHelper.getWritableDatabase().delete("EmployeeApplication", "applicationId=?", new String[]{applicationId});
        // Добавляем новые связи
        for (String employeeId : employeeIds) {
            ContentValues values = new ContentValues();
            values.put("employeeId", employeeId);
            values.put("applicationId", applicationId);
            dbHelper.getWritableDatabase().insert("EmployeeApplication", null, values);
        }
        // Обновляем статус заявки и назначенных сотрудников
        ContentValues appValues = new ContentValues();
        appValues.put("status", "ASSIGNED");
        appValues.put("assignedEmployeeId", employeeIds.get(0)); // для совместимости, если нужно
        dbHelper.getWritableDatabase().update("Application", appValues, "id=?", new String[]{applicationId});
        Toast.makeText(getContext(), "Сотрудники назначены!", Toast.LENGTH_SHORT).show();
        loadApplications();
        adapter.notifyDataSetChanged();
    }
} 