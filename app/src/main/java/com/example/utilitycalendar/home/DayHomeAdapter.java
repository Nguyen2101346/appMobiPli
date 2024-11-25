package com.example.utilitycalendar.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.utilitycalendar.Database.NoteCategories;
import com.example.utilitycalendar.Database.Notification;
import com.example.utilitycalendar.R;

import java.util.List;

public class DayHomeAdapter extends RecyclerView.Adapter<DayHomeAdapter.NotificationViewHolder> {
    private Context context;
    private List<Notification> notifications;
    private OnDayHomeClickListener listener;

    // Constructor
    public DayHomeAdapter(Context context, List<Notification> notifications, OnDayHomeClickListener listener) {
        this.context = context;
        this.notifications = notifications;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout cho từng item trong RecyclerView
        View view = LayoutInflater.from(context).inflate(R.layout.item_reminder, parent, false);
        return new NotificationViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        // Lấy đối tượng NoteCategory tại vị trí 'position'
        Notification notification = notifications.get(position);

        // Cập nhật view con với dữ liệu từ NoteCategory
        holder.dayHomeTitle.setText(notification.getTittle());





        holder.dayHomeTime.setText(String.valueOf(notification.NotiDate));


        // Đặt icon cho ImageView
        @SuppressLint("DiscouragedApi") int iconResId = context.getResources().getIdentifier("ic_delete", "drawable", context.getPackageName());
        holder.dayHomeImage.setImageResource(iconResId);

        // Xử lý sự kiện khi nhấn vào item
        holder.itemView.setOnClickListener(v -> listener.onDayHomeClick(notification));
        holder.dayHomeImage.setOnClickListener(view -> listener.onDeleteClick(notification.getNoti_id()));
    }

    @Override
    public int getItemCount() {
        // Trả về số lượng phần tử trong danh sách
        return notifications.size();
    }

    // Định nghĩa lớp ViewHolder
    public static class NotificationViewHolder extends RecyclerView.ViewHolder {
        public TextView dayHomeTitle, dayHomeTime;
        public ImageView dayHomeImage;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            // Khởi tạo các view con từ layout item_note_category
            dayHomeTitle = itemView.findViewById(R.id.tvReminderTitle);
            dayHomeTime = itemView.findViewById(R.id.tvReminderTime);
            dayHomeImage = itemView.findViewById(R.id.btnDeleteReminder);
        }
    }

    // Định nghĩa giao diện cho click listener (nếu cần)
    public interface OnDayHomeClickListener {
        void onDayHomeClick(Notification notification);
        void onDeleteClick(int id);
    }
}
