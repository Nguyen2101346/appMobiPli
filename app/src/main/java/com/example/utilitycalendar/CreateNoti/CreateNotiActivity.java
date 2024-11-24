package com.example.utilitycalendar.CreateNoti;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.utilitycalendar.R;

public class CreateNotiActivity extends AppCompatActivity {
    private EditText editTextName, editTextDetails, editTextDate, editTextTime;
    private ImageView notiIcon;
    private String color = "#FFFFFF"; // Màu mặc định, thay đổi theo ColorManager của bạn
    private int reminder = 1; // Giá trị mặc định của reminder

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_noti);

        // Khởi tạo các view
        editTextName = findViewById(R.id.editTextName);
        editTextDetails = findViewById(R.id.editTextDetails);
        editTextDate = findViewById(R.id.editTextDate);
        editTextTime = findViewById(R.id.editTextTime);
        notiIcon = findViewById(R.id.notiIcon);

        // Kiểm tra trạng thái của icon thông báo
        if (notiIcon.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.ic_unnoti_45).getConstantState()) {
            reminder = 0; // Không có reminder
        }

        // Logic cho nút lưu
        ImageView btnSave = findViewById(R.id.btn_Save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNotification();
            }
        });
    }

    private void saveNotification() {
        String title = editTextName.getText().toString();
        String details = editTextDetails.getText().toString();
        String notiDate = editTextDate.getText().toString();
        String notiTime = editTextTime.getText().toString();

        // Lưu vào cơ sở dữ liệu
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        dbHelper.addNotification(title, details, color, reminder, notiDate, notiTime);
    }
}
