package com.example.utilitycalendar.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class StopMusicReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Dừng SoundService khi người dùng nhấn vào thông báo
        Intent serviceIntent = new Intent(context, SoundService.class);
        context.stopService(serviceIntent);  // Dừng Service phát âm thanh
    }
}
