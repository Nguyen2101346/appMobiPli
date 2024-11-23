package com.example.utilitycalendar;

import static java.security.AccessController.getContext;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.utilitycalendar.Database.Database;
import com.example.utilitycalendar.Database.Notification;
import com.example.utilitycalendar.alarm.AlarmReceiver;
import com.example.utilitycalendar.home.DayHomeFragment;
import com.example.utilitycalendar.note.CategoryNoteFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    public static String CHANNEL_ID = "default channel";

    public static Database appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {;


        super.onCreate(savedInstanceState);




        setContentView(R.layout.menu);

        appDatabase = Database.getInstance(getApplicationContext());


        //thong bao

        createNotificationChannel();

        // Hiển thị danh sách thông báo
        Executor executorMain = Executors.newSingleThreadExecutor();
        executorMain.execute(() -> {
            // Lấy danh sách thông báo từ cơ sở dữ liệu
            List<Notification> notificationList = appDatabase.notificationDao().getAllNotifications();

            // Lặp qua danh sách thông báo
            for (Notification notification : notificationList) {
                // Truyền từng Notification vào hàm showNotificationList
                showNotificationList(notification);
            }
        });





        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            if(item.getItemId() == R.id.nav_home) {
                selectedFragment = new DayHomeFragment();
                getSupportActionBar().setTitle("Home");
            }
            if(item.getItemId() == R.id.nav_note) {
                selectedFragment = new CategoryNoteFragment();
                getSupportActionBar().setTitle("Note");
            }
            if(item.getItemId() == R.id.nav_flag) {
                selectedFragment = new FlagFragment();
                getSupportActionBar().setTitle("Flag");
            }
            if(item.getItemId() == R.id.nav_setting) {
                selectedFragment = new SettingFragment();
                getSupportActionBar().setTitle("Setting");
            }


            // Thay thế fragment tương ứng trong FrameLayout
            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, selectedFragment)
                        .commit();
            }

            return true;
        });

        // Load fragment mặc định
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
    }

    // Phương thức tạo NotificationChannel cho Android O trở lên
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Default Channel";
            String description = "This is the default notification channel.";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    // Phương thức để hiển thị danh sách các thông báo
    private void showNotificationList(Notification notification) {
        AlarmManager alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);

        // Gửi thông tin thông báo vào Intent
        intent.putExtra("title", notification.getTittle());
        intent.putExtra("content", notification.getDetails());

        // PendingIntent để thực thi AlarmReceiver
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, notification.getNoti_id(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Thiết lập thời gian báo thức
        long notificationTimeMillis = notification.getNotiDate().getTime();
        alarmMgr.setExact(AlarmManager.RTC_WAKEUP, notificationTimeMillis, pendingIntent);



    }


}
