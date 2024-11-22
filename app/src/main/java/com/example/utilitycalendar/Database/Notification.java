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
    public String Color;
    public int Reminder;

    @ColumnInfo(name = "Noti_Date")
    public Date NotiDate;

    @ColumnInfo(name = "Noti_Time")
    public Date NotiTime;

    public String Ring_Name;


    public int getNoti_id() {
        return Noti_id;
    }

    public void setNoti_id(int noti_id) {
        Noti_id = noti_id;
    }

    public String getTittle() {
        return Tittle;
    }

    public void setTittle(String tittle) {
        Tittle = tittle;
    }

    public String getDetails() {
        return Details;
    }

    public void setDetails(String details) {
        Details = details;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public int getReminder() {
        return Reminder;
    }

    public void setReminder(int reminder) {
        Reminder = reminder;
    }

    public Date getNotiDate() {
        return NotiDate;
    }

    public void setNotiDate(Date notiDate) {
        NotiDate = notiDate;
    }

    public Date getNotiTime() {
        return NotiTime;
    }

    public void setNotiTime(Date notiTime) {
        NotiTime = notiTime;
    }

    public String getRing_Name() {
        return Ring_Name;
    }

    public void setRing_Name(String ring_Name) {
        Ring_Name = ring_Name;
    }



    public Notification(int noti_id, String tittle, String details, String color, int reminder, Date notiDate, Date notiTime, String ring_Name) {
        Noti_id = noti_id;
        Tittle = tittle;
        Details = details;
        Color = color;
        Reminder = reminder;
        NotiDate = notiDate;
        NotiTime = notiTime;
        Ring_Name = ring_Name;
    }

    public Notification(){

    }


}

