package com.example.utilitycalendar.Flag;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.utilitycalendar.Database.Converters;
import com.example.utilitycalendar.Database.Database;
import com.example.utilitycalendar.Database.Notes;
import com.example.utilitycalendar.MainActivity;
import com.example.utilitycalendar.R;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class FlagFragment extends Fragment {

    //private TextView flagIdTextView;

    private RecyclerView recyclerView;
    private FlagAdapter adapter;

    Database database = MainActivity.appDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.flag, container, false);

        //flagIdTextView = view.findViewById(R.id.text_view);
        recyclerView = view.findViewById(R.id.flag_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        Executor executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {
            // Thêm dữ liệu mẫu vào database (nếu cần)
            // Lấy tất cả dữ liệu từ database
            List<Notes> notes = database.notesDao().getNoteOnDate(new Date());

            // Cập nhật UI trên main thread
            getActivity().runOnUiThread(() -> {
                if (notes.isEmpty()) {
                    Log.d("FlagFragment", "noteList is empty");
                } else {
                    adapter = new FlagAdapter(getContext(), notes, note -> {
                        // Xử lý khi nhấn vào một ghi chú


                    });

                    recyclerView.setAdapter(adapter);
                }
            });
        });

        return view;
    }

}
