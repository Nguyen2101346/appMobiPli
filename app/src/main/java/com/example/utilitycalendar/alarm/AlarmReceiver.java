package com.example.utilitycalendar.alarm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import com.example.utilitycalendar.R;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Bắt đầu SoundService để phát âm thanh
        Intent serviceIntent = new Intent(context, SoundService.class);
        context.startService(serviceIntent);  // Khởi động Service phát âm thanh

        // Lấy dữ liệu thông báo từ intent nếu cần
        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");

        // Tạo NotificationCompat.Builder trong AlarmReceiver
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId = "default_channel";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Default Channel",
                    NotificationManager.IMPORTANCE_HIGH
            );
            notificationManager.createNotificationChannel(channel);
        }

        // Tạo thông báo với PendingIntent để dừng nhạc khi nhấn vào thông báo
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, new Intent(context, StopMusicReceiver.class), PendingIntent.FLAG_UPDATE_CURRENT);

        // Tạo thông báo
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, channelId)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(R.drawable.ic_calendar)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);  // Thiết lập PendingIntent cho sự kiện nhấn vào thông báo

        // Hiển thị thông báo
        notificationManager.notify(0, notificationBuilder.build());
    }






}
