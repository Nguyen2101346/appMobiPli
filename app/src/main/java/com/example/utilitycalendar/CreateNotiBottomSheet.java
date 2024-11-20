package com.example.utilitycalendar;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class CreateNotiBottomSheet extends BottomSheetDialog {

    private DatePickerHelper datePickerHelper;
    private TimePickerHelper timePickerHelper;
    private EditText dateText;
    private EditText timeText;
    private BottomSheetManager bottomSheetManager;

    public CreateNotiBottomSheet(Context context, DatePickerHelper datePickerHelper,TimePickerHelper timePickerHelper,BottomSheetManager bottomSheetManager) {
        super(context);
        this.datePickerHelper = datePickerHelper;
        this.timePickerHelper = timePickerHelper;
        this.bottomSheetManager = bottomSheetManager;

        setupLayout();
    }

    private void setupLayout() {
        View createNotiView = getLayoutInflater().inflate(R.layout.create_noti, null);
        setContentView(createNotiView);
        // Lấy Ngày
        dateText = createNotiView.findViewById(R.id.editTextDate);
        datePickerHelper.initializeDatePicker(dateText);
        createNotiView.findViewById(R.id.btn_DatePicker).setOnClickListener(v -> datePickerHelper.showDatePicker());
        // Lấy giờ
        timeText = createNotiView.findViewById(R.id.editTextTime);
        createNotiView.findViewById(R.id.btn_TimePicker).setOnClickListener(v -> timePickerHelper.openTimePicker(timeText));
        // Xử lý nút back
        createNotiView.findViewById(R.id.backButton).setOnClickListener(v -> {
            dismiss(); // Đóng CreateNotiBottomSheet
            bottomSheetManager.showMainBottomSheet(); // Hiển thị lại bottomSheetDialog
        });

        ImageView notiIcon = createNotiView.findViewById(R.id.notiIcon);
        notiIcon.setOnClickListener(v -> toggleIcon(notiIcon));

        // Cài đặt ColorPicker
        setupColorPicker(createNotiView);
    }

    private void toggleIcon(ImageView icon) {
        // Kiểm tra nếu ImageView có tag không
        Boolean isNotified = (Boolean) icon.getTag();
        if (isNotified == null || !isNotified) {
            icon.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_noti_45));
            icon.setTag(true); // Đánh dấu icon đã hiển thị thông báo
        } else {
            icon.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_unnoti_45));
            icon.setTag(false); // Đánh dấu icon đã tắt thông báo
        }
    }

    private void setupColorPicker(View createNotiView) {
        // Tìm RadioGroup trong layout
        RadioGroup colorRadioGroup = createNotiView.findViewById(R.id.radioGroup);
        // Tìm ImageView để thay đổi màu
        ImageView checkColor = createNotiView.findViewById(R.id.checkColor);
        // Khởi tạo ColorManager
        ColorManager colorManager = new ColorManager();

        // Tạo RadioGroup với danh sách màu
        colorManager.createColorRadioGroup(getContext(), colorRadioGroup, selectedColor -> {
            // Xử lý khi màu được chọn
            Toast.makeText(getContext(), "Màu đã chọn: " + selectedColor.getName(), Toast.LENGTH_SHORT).show();

            // Thay đổi màu hình của ImageView checkColor
            changeImageColor(checkColor, selectedColor.getHexCode());
        });
    }
    private void changeImageColor(ImageView imageView, String hexColor) {
        // Lấy Drawable hiện tại của ImageView
        Drawable drawable = imageView.getDrawable();

        if (drawable == null) {
            // Nếu chưa có Drawable, gán một Drawable mặc định vào ImageView
            drawable = ContextCompat.getDrawable(imageView.getContext(), R.drawable.ic_color_lens_50);
            imageView.setImageDrawable(drawable);
        }

        // Tạo bản sao của Drawable hiện tại để tránh thay đổi chung cho tất cả các ImageView
        Drawable drawableCopy = drawable.mutate();

        // Áp dụng màu mới cho bản sao của Drawable
        drawableCopy.setTint(Color.parseColor(hexColor));

        // Gán lại Drawable cho ImageView
        imageView.setImageDrawable(drawableCopy);
    }
}