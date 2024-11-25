package com.example.utilitycalendar.home;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.utilitycalendar.Database.Database;
import com.example.utilitycalendar.Database.NoteCategories;
import com.example.utilitycalendar.Database.Notification;
import com.example.utilitycalendar.MainActivity;
import com.example.utilitycalendar.R;
import com.example.utilitycalendar.alarm.AlarmReceiver;
import com.example.utilitycalendar.note.NoteCategoryAdapter;
import com.example.utilitycalendar.note.NoteFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DayHomeFragment extends Fragment implements DayHomeAdapter.OnDayHomeClickListener{
    private RecyclerView recyclerView;
    private DayHomeAdapter adapter;
    private Button btnToggleDatePicker;
    private LinearLayout datePickerLayout;
    private RelativeLayout emptyStateLayout;
    private String checkChooseDay;

    Database database = MainActivity.appDatabase;


    @Override
    public void onDayHomeClick(Notification notification) {
        NoteFragment fragment = new NoteFragment();

        // Truyền dữ liệu qua Bundle
        Bundle bundle = new Bundle();
        bundle.putString("notifyId", "1"); // Truyền id của category
        fragment.setArguments(bundle);

        // Chuyển fragment mới
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .addToBackStack(null)
                .commit();

    }


    @Override
    public void onDeleteClick(int id) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            // Xử lý khi người dùng nhấn vào nút xóa
            Notification notificationToDelete = database.notificationDao().getNotificationById(id);
            if (notificationToDelete != null) {
                cancelNotification(notificationToDelete);
                database.notificationDao().deleteNotification(notificationToDelete);
                queryDataByDatePattern(checkChooseDay);


            }

        });
    }


    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.day_home, container, false);
        checkChooseDay = "";

        recyclerView = view.findViewById(R.id.day_home_recycler_view);
        btnToggleDatePicker = view.findViewById(R.id.btnToggleDatePicker);
        datePickerLayout = view.findViewById(R.id.datePickerLayout); // LinearLayout chứa DatePicker
        emptyStateLayout = view.findViewById(R.id.emptyStateLayout);
        ImageView btnDeleteReminder = view.findViewById(R.id.btnDeleteReminder);


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        queryDataByDatePattern(checkChooseDay);



        btnToggleDatePicker.setOnClickListener(v -> {
            // Đảo trạng thái hiển thị của DatePicker và RecyclerView
            boolean isDatePickerVisible = datePickerLayout.getVisibility() == View.VISIBLE;

            if (isDatePickerVisible) {
                // Ẩn DatePicker và hiển thị RecyclerView
                datePickerLayout.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                if(checkChooseDay == "" || checkChooseDay.isEmpty())
                    emptyStateLayout.setVisibility(View.VISIBLE);
            } else {
                // Hiển thị DatePicker và ẩn RecyclerView
                datePickerLayout.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                emptyStateLayout.setVisibility(View.GONE);
            }
        });



// Thêm sự kiện khi chọn ngày trong DatePicker
        DatePicker datePicker = view.findViewById(R.id.datePicker);
        datePicker.setOnDateChangedListener((view1, year, monthOfYear, dayOfMonth) -> {
            // Tạo đối tượng Calendar từ DatePicker
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, monthOfYear, dayOfMonth, 0, 0, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            Date selectedDate = calendar.getTime();

            // Chuyển đổi Date thành chuỗi có định dạng YYYY-MM-DD
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            checkChooseDay = dateFormat.format(selectedDate);

            // Gọi phương thức để truy vấn dữ liệu từ database với chuỗi ngày

            queryDataByDatePattern(checkChooseDay);
            datePickerLayout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        });









        return view;
    }


    private void queryDataByDatePattern(String datePattern) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            List<Notification> notifications = new ArrayList<>();
            // Thêm dữ liệu mẫu vào database (nếu cần)

//            Date now = new Date();
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(now);
//            calendar.add(Calendar.SECOND, 10); // Thêm 1 phút
//
//            Date updatedTime = calendar.getTime();
//
//            database.notificationDao().insertNotification(new Notification(0, "1", "1", "F0000", 1, updatedTime, new Date(), "okok"));




            if(datePattern == "" || datePattern == null){
                // Lấy tất cả dữ liệu từ database
                notifications.clear();  // Đảm bảo danh sách không còn dữ liệu cũ
                notifications.addAll(database.notificationDao().getAllNotifications(new Date()));
            }else {
                notifications.clear();  // Đảm bảo danh sách không còn dữ liệu cũ
                notifications.addAll(database.notificationDao().getNotificationsByDatePattern(datePattern));
            }
            Log.d("DayHomeFragment", "Selected date: " + datePattern);

            // Cập nhật UI trên main thread
            getActivity().runOnUiThread(() -> {
                if (notifications.isEmpty()) {
                    emptyStateLayout.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);

                    checkChooseDay = "";
                    adapter = new DayHomeAdapter(getContext(), notifications, this);
                    recyclerView.setAdapter(adapter);
                } else {
                    adapter = new DayHomeAdapter(getContext(), notifications, this);

                    emptyStateLayout.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);

                    recyclerView.setAdapter(adapter);
                }
            });
        });
    }




    public void cancelNotification(Notification notification) {
        AlarmManager alarmMgr = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getContext(), AlarmReceiver.class);

        // Gửi cùng thông tin như khi tạo thông báo
        intent.putExtra("title", notification.getTittle());
        intent.putExtra("content", notification.getDetails());
        Log.d("Check", String.valueOf(intent));
        Log.d("Check", String.valueOf(notification.getNoti_id()));

        // Tạo PendingIntent với cùng ID và FLAG
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), notification.getNoti_id(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Hủy báo thức
        if (alarmMgr != null) {
            alarmMgr.cancel(pendingIntent);
        }

        // Hủy PendingIntent
        pendingIntent.cancel();
    }



}

