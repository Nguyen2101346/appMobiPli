package com.example.utilitycalendar.alarm;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import androidx.annotation.Nullable;

import com.example.utilitycalendar.R;

public class SoundService extends Service {

    private MediaPlayer mediaPlayer;

    @Override
    public void onCreate() {
        super.onCreate();
        // Khởi tạo MediaPlayer để phát âm thanh
        mediaPlayer = MediaPlayer.create(this, R.raw.sound); // Đảm bảo tệp âm thanh đúng
        mediaPlayer.setLooping(false);  // Âm thanh phát một lần
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();  // Phát âm thanh
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();  // Dừng âm thanh khi service bị hủy
            mediaPlayer.release();  // Giải phóng tài nguyên
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
