package com.example.praktika3curs;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class PassengerActivity extends AppCompatActivity {
    private EditText editTextFullName, editTextPhone;
    private Spinner spinnerMobilityType, spinnerStartStation, spinnerEndStation;
    private Button buttonSelectDate, buttonSelectTime, buttonSubmitApplication;
    private String selectedDate = "", selectedTime = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_mode);

        editTextFullName = findViewById(R.id.editTextFullName);
        editTextPhone = findViewById(R.id.editTextPhone);
        spinnerMobilityType = findViewById(R.id.spinnerMobilityType);
        spinnerStartStation = findViewById(R.id.spinnerStartStation);
        spinnerEndStation = findViewById(R.id.spinnerEndStation);
        buttonSelectDate = findViewById(R.id.buttonSelectDate);
        buttonSelectTime = findViewById(R.id.buttonSelectTime);
        buttonSubmitApplication = findViewById(R.id.buttonSubmitApplication);

        // Здесь можно добавить выбор даты и времени через DatePicker/TimePicker
        // Для простоты сейчас просто сохраняем текст
        buttonSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: реализовать выбор даты
                selectedDate = "2024-06-10";
                buttonSelectDate.setText(selectedDate);
            }
        });
        buttonSelectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: реализовать выбор времени
                selectedTime = "12:00";
                buttonSelectTime.setText(selectedTime);
            }
        });

        buttonSubmitApplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = editTextFullName.getText().toString();
                String phone = editTextPhone.getText().toString();
                String mobilityType = spinnerMobilityType.getSelectedItem().toString();
                String startStation = spinnerStartStation.getSelectedItem().toString();
                String endStation = spinnerEndStation.getSelectedItem().toString();
                String meetingTime = selectedDate + " " + selectedTime;
                String status = "PENDING";
                String id = "APP_" + System.currentTimeMillis();

                DatabaseHelper dbHelper = new DatabaseHelper(PassengerActivity.this);
                dbHelper.getWritableDatabase().execSQL(
                        "INSERT INTO Application (id, passengerName, passengerPhone, mobilityType, startStation, endStation, meetingTime, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                        new Object[]{id, fullName, phone, mobilityType, startStation, endStation, meetingTime, status}
                );
                Toast.makeText(PassengerActivity.this, "Заявка отправлена!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
} 