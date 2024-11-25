package com.example.utilitycalendar.note;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.utilitycalendar.Database.Database;
import com.example.utilitycalendar.Database.NoteCategories;
import com.example.utilitycalendar.Database.Notes;
import com.example.utilitycalendar.MainActivity;
import com.example.utilitycalendar.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class NoteFragment extends Fragment {
    private TextView categoryIdTextView;

    private RecyclerView recyclerView;
    private NoteAdapter adapter;

    Database database = MainActivity.appDatabase;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.note, container, false);

        categoryIdTextView = view.findViewById(R.id.text_view);
        recyclerView = view.findViewById(R.id.note_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));




//        noteList = new ArrayList<>();
        // Thêm dữ liệu mẫu



        if (getArguments() != null) {
            String categoryId = getArguments().getString("categoryId");
            // Hiển thị id


            Executor executor = Executors.newSingleThreadExecutor();

            executor.execute(() -> {
                // Thêm dữ liệu mẫu vào database (nếu cần)


                // Lấy tất cả dữ liệu từ database
                List<Notes> notes = database.notesDao().getNotesByCateId(categoryId);

                // Cập nhật UI trên main thread
                getActivity().runOnUiThread(() -> {
                    if (notes.isEmpty()) {
                        Log.d("CategoryNoteFragment", "categoryList is empty");
                    } else {
                        adapter = new NoteAdapter(getContext(), notes, note -> {
                            // Xử lý khi nhấn vào một danh mục
                            NoteFragment fragment = new NoteFragment();

                        });

                        recyclerView.setAdapter(adapter);
                    }
                });
            });


        }

        return view;


    }



}
