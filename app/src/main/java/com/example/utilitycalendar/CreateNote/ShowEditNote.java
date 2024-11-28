package com.example.utilitycalendar.CreateNote;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.utilitycalendar.BottomSheetManager;
import com.example.utilitycalendar.Database.Database;
import com.example.utilitycalendar.Database.Notes;
import com.example.utilitycalendar.MainActivity;
import com.example.utilitycalendar.R;
import com.example.utilitycalendar.note.NoteFragment;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ShowEditNote extends BottomSheetDialog{

    private BottomSheetManager bottomSheetManager;
    private Notes notes;
    private Context context;
    private NoteFragment noteFragment;

    public ShowEditNote(Context context,Notes notes, BottomSheetManager bottomSheetManager, NoteFragment noteFragment) {
        super(context);
        this.context = context;
        this.notes = notes;
        this.bottomSheetManager = bottomSheetManager;
        this.noteFragment = noteFragment;
        setupLayout();
    }
    private void setupLayout(){
        View view = getLayoutInflater().inflate(R.layout.show_note, null);


        EditText title = view.findViewById(R.id.editTextName);
        EditText content = view.findViewById(R.id.editTextNote);
        title.setText(notes.getTittle());
        content.setText(notes.getContent());

        setContentView(view);


//        ImageView saveBtn = view.findViewById(R.id.btn_Save);

        view.findViewById(R.id.backButton).setOnClickListener(v -> {
            dismiss(); // Đóng CreateNoteBottomSheet
            bottomSheetManager.showMainBottomSheet(); // Hiển thị lại BottomSheet chính
        });

        view.findViewById(R.id.btn_Save).setOnClickListener(v ->{
            String titleTxt = title.getText().toString();
            String contentTxt = content.getText().toString();
            Log.d("Check edit", titleTxt);

            if(titleTxt.isEmpty() || contentTxt.isEmpty()){
                Toast.makeText(getContext(), "Vui lòng nhập tên và nội dung ghi chú", Toast.LENGTH_SHORT).show();
                return;
            }else{
                notes.setTittle(titleTxt);
                notes.setContent(contentTxt);
            }

            Executor executor = Executors.newSingleThreadExecutor();
            executor.execute(() -> {
                Database database = MainActivity.appDatabase;



                database.notesDao().updateNotes(notes);
                noteFragment.queryData(); // Cập nhật lại dữ liệu trên Adapter


            });


            Toast.makeText(getContext(), "Đã cập nhật ghi chú thành công", Toast.LENGTH_SHORT).show();
            dismiss();


        });
    }
}
