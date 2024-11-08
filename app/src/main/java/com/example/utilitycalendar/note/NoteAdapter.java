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

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    private Context context;
    private List<Note> notes;
    private OnNoteClickListener listener;

    // Constructor
    public NoteAdapter(Context context, List<Note> notes, OnNoteClickListener listener) {
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
        Note note = notes.get(position);

        // Cập nhật view con với dữ liệu từ NoteCategory
        holder.noteTitle.setText(note.getTitle());
        holder.noteContent.setText(note.getContent());
        holder.noteDate.setText(note.getDate());

        // Xử lý sự kiện khi nhấn vào item
        holder.itemView.setOnClickListener(v -> listener.onNoteClick(note));
    }

    @Override
    public int getItemCount() {
        // Trả về số lượng phần tử trong danh sách
        return notes.size();
    }

    // Định nghĩa lớp ViewHolder
    public static class NoteViewHolder extends RecyclerView.ViewHolder {
        public TextView noteTitle, noteContent, noteDate;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            // Khởi tạo các view con từ layout item_note_category
            noteTitle = itemView.findViewById(R.id.note_title);
            noteContent = itemView.findViewById(R.id.note_content);
            noteDate = itemView.findViewById(R.id.note_date);
        }
    }

    // Định nghĩa giao diện cho click listener (nếu cần)
    public interface OnNoteClickListener {
        void onNoteClick(Note note);
    }
}
