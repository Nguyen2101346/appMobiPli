package com.example.utilitycalendar.note;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.utilitycalendar.R;

import java.util.List;

public class NoteCategoryAdapter extends RecyclerView.Adapter<NoteCategoryAdapter.NoteCategoryViewHolder> {
    private Context context;
    private List<NoteCategory> noteCategories;
    private OnNoteCategoryClickListener listener;

    // Constructor
    public NoteCategoryAdapter(Context context, List<NoteCategory> noteCategories, OnNoteCategoryClickListener listener) {
        this.context = context;
        this.noteCategories = noteCategories;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NoteCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout cho từng item trong RecyclerView
        View view = LayoutInflater.from(context).inflate(R.layout.item_note_category, parent, false);
        return new NoteCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteCategoryViewHolder holder, int position) {
        // Lấy đối tượng NoteCategory tại vị trí 'position'
        NoteCategory category = noteCategories.get(position);

        // Cập nhật view con với dữ liệu từ NoteCategory
        holder.categoryName.setText(category.getName());


        StringBuilder noteCount = new StringBuilder();
        noteCount.append(category.getNoteCount()).append(" ghi chú");


        holder.noteCount.setText(String.valueOf(noteCount));

        // Xử lý sự kiện khi nhấn vào item
        holder.itemView.setOnClickListener(v -> listener.onCategoryClick(category));
    }

    @Override
    public int getItemCount() {
        // Trả về số lượng phần tử trong danh sách
        return noteCategories.size();
    }

    // Định nghĩa lớp ViewHolder
    public static class NoteCategoryViewHolder extends RecyclerView.ViewHolder {
        public TextView categoryName, noteCount;

        public NoteCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            // Khởi tạo các view con từ layout item_note_category
            categoryName = itemView.findViewById(R.id.note_category_name);
            noteCount = itemView.findViewById(R.id.note_category_count);
        }
    }

    // Định nghĩa giao diện cho click listener (nếu cần)
    public interface OnNoteCategoryClickListener {
        void onCategoryClick(NoteCategory category);
    }
}
