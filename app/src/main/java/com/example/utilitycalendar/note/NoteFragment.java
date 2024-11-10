package com.example.utilitycalendar.note;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.utilitycalendar.R;

import java.util.ArrayList;
import java.util.List;

public class NoteFragment extends Fragment {
    private TextView categoryIdTextView;

    private RecyclerView recyclerView;
    private NoteAdapter adapter;
    private List<Note> noteList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.note, container, false);

        categoryIdTextView = view.findViewById(R.id.text_view);
        recyclerView = view.findViewById(R.id.note_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));


        noteList = new ArrayList<>();
        // Thêm dữ liệu mẫu
        noteList.add(new Note("1","hoc 1","Học tập", "5/5/2022"));
        noteList.add(new Note("2","hoc 2","Học tập", "5/5/2022"));
        noteList.add(new Note("3","hoc 3","sddssdssdds \n ggggfffffffffffffffff\n ffffffffffffffffffffffffff\nfffffffffff\nffff    \nsdsdsddsdsdsdsds \nn sdddsdsdsdsd \nsdsddssd\nddsdssd\n heets", "5/5/2022"));
        noteList.add(new Note("4","hoc 4","Học tập", "5/5/2022"));
        noteList.add(new Note("5","hoc 5","Học tập", "5/5/2022"));
        noteList.add(new Note("6","hoc 6","Học tập", "5/5/2022"));


        if (getArguments() != null) {
            String categoryId = getArguments().getString("categoryId");
            categoryIdTextView.setText("ID của danh mục: " + categoryId);  // Hiển thị id
        }

        adapter = new NoteAdapter(getContext(), noteList, note -> {
            // Xử lý khi nhấn vào một danh mục



        });


        recyclerView.setAdapter(adapter);

        return view;


    }



}
