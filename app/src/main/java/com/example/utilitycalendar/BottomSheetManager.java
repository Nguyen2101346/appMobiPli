// BottomSheetManager.java

package com.example.utilitycalendar;

import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

import com.example.utilitycalendar.CreateNote.CreateNoteBottomSheet;
import com.example.utilitycalendar.CreateNoti.CreateNotiBottomSheet;
import com.example.utilitycalendar.Helper.DatePickerHelper;
import com.example.utilitycalendar.Helper.TimePickerHelper;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class BottomSheetManager {

    private final AppCompatActivity activity;
    private BottomSheetDialog bottomSheetDialog;
    private DatePickerHelper datePickerHelper;
    private TimePickerHelper timePickerHelper;

    public BottomSheetManager(AppCompatActivity activity) {
        this.activity = activity;
        datePickerHelper = new DatePickerHelper(activity);
        timePickerHelper = new TimePickerHelper(activity);
    }

    public void showBottomSheet() {
        bottomSheetDialog = new BottomSheetDialog(activity);
        View bottomSheetView = activity.getLayoutInflater().inflate(R.layout.bottomsheetlayout, null);
        bottomSheetDialog.setContentView(bottomSheetView);

        bottomSheetView.findViewById(R.id.layoutNoti).setOnClickListener(v -> openCreateNotiBottomSheet());
        bottomSheetView.findViewById(R.id.layoutNote).setOnClickListener(v -> openCreateNoteBottomSheet());

        bottomSheetView.findViewById(R.id.cancelButton).setOnClickListener(v -> bottomSheetDialog.dismiss());
        bottomSheetDialog.show();
    }

    private void openCreateNotiBottomSheet() {
        // Truyền cả DatePickerHelper và TimePickerHelper vào constructor mới
        CreateNotiBottomSheet createNotiBottomSheet = new CreateNotiBottomSheet(activity, datePickerHelper, timePickerHelper, this);
        createNotiBottomSheet.show();
        bottomSheetDialog.dismiss();
    }
    private void openCreateNoteBottomSheet() {
        // Truyền cả DatePickerHelper và TimePickerHelper vào constructor mới
        CreateNoteBottomSheet createNoteBottomSheet = new CreateNoteBottomSheet(activity, datePickerHelper, timePickerHelper, this);
        createNoteBottomSheet.show();
        bottomSheetDialog.dismiss();
    }

    public void showMainBottomSheet() {
        if (bottomSheetDialog != null && !bottomSheetDialog.isShowing()) {
            bottomSheetDialog.show();
        }
    }
}
