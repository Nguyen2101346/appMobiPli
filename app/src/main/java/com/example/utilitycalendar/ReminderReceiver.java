package com.example.utilitycalendar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class ReminderReceiver extends BroadcastReceiver {

    private static final String CHANNEL_ID = "reminder_channel";

    @Override
    public void onReceive(Context context, Intent intent) {
        createNotificationChannel(context);

        // Lấy tiêu đề nhắc nhở từ Intent
        String title = intent.getStringExtra("title");
        if (title == null || title.isEmpty()) {
            title = "Nhắc nhở";  // Tiêu đề mặc định nếu không có tiêu đề tùy chỉnh
        }

        // Tạo và hiển thị thông báo
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)  // Sử dụng tiêu đề tùy chỉnh ở đây
                .setContentText("Đã đến giờ bạn đặt nhắc nhở!")
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(1, builder.build());
    }

    // Tạo kênh thông báo (cần thiết cho Android O trở lên)
    private void createNotificationChannel(Context context) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            CharSequence name = "Reminder Channel";
            String description = "Channel for reminders";
            int importance = NotificationManagerCompat.IMPORTANCE_HIGH;
            android.app.NotificationChannel channel = new android.app.NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            android.app.NotificationManager notificationManager = context.getSystemService(android.app.NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }
}
