package com.example.utilitycalendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;

import com.example.utilitycalendar.ReminderReceiver;

import java.util.ArrayList;
import java.util.Calendar;

public class HomeFragment extends Fragment {

    // Khai báo các biến cần thiết
    private Calendar calendar = Calendar.getInstance();
    private DatePicker datePicker;
    private EditText edtTitle;
    private Button btnPickTime, btnOK, btnCancel, btnToggleDatePicker;
    private LinearLayout btnContainer, llReminders;
    private ArrayList<String> remindersList = new ArrayList<>();
    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "RemindersPrefs";
    private static final String REMINDERS_KEY = "reminders";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate layout của fragment
        View view = inflater.inflate(R.layout.home, container, false);

        // Ánh xạ các thành phần giao diện
        datePicker = view.findViewById(R.id.datePicker);
        edtTitle = view.findViewById(R.id.edtTitle);
        btnPickTime = view.findViewById(R.id.btnPickTime);
        btnOK = view.findViewById(R.id.btnOK);
        btnCancel = view.findViewById(R.id.btnCancel);
        btnToggleDatePicker = view.findViewById(R.id.btnToggleDatePicker);
        btnContainer = view.findViewById(R.id.btnContainer);
        llReminders = view.findViewById(R.id.llReminders);

        // Lấy đối tượng SharedPreferences để lưu trữ danh sách nhắc nhở
        sharedPreferences = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        // Tải danh sách nhắc nhở đã lưu
        loadReminders();

