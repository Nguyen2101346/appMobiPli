package com.example.utilitycalendar.CreateNote;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.utilitycalendar.R;

public class CreateNoteActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_note);

//        EditText editTextTitle = findViewById(R.id.editTextTitle);
//        EditText editTextMessage = findViewById(R.id.editTextMessage);
//        Button buttonSaveNoti = findViewById(R.id.buttonSaveNoti);
//
//        buttonSaveNoti.setOnClickListener(v -> {
//            // Xử lý khi người dùng nhấn nút "Save Notification"
//            String title = editTextTitle.getText().toString();
//            String message = editTextMessage.getText().toString();
//
//            // Tiến hành lưu thông báo hoặc xử lý gì đó
//            // Ví dụ: lưu vào database hoặc hiển thị một thông báo
//
//            // Quay lại MainActivity sau khi lưu thông báo
//            finish();  // Hoặc có thể gọi một Intent để chuyển trang khác
//        });
    }
}
