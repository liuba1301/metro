package com.example.praktika3curs;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

// Класс для хранения информации о рабочих сменах сотрудников
public class WorkShift {
    private String id;                 // Идентификатор смены
    private Employee employee;         // Сотрудник, работающий в данную смену
    private LocalDate date;            // Дата смены
    private LocalTime startTime;       // Время начала смены
    private LocalTime endTime;         // Время окончания смены
    private Station station;           // Станция, на которой сотрудник работает в эту смену
    
    public WorkShift(String id, Employee employee, LocalDate date, LocalTime startTime, LocalTime endTime, Station station) {
        this.id = id;
        this.employee = employee;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.station = station;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public Employee getEmployee() {
        return employee;
    }
    
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    
    public String getEmployeeId() {
        return employee != null ? employee.getId() : null;
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    public LocalTime getStartTime() {
        return startTime;
    }
    
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }
    
    public LocalTime getEndTime() {
        return endTime;
    }
    
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
    
    public Station getStation() {
        return station;
    }
    
    public void setStation(Station station) {
        this.station = station;
    }
    
    // Проверка, перекрывается ли данная смена с указанным временем
    public boolean containsDateTime(LocalDateTime dateTime) {
        LocalDate dt = dateTime.toLocalDate();
        LocalTime tm = dateTime.toLocalTime();
        
        // Сначала проверяем, совпадает ли дата
        if (!dt.equals(date)) {
            return false;
        }
        
        // Затем проверяем, попадает ли время в интервал смены
        return !tm.isBefore(startTime) && !tm.isAfter(endTime);
    }
    
    // Проверка, может ли сотрудник в эту смену выполнить заявку
    public boolean canHandleApplication(Application application) {
        // Проверяем, находится ли время встречи в рамках смены
        LocalDateTime meetingTime = application.getMeetingTime();
        if (!containsDateTime(meetingTime)) {
            return false;
        }
        
        // Проверяем, работает ли сотрудник на нужной станции
        return station.getId().equals(application.getStartStation().getId());
    }
    
    // Генерация ID для новой смены
    public static String generateId() {
        return "SHIFT_" + System.currentTimeMillis();
    }
    
    @Override
    public String toString() {
        return "WorkShift{" +
                "id='" + id + '\'' +
                ", employee=" + (employee != null ? employee.getName() : "null") +
                ", date=" + date +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", station=" + station.getName() +
                '}';
    }
} 