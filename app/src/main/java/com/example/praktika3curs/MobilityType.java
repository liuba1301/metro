package com.example.praktika3curs;

// Типы маломобильности пассажиров
public class MobilityType {
    // Константы для типов маломобильности
    public static final String VISUALLY_IMPAIRED = "VISUALLY_IMPAIRED"; // Слабовидящие
    public static final String HEARING_IMPAIRED = "HEARING_IMPAIRED";   // Слабослышащие
    public static final String WHEELCHAIR = "WHEELCHAIR";               // Инвалидная коляска
    public static final String ELDERLY = "ELDERLY";                     // Пожилые люди
    public static final String TEMPORARY_DISABILITY = "TEMPORARY_DISABILITY"; // Временная инвалидность
        
    // Приватный конструктор, чтобы предотвратить создание экземпляров класса
    private MobilityType() {
    }
} 