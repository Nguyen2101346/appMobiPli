package com.example.utilitycalendar.note;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.utilitycalendar.Database.Database;
import com.example.utilitycalendar.Database.NoteCategories;
import com.example.utilitycalendar.MainActivity;
import com.example.utilitycalendar.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CategoryNoteFragment extends Fragment {
    private RecyclerView recyclerView;
    private NoteCategoryAdapter adapter;

    Database database = MainActivity.appDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.note_category, container, false);
        recyclerView = view.findViewById(R.id.note_category_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Khởi tạo danh sách danh mục

        // Executor để chạy database trên background thread
        Executor executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {
            // Thêm dữ liệu mẫu vào database (nếu cần)


            // Lấy tất cả dữ liệu từ database
            List<NoteCategories> categories = database.noteCategoriesDao().getAllNoteCategories();

            // Cập nhật UI trên main thread
            getActivity().runOnUiThread(() -> {
                if (categories.isEmpty()) {
                    Log.d("CategoryNoteFragment", "categoryList is empty");
                } else {
                    adapter = new NoteCategoryAdapter(getContext(), categories, category -> {
                        // Xử lý khi nhấn vào một danh mục
                        NoteFragment fragment = new NoteFragment();

                        // Truyền dữ liệu qua Bundle
                        Bundle bundle = new Bundle();
                        bundle.putString("categoryId", category.getNote_id()); // Truyền id của category
                        fragment.setArguments(bundle);

                        // Chuyển fragment mới
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frame_layout, fragment)
                                .addToBackStack(null)
                                .commit();
                    });

                    recyclerView.setAdapter(adapter);
                }
            });
        });

        return view;
    }
}
