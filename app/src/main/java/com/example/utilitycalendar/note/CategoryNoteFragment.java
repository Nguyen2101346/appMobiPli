package com.example.utilitycalendar.note;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.utilitycalendar.R;

import java.util.ArrayList;
import java.util.List;

public class CategoryNoteFragment extends Fragment {
    private RecyclerView recyclerView;
    private NoteCategoryAdapter adapter;
    private List<NoteCategory> categoryList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.note_category, container, false);
        recyclerView = view.findViewById(R.id.note_category_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Khởi tạo danh sách danh mục
        categoryList = new ArrayList<>();
        // Thêm dữ liệu mẫu
        categoryList.add(new NoteCategory("hoctap","Học tập", 5));
        categoryList.add(new NoteCategory("lamviec","Làm việc", 5));
        categoryList.add(new NoteCategory("giaitri","Giải trí", 5));
        categoryList.add(new NoteCategory("thuongnagy","Thường ngày", 5));

        adapter = new NoteCategoryAdapter(getContext(), categoryList, category -> {
            // Xử lý khi nhấn vào một danh mục
            NoteFragment fragment = new NoteFragment();

            // Truyền dữ liệu qua Bundle
            Bundle bundle = new Bundle();
            bundle.putString("categoryId", category.getId()); // Truyền id của category
            fragment.setArguments(bundle);


            // Chuyển fragment mới
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_layout, fragment)
                    .addToBackStack(null)
                    .commit();



        });

        recyclerView.setAdapter(adapter);
        return view;
    }

}
