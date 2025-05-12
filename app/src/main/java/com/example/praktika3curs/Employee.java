package com.example.praktika3curs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import androidx.annotation.NonNull;


public class Employee {
    @NonNull
    public String id;              // Идентификатор сотрудника
    public String name;            // ФИО сотрудника
    public String phone;           // Номер телефона
    public String email;           // Адрес электронной почты
    public boolean isAvailable;    // Доступен ли сотрудник
    private List<WorkShift> workSchedule;
    private List<String> workStations; // Станции, на которых сотрудник может работать
    public String status;          // Статус (работает, отпуск, больничный и т.д.)
    
    // Конструктор с основными данными
    public Employee(String id, String name) {
        this.id = id;
        this.name = name;
        this.isAvailable = true;
        this.workSchedule = new ArrayList<>();
        this.workStations = new ArrayList<>();
        this.status = "Available";
    }
    
    // Расширенный конструктор со всеми данными
    public Employee(String id, String name, String phone, String email) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.isAvailable = true;
        this.workSchedule = new ArrayList<>();
        this.workStations = new ArrayList<>();
        this.status = "Available";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public List<WorkShift> getWorkSchedule() {
        return workSchedule;
    }

    public void setWorkSchedule(List<WorkShift> workSchedule) {
        this.workSchedule = workSchedule;
    }

    public void addWorkShift(WorkShift shift) {
        this.workSchedule.add(shift);
    }

    public List<String> getWorkStations() {
        return workStations;
    }

    public void setWorkStations(List<String> workStations) {
        this.workStations = workStations;
    }

    public void addWorkStation(String station) {
        if (!this.workStations.contains(station)) {
            this.workStations.add(station);
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        // Если сотрудник в отпуске или на больничном, он не доступен
        if (status.equals("Vacation") || status.equals("Sick leave")) {
            this.isAvailable = false;
        }
    }
    
    // Генерация ID для нового сотрудника
    public static String generateId() {
        return "EMP_" + System.currentTimeMillis();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", isAvailable=" + isAvailable +
                ", status='" + status + '\'' +
                '}';
    }
}
