package com.example.utilitycalendar.Helper;

import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Locale;

public class TimePickerHelper {
    private final Context context;
    private int hour, minute;
    private TimePickerDialog timePickerDialog;

    public TimePickerHelper(Context context) {
        this.context = context;
    }

    public void openTimePicker(EditText timeText) {
        Calendar cal = Calendar.getInstance();
        hour = cal.get(Calendar.HOUR_OF_DAY);
        minute = cal.get(Calendar.MINUTE);

        timePickerDialog = new TimePickerDialog(context, (TimePicker timePicker, int selectedHour, int selectedMinute) -> {
            hour = selectedHour;
            minute = selectedMinute;

            // Xác định xem là "Sáng" hay "Chiều"
            String period = (hour < 12) ? "Sáng" : "Chiều";

            // Định dạng giờ theo kiểu 12 giờ (1-12)
            int displayHour = (hour == 0 || hour == 12) ? 12 : hour % 12;

            // Hiển thị thời gian với định dạng "hh giờ mm phút Sáng/Chiều"
            String formattedTime = String.format(Locale.getDefault(), "%02d giờ %02d phút %s", displayHour, minute, period);
            timeText.setText(formattedTime);
        }, hour, minute, false); // false để sử dụng chế độ 12 giờ

        timePickerDialog.show();
    }
}



