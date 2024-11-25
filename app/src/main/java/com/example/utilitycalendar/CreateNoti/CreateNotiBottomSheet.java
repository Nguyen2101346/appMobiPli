package com.example.utilitycalendar.CreateNoti;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.example.utilitycalendar.BottomSheetManager;
import com.example.utilitycalendar.Database.Database;
import com.example.utilitycalendar.Database.Notification;
import com.example.utilitycalendar.Helper.ColorManager;
import com.example.utilitycalendar.Helper.DatePickerHelper;
import com.example.utilitycalendar.Helper.TimePickerHelper;
import com.example.utilitycalendar.MainActivity;
import com.example.utilitycalendar.R;
import com.example.utilitycalendar.alarm.AlarmReceiver;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CreateNotiBottomSheet extends BottomSheetDialog {

    private DatePickerHelper datePickerHelper;
    private TimePickerHelper timePickerHelper;
    private EditText dateText;
    private EditText timeText;
    private BottomSheetManager bottomSheetManager;
    private String selectedColorHex = "#EB8585";
    private int reminder = 1;

    public interface OnSaveListener {
        void onSave(Noti newNotiData);
    }

    private OnSaveListener onSaveListener;

    public CreateNotiBottomSheet(Context context, DatePickerHelper datePickerHelper, TimePickerHelper timePickerHelper, BottomSheetManager bottomSheetManager) {
        super(context);
        this.datePickerHelper = datePickerHelper;
        this.timePickerHelper = timePickerHelper;
        this.bottomSheetManager = bottomSheetManager;
        setupLayout();
    }

    private void setupLayout() {

        View createNotiView = getLayoutInflater().inflate(R.layout.create_noti, null);
        setContentView(createNotiView);

        // Lấy các trường thông tin
        EditText editTextName = createNotiView.findViewById(R.id.editTextName);
        dateText = createNotiView.findViewById(R.id.editTextDate);
        timeText = createNotiView.findViewById(R.id.editTextTime);
        EditText editTextDetails = createNotiView.findViewById(R.id.editTextDetails);

        // Lấy Ngày
        datePickerHelper.initializeDatePicker(dateText);
        createNotiView.findViewById(R.id.btn_DatePicker).setOnClickListener(v -> datePickerHelper.showDatePicker());

        // Lấy giờ
        createNotiView.findViewById(R.id.btn_TimePicker).setOnClickListener(v -> timePickerHelper.openTimePicker(timeText));

        // Xử lý nút back
        createNotiView.findViewById(R.id.backButton).setOnClickListener(v -> {
            dismiss();
            bottomSheetManager.showMainBottomSheet();
        });

        ImageView notiIcon = createNotiView.findViewById(R.id.notiIcon);
        notiIcon.setOnClickListener(v -> toggleIcon(notiIcon));

        // Cài đặt ColorPicker
        setupColorPicker(createNotiView);

        // Xử lý nút Lưu
        ImageView btnSave = createNotiView.findViewById(R.id.btn_Save);
        btnSave.setOnClickListener(v -> {

            String name = editTextName.getText().toString();
            String date = dateText.getText().toString();
            String time = timeText.getText().toString();
            String details = editTextDetails.getText().toString();

            // Kiểm tra thông tin nhập vào
            if (name.isEmpty() || date.isEmpty() || time.isEmpty()) {
                Toast.makeText(getContext(), "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Định dạng ngày và giờ
            String dateInputFormat = "EEEE - dd/MM/yyyy";  // Định dạng ngày
            String timeInputFormat = "hh 'giờ' mm 'phút' a"; // Định dạng giờ (Sáng/Chiều)
            String dateOutputFormat = "dd-MM-yyyy";  // Định dạng ngày đầu ra
            String timeOutputFormat = "HH:mm";  // Định dạng giờ đầu ra
            Date Finaldate;

            SimpleDateFormat dateInputFormatter = new SimpleDateFormat(dateInputFormat,new Locale("vi", "VN"));
            SimpleDateFormat timeInputFormatter = new SimpleDateFormat(timeInputFormat,Locale.ENGLISH);
            SimpleDateFormat dateOutputFormatter = new SimpleDateFormat(dateOutputFormat,Locale.ENGLISH);
            SimpleDateFormat timeOutputFormatter = new SimpleDateFormat(timeOutputFormat,Locale.ENGLISH);

            try {
                // Làm sạch chuỗi ngày và giờ trước khi phân tích
                date = date.trim();
                time = time.trim();
                time = time.replace("Sáng", "AM").replace("Chiều", "PM");
//                Log.d("Time After Replace", "Giờ sau khi thay thế: " + time);
//                Log.d("Date After Replace", "Ngày sau khi thay thế: " + date);
                String formattedDate;
                String formattedTime;
                // Kiểm tra nếu định dạng ngày khớp với mẫu
                try {
                    Date Tdate = dateInputFormatter.parse(date);  // Kiểm tra ngày
                    formattedDate = dateOutputFormatter.format(Tdate);
                    Log.d("Fuck u","Chuỗi ngày đã chuyển đổi: " + formattedDate);
                } catch (ParseException e) {
                    Toast.makeText(getContext(), "Định dạng ngày không hợp lệ! Hãy kiểm tra lại.", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                    return;  // Dừng lại nếu ngày sai
                }
                try {
                    // Định dạng đúng và phân tích chuỗi giờ
//                    SimpleDateFormat timeInputFormatter = new SimpleDateFormat("hh 'giờ' mm 'phút' a", Locale.ENGLISH);
                    Date TTime = timeInputFormatter.parse(time);
                    // Lưu thời gian đã phân tích
                    formattedTime = timeOutputFormatter.format(TTime);
                    Log.d("Fuck u","Chuỗi ngày đã chuyển đổi: " + formattedTime);
                    // Kiểm tra giờ
                } catch (ParseException e) {
                    Toast.makeText(getContext(), "Định dạng giờ không hợp lệ! Hãy kiểm tra lại.", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                    return;  // Dừng lại nếu giờ sai
                }

                String dataDate = formattedDate + " " + formattedTime;
                Log.d("Data Date", "Ngày và giờ lưu: " + dataDate);

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
                Finaldate = dateFormat.parse(dataDate);

            } catch (Exception e) {
                e.printStackTrace();
                return;
            }

            // Tạo đối tượng Noti
            int bellIconResId = R.drawable.ic_noti_45;
            Noti newNotiData = new Noti(name, date, time, details, selectedColorHex, bellIconResId);

            // Gửi dữ liệu qua listener
            if (onSaveListener != null) {
                onSaveListener.onSave(newNotiData);
            }

            // Lưu vào cơ sở dữ liệu
            Executor executor = Executors.newSingleThreadExecutor();
            executor.execute(() -> {
                Database database = MainActivity.appDatabase;

                Notification notification = new Notification(name, details, selectedColorHex, reminder, Finaldate , new Date(), "default_Ring");
                long nitiId = database.notificationDao().insertNotification(notification);
                notification.Noti_id = (int) nitiId;


                showNotificationList(notification);
            });

            // Hiển thị thông báo thành công
            Toast.makeText(getContext(), "Đã lưu thông báo: " + name, Toast.LENGTH_SHORT).show();
            dismiss(); // Đóng BottomSheet sau khi lưu
        });
    }

    private void toggleIcon(ImageView icon) {
        Boolean isNotified = (Boolean) icon.getTag();
        if (isNotified == null || !isNotified) {
            icon.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_noti_45));
            icon.setTag(true);
            reminder = 1;
        } else {
            icon.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_unnoti_45));
            icon.setTag(false);
            reminder = 0;
        }

        // Ghi log giá trị reminder
        Log.d("Reminder Status", "Reminder value: " + reminder);
    }

    private void setupColorPicker(View createNotiView) {
        RadioGroup colorRadioGroup = createNotiView.findViewById(R.id.radioGroup);
        ImageView checkColor = createNotiView.findViewById(R.id.checkColor);
        ColorManager colorManager = new ColorManager();

        colorManager.createColorRadioGroup(getContext(), colorRadioGroup, selectedColor -> {
            Toast.makeText(getContext(), "Màu đã chọn: " + selectedColor.getName(), Toast.LENGTH_SHORT).show();
            selectedColorHex = selectedColor.getHexCode();
            changeImageColor(checkColor, selectedColorHex);
        });
    }

    private void changeImageColor(ImageView imageView, String hexColor) {
        Drawable drawable = imageView.getDrawable();
        if (drawable == null) {
            drawable = ContextCompat.getDrawable(imageView.getContext(), R.drawable.ic_color_lens_50);
            imageView.setImageDrawable(drawable);
        }
        Drawable drawableCopy = drawable.mutate();
        drawableCopy.setTint(Color.parseColor(hexColor));
        imageView.setImageDrawable(drawableCopy);
    }

    // Các lớp wrapper cho Date và Time
    public void showNotificationList(Notification notification) {
        AlarmManager alarmMgr = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getContext(), AlarmReceiver.class);

        // Gửi thông tin thông báo vào Intent
        intent.putExtra("title", notification.getTittle());
        intent.putExtra("content", notification.getDetails());

        Log.d("CheckCreate", String.valueOf(notification.getNoti_id()));
        // PendingIntent để thực thi AlarmReceiver
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), notification.getNoti_id(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Thiết lập thời gian báo thức
        long notificationTimeMillis = notification.getNotiDate().getTime();
        alarmMgr.setExact(AlarmManager.RTC_WAKEUP, notificationTimeMillis, pendingIntent);



    }









}
