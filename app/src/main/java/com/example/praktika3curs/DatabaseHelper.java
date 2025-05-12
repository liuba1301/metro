package com.example.praktika3curs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "praktika3curs.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_EMPLOYEE = "Employee";
    public static final String TABLE_APPLICATION = "Application";
    public static final String TABLE_EMPLOYEE_APPLICATION = "EmployeeApplication";
    public static final String TABLE_WORKSHIFT = "WorkShift";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_EMPLOYEE + " (" +
                "id TEXT PRIMARY KEY," +
                "name TEXT," +
                "phone TEXT," +
                "email TEXT," +
                "status TEXT" +
                ");");

        db.execSQL("CREATE TABLE " + TABLE_APPLICATION + " (" +
                "id TEXT PRIMARY KEY," +
                "passengerName TEXT," +
                "passengerPhone TEXT," +
                "mobilityType TEXT," +
                "startStation TEXT," +
                "endStation TEXT," +
                "meetingTime TEXT," +
                "status TEXT" +
                ");");

        db.execSQL("CREATE TABLE " + TABLE_EMPLOYEE_APPLICATION + " (" +
                "employeeId TEXT," +
                "applicationId TEXT," +
                "PRIMARY KEY(employeeId, applicationId)," +
                "FOREIGN KEY(employeeId) REFERENCES " + TABLE_EMPLOYEE + "(id)," +
                "FOREIGN KEY(applicationId) REFERENCES " + TABLE_APPLICATION + "(id)" +
                ");");

        db.execSQL("CREATE TABLE " + TABLE_WORKSHIFT + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "employeeId TEXT," +
                "date TEXT," +
                "startTime TEXT," +
                "endTime TEXT," +
                "station TEXT," +
                "FOREIGN KEY(employeeId) REFERENCES " + TABLE_EMPLOYEE + "(id)" +
                ");");

        // --- Список сотрудников (30 человек) ---
        db.execSQL("INSERT INTO Employee (id, name, phone, email, status) VALUES " +
                "('EMP_1', 'Иванов Иван Иванович', '+7 999 111-11-01', 'ivanov1@mail.ru', 'Работает')," +
                "('EMP_2', 'Петров Петр Петрович', '+7 999 111-11-02', 'petrov2@mail.ru', 'Работает')," +
                "('EMP_3', 'Сидорова Мария Сергеевна', '+7 999 111-11-03', 'sidorova3@mail.ru', 'Работает')," +
                "('EMP_4', 'Кузнецова Анна Владимировна', '+7 999 111-11-04', 'kuznetsova4@mail.ru', 'Работает')," +
                "('EMP_5', 'Васильев Алексей Олегович', '+7 999 111-11-05', 'vasiliev5@mail.ru', 'Работает')," +
                "('EMP_6', 'Морозов Дмитрий Павлович', '+7 999 111-11-06', 'morozov6@mail.ru', 'Работает')," +
                "('EMP_7', 'Смирнова Ольга Николаевна', '+7 999 111-11-07', 'smirnova7@mail.ru', 'Работает')," +
                "('EMP_8', 'Попов Михаил Андреевич', '+7 999 111-11-08', 'popov8@mail.ru', 'Работает')," +
                "('EMP_9', 'Волкова Екатерина Юрьевна', '+7 999 111-11-09', 'volkova9@mail.ru', 'Работает')," +
                "('EMP_10', 'Соколов Артем Сергеевич', '+7 999 111-11-10', 'sokolov10@mail.ru', 'Работает')," +
                "('EMP_11', 'Лебедев Игорь Владимирович', '+7 999 111-11-11', 'lebedev11@mail.ru', 'Работает')," +
                "('EMP_12', 'Козлова Наталья Ивановна', '+7 999 111-11-12', 'kozlova12@mail.ru', 'Работает')," +
                "('EMP_13', 'Новиков Павел Алексеевич', '+7 999 111-11-13', 'novikov13@mail.ru', 'Работает')," +
                "('EMP_14', 'Медведева Светлана Петровна', '+7 999 111-11-14', 'medvedeva14@mail.ru', 'Работает')," +
                "('EMP_15', 'Федоров Денис Евгеньевич', '+7 999 111-11-15', 'fedorov15@mail.ru', 'Работает')," +
                "('EMP_16', 'Борисова Ирина Дмитриевна', '+7 999 111-11-16', 'borisova16@mail.ru', 'Работает')," +
                "('EMP_17', 'Григорьев Максим Олегович', '+7 999 111-11-17', 'grigorev17@mail.ru', 'Работает')," +
                "('EMP_18', 'Степанова Елена Сергеевна', '+7 999 111-11-18', 'stepanova18@mail.ru', 'Работает')," +
                "('EMP_19', 'Алексеев Виктор Павлович', '+7 999 111-11-19', 'alekseev19@mail.ru', 'Работает')," +
                "('EMP_20', 'Михайлова Татьяна Владимировна', '+7 999 111-11-20', 'mikhailova20@mail.ru', 'Работает')," +
                "('EMP_21', 'Захаров Андрей Николаевич', '+7 999 111-11-21', 'zakharov21@mail.ru', 'Работает')," +
                "('EMP_22', 'Орлова Мария Алексеевна', '+7 999 111-11-22', 'orlova22@mail.ru', 'Работает')," +
                "('EMP_23', 'Тимофеев Сергей Игоревич', '+7 999 111-11-23', 'timofeev23@mail.ru', 'Работает')," +
                "('EMP_24', 'Павлова Оксана Дмитриевна', '+7 999 111-11-24', 'pavlova24@mail.ru', 'Работает')," +
                "('EMP_25', 'Гусев Владимир Сергеевич', '+7 999 111-11-25', 'gusev25@mail.ru', 'Работает')," +
                "('EMP_26', 'Дмитриева Анна Павловна', '+7 999 111-11-26', 'dmitrieva26@mail.ru', 'Работает')," +
                "('EMP_27', 'Беляев Евгений Алексеевич', '+7 999 111-11-27', 'belyaev27@mail.ru', 'Работает')," +
                "('EMP_28', 'Комарова Юлия Викторовна', '+7 999 111-11-28', 'komarova28@mail.ru', 'Работает')," +
                "('EMP_29', 'Киселев Артем Сергеевич', '+7 999 111-11-29', 'kiselev29@mail.ru', 'Работает')," +
                "('EMP_30', 'Семенова Валентина Игоревна', '+7 999 111-11-30', 'semenova30@mail.ru', 'Работает')"
        );

        // --- Расписание на каждый день недели для каждого сотрудника (7 станций) ---
        String[] stations = {"Киевская", "Парк Культуры", "Таганская", "ВДНХ", "Тверская", "Арбатская", "Курская"};
        for (int emp = 1; emp <= 30; emp++) {
            for (int day = 0; day < 7; day++) {
                String empId = "EMP_" + emp;
                String date = "2024-06-" + String.format("%02d", 10 + day); // 10-16 июня
                String station = stations[(emp + day) % stations.length];
                db.execSQL("INSERT INTO WorkShift (employeeId, date, startTime, endTime, station) VALUES ('" + empId + "', '" + date + "', '08:00', '17:00', '" + station + "')");
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEE_APPLICATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_APPLICATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORKSHIFT);
        onCreate(db);
    }
} 