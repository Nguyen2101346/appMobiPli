package com.example.utilitycalendar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.utilitycalendar.CreateNoti.Noti;

import java.util.List;

public class NotiAdapter extends RecyclerView.Adapter<NotiAdapter.NotiViewHolder> {

    private final List<Noti> notiList;

    // Constructor
    public NotiAdapter(List<Noti> notiList) {
        this.notiList = notiList;
    }

    @NonNull
    @Override
    public NotiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the correct item layout (make sure to use the correct layout here)
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.noti_item, parent, false);
        return new NotiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotiViewHolder holder, int position) {
        // Bind data to the views
        Noti noti = notiList.get(position);
        holder.tvTime.setText(noti.getTime());
        holder.tvEvent.setText(noti.getName());
        holder.ivBell.setImageResource(noti.getBellIcon());
    }

    @Override
    public int getItemCount() {
        return notiList.size();
    }

    // ViewHolder class to hold each item view
    public static class NotiViewHolder extends RecyclerView.ViewHolder {
        TextView tvTime, tvAmPm, tvEvent;
        ImageView ivBell;

        public NotiViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ensure you're using the correct IDs here
            tvTime = itemView.findViewById(R.id.tv_time);
            tvAmPm = itemView.findViewById(R.id.tv_am_pm);
            tvEvent = itemView.findViewById(R.id.tv_event);
            ivBell = itemView.findViewById(R.id.iv_bell);
        }
    }
}
