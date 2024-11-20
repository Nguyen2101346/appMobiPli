package com.example.utilitycalendar.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NotificationDao {
    @Insert
    void insertNotification(Notification notification);

    @Update
    void updateNotification(Notification notification);

    @Delete
    void deleteNotification(Notification notification);

    @Query("SELECT * FROM Notification WHERE Noti_id = :id")
    Notification getNotificationById(int id);

    @Query("SELECT * FROM Notification")
    List<Notification> getAllNotifications();
}
