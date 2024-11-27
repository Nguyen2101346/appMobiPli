package com.example.utilitycalendar.CreateNote;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.utilitycalendar.BottomSheetManager;
import com.example.utilitycalendar.CreateNoti.CreateNotiBottomSheet;
import com.example.utilitycalendar.CreateNoti.Noti;
import com.example.utilitycalendar.Database.Database;
import com.example.utilitycalendar.Database.Notes;
import com.example.utilitycalendar.Database.Notification;
import com.example.utilitycalendar.Helper.CateAdapter;
import com.example.utilitycalendar.Helper.CateItem;
import com.example.utilitycalendar.Helper.ColorManager;
import com.example.utilitycalendar.Helper.DatePickerHelper;
import com.example.utilitycalendar.Helper.SettingFragment;
import com.example.utilitycalendar.Helper.TimePickerHelper;
import com.example.utilitycalendar.MainActivity;
import com.example.utilitycalendar.NoteFragment;
import com.example.utilitycalendar.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ShowEditNote extends BottomSheetDialog{

    private BottomSheetManager bottomSheetManager;
    private Notes notes;
    private Context context;
    private NoteFragment noteFragment;

    public ShowEditNote(Context context,Notes notes, BottomSheetManager bottomSheetManager) {
        super(context);
        this.context = context;
        this.notes = notes;
        this.bottomSheetManager = bottomSheetManager;

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



                dismiss();

                // Hiển thị thông báo thành công






            });
        });
    }
}
