package com.example.utilitycalendar.note;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.utilitycalendar.BottomSheetManager;
import com.example.utilitycalendar.Database.Database;
import com.example.utilitycalendar.Database.NoteCategories;
import com.example.utilitycalendar.Database.Notes;
import com.example.utilitycalendar.Database.Notification;
import com.example.utilitycalendar.MainActivity;
import com.example.utilitycalendar.R;
import com.example.utilitycalendar.home.DayHomeAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import androidx.appcompat.app.AppCompatActivity;

public class NoteFragment extends Fragment  implements NoteAdapter.OnNoteClickListener {
    private TextView categoryIdTextView;

    private RecyclerView recyclerView;
    private NoteAdapter adapter;

    Database database = MainActivity.appDatabase;


    public BottomSheetManager bottomSheetManager;




    @Override
    public void onNoteClick(Notes notes) {

        // Truyền dữ liệu qua Bundle
        // Truyền id của category
        bottomSheetManager = new BottomSheetManager((AppCompatActivity) requireActivity());


        bottomSheetManager.showEditNote(notes, this);


    }


    @Override
    public void onDeleteClick(int id) {
        new AlertDialog.Builder(getContext())
                .setTitle("Xác nhận")
                .setMessage("Bạn có muốn xóa ghi chú không?")
                .setPositiveButton("Có", (dialog, which) -> {
                    // Xử lý khi người dùng chọn "Yes"
                    Executor executor = Executors.newSingleThreadExecutor();
                    executor.execute(() -> {
                        // Xử lý khi người dùng nhấn vào nút xóa
                        Notes notes = database.notesDao().getNotesById(id);
                        if (notes != null) {

                            database.notesDao().deleteNotes(notes);
                            database.noteCategoriesDao().updateRemoveNoteCategories(notes.getCate_name());
                            queryData();


                        }

                    });

                    dialog.dismiss();

                })
                .setNegativeButton("Không", (dialog, which) -> {
                    // Xử lý khi người dùng chọn "No"
                    dialog.dismiss(); // Đóng hộp thoại
                })
                .show();

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.note, container, false);

        categoryIdTextView = view.findViewById(R.id.text_view);
        recyclerView = view.findViewById(R.id.note_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));


        queryData();

//        noteList = new ArrayList<>();
        // Thêm dữ liệu mẫu


        return view;


    }




    public void queryData(){
        if (getArguments() != null) {
            String categoryId = getArguments().getString("categoryId");
            // Hiển thị id
            Executor executor = Executors.newSingleThreadExecutor();

            executor.execute(() -> {
                // Thêm dữ liệu mẫu vào database (nếu cần)


                // Lấy tất cả dữ liệu từ database
                List<Notes> notes = database.notesDao().getNotesByCateId(categoryId);

                getActivity().runOnUiThread(() -> {
                    if (notes.isEmpty()) {

                        adapter = new NoteAdapter(getContext(), notes, (NoteAdapter.OnNoteClickListener) this);
                        recyclerView.setAdapter(adapter);
                    } else {
                        adapter = new NoteAdapter(getContext(), notes, (NoteAdapter.OnNoteClickListener) this);

                        recyclerView.setAdapter(adapter);
                    }
                });

                // Cập nhật UI trên main thread

            });


        }
    }



}
