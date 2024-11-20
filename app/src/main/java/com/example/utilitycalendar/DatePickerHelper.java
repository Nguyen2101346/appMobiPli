package com.example.utilitycalendar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.EditText;
import java.util.Calendar;

public class DatePickerHelper {

    private final Context context;
    private DatePickerDialog datePickerDialog;

    public DatePickerHelper(Context context) {
        this.context = context;
    }

    public void initializeDatePicker(EditText dateText) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(context, AlertDialog.THEME_HOLO_LIGHT,
                (view, year1, month1, day1) -> dateText.setText(makeDateString(day1, month1 + 1, year1)), year, month, day);
    }

    public void showDatePicker() {
        if (datePickerDialog != null) {
            datePickerDialog.show();
        }
    }

    private String makeDateString(int day, int month, int year) {
        return getDayOfWeek(day, month, year) + " - " + day + "/" + month + "/" + year;
    }

    private String getDayOfWeek(int day, int month, int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, day); // Trừ 1 vì tháng trong Calendar bắt đầu từ 0 (0 = Tháng 1)

        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        String dayOfWeekStr = "";

        switch (dayOfWeek) {
            case Calendar.SUNDAY:
                dayOfWeekStr = "Chủ Nhật";
                break;
            case Calendar.MONDAY:
                dayOfWeekStr = "Thứ Hai";
                break;
            case Calendar.TUESDAY:
                dayOfWeekStr = "Thứ Ba";
                break;
            case Calendar.WEDNESDAY:
                dayOfWeekStr = "Thứ Tư";
                break;
            case Calendar.THURSDAY:
                dayOfWeekStr = "Thứ Năm";
                break;
            case Calendar.FRIDAY:
                dayOfWeekStr = "Thứ Sáu";
                break;
            case Calendar.SATURDAY:
                dayOfWeekStr = "Thứ Bảy";
                break;
        }

        return dayOfWeekStr;
    }
}

