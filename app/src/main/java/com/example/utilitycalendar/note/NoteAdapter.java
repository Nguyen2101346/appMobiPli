package com.example.utilitycalendar.note;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.utilitycalendar.Database.Notes;
import com.example.utilitycalendar.R;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    private Context context;
    private List<Notes> notes;
    private OnNoteClickListener listener;

    // Constructor
    public NoteAdapter(Context context, List<Notes> notes, OnNoteClickListener listener) {
        this.context = context;
        this.notes = notes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout cho từng item trong RecyclerView
        View view = LayoutInflater.from(context).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        // Lấy đối tượng NoteCategory tại vị trí 'position'
        Notes note = notes.get(position);

        // Cập nhật view con với dữ liệu từ NoteCategory
        holder.noteTitle.setText(note.getTittle());
        holder.noteContent.setText(note.getContent());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        holder.noteDate.setText(dateFormat.format(note.getNoteDate()));


        // Kiểm tra giá trị 'pin' để ẩn hoặc hiển thị note_image
        if (note.getPinned() == 1) { // Giả sử có phương thức isPin() để kiểm tra giá trị pin
            holder.noteImage.setVisibility(View.VISIBLE);
        } else {
            holder.noteImage.setVisibility(View.GONE);
        }



        // Xử lý sự kiện khi nhấn vào item
        holder.itemView.setOnClickListener(v -> listener.onNoteClick(note));
        holder.noteDelete.setOnClickListener(view -> listener.onDeleteClick(note.getNote_id()));
    }

    @Override
    public int getItemCount() {
        // Trả về số lượng phần tử trong danh sách
        return notes.size();
    }

    // Định nghĩa lớp ViewHolder
    public static class NoteViewHolder extends RecyclerView.ViewHolder {
        public TextView noteTitle, noteContent, noteDate;
        public ImageView noteImage, noteDelete;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            // Khởi tạo các view con từ layout item_note_category
            noteTitle = itemView.findViewById(R.id.note_title);
            noteContent = itemView.findViewById(R.id.note_content);
            noteDate = itemView.findViewById(R.id.note_date);
            noteImage = itemView.findViewById(R.id.note_image);
            noteDelete = itemView.findViewById(R.id.btn_Delete);
        }
    }

    // Định nghĩa giao diện cho click listener (nếu cần)
    public interface OnNoteClickListener {
        void onNoteClick(Notes note);
        void onDeleteClick(int id);
    }
}
