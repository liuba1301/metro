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

public class EmployeesFragment extends Fragment {
    private RecyclerView recyclerView;
    private EmployeesAdapter adapter;
    private List<Employee> employees = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_employees, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewEmployees);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        loadEmployees();
        adapter = new EmployeesAdapter(employees, emp -> showStatusDialog(emp));
        recyclerView.setAdapter(adapter);
        return view;
    }

    private void loadEmployees() {
        employees.clear();
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery("SELECT * FROM Employee", null);
        if (cursor.moveToFirst()) {
            do {
                Employee emp = new Employee(
                        cursor.getString(cursor.getColumnIndexOrThrow("id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("name")),
                        cursor.getString(cursor.getColumnIndexOrThrow("phone")),
                        cursor.getString(cursor.getColumnIndexOrThrow("email"))
                );
                emp.status = cursor.getString(cursor.getColumnIndexOrThrow("status"));
                employees.add(emp);
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    private void showStatusDialog(Employee employee) {
        String[] statuses = {"Работает", "Отпуск", "Больничный", "Чрезвычайная ситуация"};
        new AlertDialog.Builder(getContext())
                .setTitle("Изменить статус сотрудника")
                .setItems(statuses, (dialog, which) -> {
                    String newStatus = statuses[which];
                    updateEmployeeStatus(employee.id, newStatus);
                })
                .setNegativeButton("Отмена", null)
                .show();
    }

    private void updateEmployeeStatus(String employeeId, String newStatus) {
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        ContentValues values = new ContentValues();
        values.put("status", newStatus);
        dbHelper.getWritableDatabase().update("Employee", values, "id=?", new String[]{employeeId});
        if (newStatus.equals("Чрезвычайная ситуация") || newStatus.equals("Отпуск") || newStatus.equals("Больничный")) {
            // Найти все заявки, где этот сотрудник назначен
            Cursor cursor = dbHelper.getReadableDatabase().rawQuery(
                "SELECT applicationId FROM EmployeeApplication WHERE employeeId=?", new String[]{employeeId});
            int count = 0;
            while (cursor.moveToNext()) {
                String appId = cursor.getString(cursor.getColumnIndexOrThrow("applicationId"));
                // Удалить связь сотрудника с заявкой
                dbHelper.getWritableDatabase().delete("EmployeeApplication", "employeeId=? AND applicationId=?", new String[]{employeeId, appId});
                // Проверить сколько сотрудников осталось у заявки
                Cursor c2 = dbHelper.getReadableDatabase().rawQuery(
                    "SELECT COUNT(*) as cnt FROM EmployeeApplication WHERE applicationId=?", new String[]{appId});
                int assignedCount = 0;
                if (c2.moveToFirst()) assignedCount = c2.getInt(c2.getColumnIndexOrThrow("cnt"));
                c2.close();
                // Получить mobilityType заявки
                Cursor c3 = dbHelper.getReadableDatabase().rawQuery(
                    "SELECT mobilityType FROM Application WHERE id=?", new String[]{appId});
                String mobilityType = "";
                if (c3.moveToFirst()) mobilityType = c3.getString(c3.getColumnIndexOrThrow("mobilityType"));
                c3.close();
                int min = 1;
                if (mobilityType.equalsIgnoreCase("Слабовидящий")) min = 1;
                else if (mobilityType.equalsIgnoreCase("Слабовидящий с багажом")) min = 2;
                else if (mobilityType.equalsIgnoreCase("Колясочник")) min = 2;
                // Если сотрудников стало меньше минимума — статус заявки 'СРОЧНОЕ НАЗНАЧЕНИЕ'
                if (assignedCount < min) {
                    ContentValues appValues = new ContentValues();
                    appValues.put("status", "СРОЧНОЕ НАЗНАЧЕНИЕ");
                    dbHelper.getWritableDatabase().update("Application", appValues, "id=?", new String[]{appId});
                    Toast.makeText(getContext(), "СРОЧНОЕ НАЗНАЧЕНИЕ: требуется назначить сотрудника на заявку " + appId, Toast.LENGTH_LONG).show();
                }
                count++;
            }
            cursor.close();
            if (count > 0) {
                Toast.makeText(getContext(), "Сотрудник снят с " + count + " заявок", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getContext(), "Статус изменён", Toast.LENGTH_SHORT).show();
        }
        loadEmployees();
        adapter.notifyDataSetChanged();
    }
} 