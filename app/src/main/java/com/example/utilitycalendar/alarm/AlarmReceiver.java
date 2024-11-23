package com.example.utilitycalendar.alarm;

import static com.example.utilitycalendar.MainActivity.CHANNEL_ID;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.utilitycalendar.R;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        //Lấy nhạc
//        Uri soundUri = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.my_sound);



        // Lấy dữ liệu thông báo từ intent nếu cần
        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");

        // Tạo NotificationCompat.Builder trong AlarmReceiver
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Tạo Notification nếu chưa có NotificationChannel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Default Channel";
            String description = "This is the default notification channel.";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("default_channel", name, importance);
            channel.setDescription(description);
            notificationManager.createNotificationChannel(channel);
        }

        // Tạo NotificationCompat.Builder
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, "default_channel")
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(R.drawable.ic_calendar)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);
        //.setSound(soundUri);

        // Hiển thị thông báo
        notificationManager.notify(0, notificationBuilder.build()); // Sử dụng ID thông báo tùy ý
    }

}