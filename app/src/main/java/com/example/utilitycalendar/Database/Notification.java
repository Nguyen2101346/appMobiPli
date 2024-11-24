package com.example.utilitycalendar.Database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;
import androidx.room.Index;
import java.util.Date;

@Entity(tableName = "Notification", indices = {@Index(value = "Noti_id", unique = true)})
public class Notification {
    @PrimaryKey(autoGenerate = true)
    public int Noti_id;
    public String Tittle;
    public String Details;
    public int Color;
    public int Reminder;

    @ColumnInfo(name = "Noti_Date")
    public Date NotiDate;

    @ColumnInfo(name = "Noti_Time")
    public Date NotiTime;

    public String Ring_Name;
}

