package com.example.utilitycalendar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.utilitycalendar.CreateNoti.Noti;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private NotiAdapter notiAdapter;
    private List<Noti> notiList; // Danh sách thông báo
    private TextView tvDate;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home, container, false);

        // Ánh xạ RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView);
        tvDate = view.findViewById(R.id.tv_date);

        // Khởi tạo danh sách thông báo
        notiList = new ArrayList<>();

        // Set up RecyclerView
        notiAdapter = new NotiAdapter(notiList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(notiAdapter);

        // Cập nhật ngày
        updateDate();

        return view;
    }

    private void updateDate() {
        // Hiển thị ngày hiện tại lên TextView (tùy chỉnh định dạng ngày)
        tvDate.setText("Today"); // Ví dụ: đặt ngày hiện tại là Today
    }

    public void addNotification(Noti newNoti) {
        // Thêm thông báo mới vào danh sách
        notiList.add(newNoti);
        notiAdapter.notifyItemInserted(notiList.size() - 1);
    }
}
