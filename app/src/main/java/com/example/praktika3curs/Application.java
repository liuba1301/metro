package com.example.praktika3curs;

import java.time.LocalDateTime;
import androidx.annotation.NonNull;


public class Application {

    @NonNull
    public String id;
    public String passengerName;     // Имя пассажира
    public String passengerPhone;    // Телефон пассажира
    public String mobilityType;      // Тип маломобильности
    public String startStation;      // Станция начала
    public String endStation;        // Станция окончания
    public String meetingTime;        // Время встречи
    public String status;            // Статус заявки: "PENDING", "ASSIGNED", "COMPLETED", "CANCELLED"
    public String assignedEmployeeId; // Назначенный сотрудник
    
    public Application(String id, String passengerName, String passengerPhone, 
                      String mobilityType, Station startStation, Station endStation, 
                      LocalDateTime meetingTime) {
        this.id = id;
        this.passengerName = passengerName;
        this.passengerPhone = passengerPhone;
        this.mobilityType = mobilityType;
        this.startStation = startStation.getName();
        this.endStation = endStation.getName();
        this.meetingTime = meetingTime.toString();
        this.status = "PENDING"; // По умолчанию заявка ожидает рассмотрения
        this.assignedEmployeeId = null;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getPassengerName() {
        return passengerName;
    }
    
    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }
    
    public String getPassengerPhone() {
        return passengerPhone;
    }
    
    public void setPassengerPhone(String passengerPhone) {
        this.passengerPhone = passengerPhone;
    }
    
    public String getMobilityType() {
        return mobilityType;
    }
    
    public void setMobilityType(String mobilityType) {
        this.mobilityType = mobilityType;
    }
    
    public Station getStartStation() {
        return new Station(startStation, "", "", "");
    }
    
    public void setStartStation(Station startStation) {
        this.startStation = startStation.getName();
    }
    
    public Station getEndStation() {
        return new Station(endStation, "", "", "");
    }
    
    public void setEndStation(Station endStation) {
        this.endStation = endStation.getName();
    }
    
    public LocalDateTime getMeetingTime() {
        return LocalDateTime.parse(meetingTime);
    }
    
    public void setMeetingTime(LocalDateTime meetingTime) {
        this.meetingTime = meetingTime.toString();
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getAssignedEmployeeId() {
        return assignedEmployeeId;
    }
    
    public void setAssignedEmployeeId(String assignedEmployeeId) {
        this.assignedEmployeeId = assignedEmployeeId;
        if (assignedEmployeeId != null) {
            this.status = "ASSIGNED"; // Автоматически изменяем статус при назначении сотрудника
        }
    }
    
    // Метод для завершения заявки
    public void complete() {
        this.status = "COMPLETED";
    }
    
    // Метод для отмены заявки
    public void cancel() {
        this.status = "CANCELLED";
        this.assignedEmployeeId = null;
    }
    
    // Генерация ID для новой заявки
    public static String generateId() {
        return "APP_" + System.currentTimeMillis();
    }
    
    @Override
    public String toString() {
        return "Application{" +
                "id='" + id + '\'' +
                ", passengerName='" + passengerName + '\'' +
                ", mobilityType='" + mobilityType + '\'' +
                ", from=" + startStation +
                ", to=" + endStation +
                ", time=" + meetingTime +
                ", status='" + status + '\'' +
                '}';
    }
}