        // Xử lý sự kiện khi chọn ngày trong DatePicker
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), (view1, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            // Hiển thị các thành phần để nhập tiêu đề và chọn thời gian
            edtTitle.setVisibility(EditText.VISIBLE);
            btnPickTime.setVisibility(Button.VISIBLE);
            btnContainer.setVisibility(LinearLayout.VISIBLE);
        });

        // Xử lý sự kiện khi nhấn nút chọn thời gian
        btnPickTime.setOnClickListener(v -> showTimePicker());

        // Xử lý sự kiện khi nhấn nút OK
        btnOK.setOnClickListener(v -> {
            setReminder();          // Lưu nhắc nhở mới
            showReminderDetails();  // Hiển thị danh sách nhắc nhở
            resetUI();              // Chuyển về giao diện hiển thị danh sách nhắc nhở
        });

        // Xử lý sự kiện khi nhấn nút Cancel
        btnCancel.setOnClickListener(v -> resetUI());

        // Xử lý sự kiện khi nhấn nút Toggle DatePicker
        btnToggleDatePicker.setOnClickListener(v -> {
            View emptyStateLayout = view.findViewById(R.id.emptyStateLayout);// Lấy view thông báo

            if (datePicker.getVisibility() == DatePicker.VISIBLE) {
                datePicker.setVisibility(DatePicker.GONE);  // Ẩn DatePicker
                llReminders.setVisibility(LinearLayout.VISIBLE); // Hiển thị danh sách nhắc nhở

                // Kiểm tra nếu danh sách nhắc nhở trống thì hiển thị thông báo
                if (remindersList.isEmpty()) {
                    emptyStateLayout.setVisibility(View.VISIBLE);
                } else {
                    emptyStateLayout.setVisibility(View.GONE);
                }
            } else {
                datePicker.setVisibility(DatePicker.VISIBLE);  // Hiển thị DatePicker
                llReminders.setVisibility(LinearLayout.GONE); // Ẩn danh sách nhắc nhở
                emptyStateLayout.setVisibility(View.GONE);  // Ẩn thông báo
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadReminders(); // Đảm bảo load lại nhắc nhở mỗi khi quay lại màn hình
    }

    // Hiển thị TimePicker để người dùng chọn thời gian
    private void showTimePicker() {
        new TimePickerDialog(getContext(), (view, hourOfDay, minute) -> {
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.SECOND, 0);
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
    }

    // Lưu nhắc nhở vào danh sách và cài đặt báo thức
    private void setReminder() {
        String title = edtTitle.getText().toString(); // Lấy tiêu đề từ EditText
        if (title.isEmpty()) {
            title = "Nhắc nhở mặc định"; // Tiêu đề mặc định nếu chưa nhập
        }

        String reminderDetails = title + " lúc " + String.format("%02d-%02d-%d %02d:%02d",
                calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.MONTH) + 1, // Tháng bắt đầu từ 0
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE));

        remindersList.add(reminderDetails); // Thêm nhắc nhở vào danh sách
        saveReminders(); // Lưu nhắc nhở vào SharedPreferences

        // **Tạo ID duy nhất cho mỗi nhắc nhở**
        int requestCode = (int) calendar.getTimeInMillis(); // Sử dụng thời gian làm ID duy nhất

        // Tạo báo thức
        Intent intent = new Intent(getContext(), ReminderReceiver.class);
        intent.putExtra("title", title);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getContext(), requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }

        // Lưu requestCode để hủy sau này
        saveReminderId(reminderDetails, requestCode);
    }


    // Hiển thị danh sách nhắc nhở đã tạo
    private void showReminderDetails() {
        llReminders.removeAllViews(); // Xóa danh sách cũ

        // Kiểm tra nếu danh sách nhắc nhở rỗng
        if (remindersList == null || remindersList.isEmpty()) {
            llReminders.setVisibility(View.GONE); // Ẩn danh sách
            if (getView() != null) {
                View emptyStateLayout = getView().findViewById(R.id.emptyStateLayout);
                if (emptyStateLayout != null) {
                    emptyStateLayout.setVisibility(View.VISIBLE); // Hiển thị thông báo rỗng
                }
            }
            return; // Thoát, không tiếp tục hiển thị danh sách
        }

        // Nếu có nhắc nhở, hiển thị danh sách
        llReminders.setVisibility(View.VISIBLE);
        if (getView() != null) {
            View emptyStateLayout = getView().findViewById(R.id.emptyStateLayout);
            if (emptyStateLayout != null) {
                emptyStateLayout.setVisibility(View.GONE); // Ẩn thông báo rỗng
            }
        }

        // Hiển thị từng nhắc nhở
        for (int i = 0; i < remindersList.size(); i++) {
            final String reminder = remindersList.get(i);

            // Chia phần ngày giờ và tiêu đề nhắc nhở
            String[] reminderParts = reminder.split(" lúc ");
            String dateTime = reminderParts[1];  // Ngày giờ
            String title = reminderParts[0];     // Tiêu đề nhắc nhở

            // Inflate layout nhắc nhở
            View reminderView = LayoutInflater.from(getContext()).inflate(R.layout.item_reminder, llReminders, false);

            // Ánh xạ các thành phần trong layout nhắc nhở
            TextView tvReminderTitle = reminderView.findViewById(R.id.tvReminderTitle);
            TextView tvReminderTime = reminderView.findViewById(R.id.tvReminderTime);
            ImageView btnDeleteReminder = reminderView.findViewById(R.id.btnDeleteReminder);

            // Gán dữ liệu nhắc nhở
            tvReminderTitle.setText(title);
            tvReminderTime.setText(dateTime);

            // Xử lý sự kiện khi nhấn nút xóa
            btnDeleteReminder.setOnClickListener(v -> {
                int requestCode = getReminderId(reminder); // Lấy requestCode từ SharedPreferences
                if (requestCode != -1) {
                    cancelReminder(requestCode); // Hủy báo thức
                }

                remindersList.remove(reminder); // Xóa khỏi danh sách nhắc nhở
                saveReminders();                // Lưu lại danh sách
                showReminderDetails();          // Cập nhật giao diện
            });

            // Thêm view nhắc nhở vào danh sách
            llReminders.addView(reminderView);
        }
    }


    // Đặt lại giao diện về trạng thái ban đầu
    private void resetUI() {
        edtTitle.setVisibility(EditText.GONE);
        btnPickTime.setVisibility(Button.GONE);
        btnContainer.setVisibility(LinearLayout.GONE);
        datePicker.setVisibility(DatePicker.GONE);
        llReminders.setVisibility(LinearLayout.VISIBLE);
    }

    // Lưu danh sách nhắc nhở vào SharedPreferences
    private void saveReminders() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        StringBuilder remindersString = new StringBuilder();
        for (String reminder : remindersList) {
            remindersString.append(reminder).append(";");
        }
        editor.putString(REMINDERS_KEY, remindersString.toString());
        editor.apply(); // Lưu thay đổi vào SharedPreferences
    }

    // Tải danh sách nhắc nhở từ SharedPreferences
    // Tải danh sách nhắc nhở từ SharedPreferences
    private void loadReminders() {
        try {
            String remindersString = sharedPreferences.getString(REMINDERS_KEY, "");
            if (!remindersString.isEmpty()) {
                String[] savedReminders = remindersString.split(";");
                remindersList.clear();  // Xóa danh sách cũ trước khi thêm mới
                for (String reminder : savedReminders) {
                    if (!reminder.isEmpty()) {
                        remindersList.add(reminder);
                    }
                }
                showReminderDetails(); // Hiển thị danh sách nhắc nhở
            } else {
                llReminders.setVisibility(View.GONE); // Ẩn danh sách nhắc nhở nếu không có nhắc nhở nào
                View emptyStateLayout = getView().findViewById(R.id.emptyStateLayout);
                if (emptyStateLayout != null) {
                    emptyStateLayout.setVisibility(View.VISIBLE); // Hiển thị thông báo trống
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Ghi log lỗi để kiểm tra
            remindersList.clear(); // Xóa dữ liệu lỗi
        }
    }



    private void saveReminderId(String reminderDetails, int requestCode) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(reminderDetails, requestCode);
        editor.apply();
    }

    private int getReminderId(String reminderDetails) {
        return sharedPreferences.getInt(reminderDetails, -1);
    }

    private void cancelReminder(int requestCode) {
        Intent intent = new Intent(getContext(), ReminderReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getContext(), requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent); // Hủy thông báo
        }
    }

    // Phương thức để xóa toàn bộ nhắc nhở
    private void clearAllReminders() {
        sharedPreferences.edit().clear().apply(); // Xóa toàn bộ dữ liệu trong SharedPreferences
        remindersList.clear();                   // Xóa danh sách trong bộ nhớ tạm
        showReminderDetails();                   // Cập nhật giao diện
    }



}
