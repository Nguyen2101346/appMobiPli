package com.example.utilitycalendar.CreateNote;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.RadioGroup;
import androidx.core.content.ContextCompat;

import com.example.utilitycalendar.BottomSheetManager;
import com.example.utilitycalendar.Helper.CateAdapter;
import com.example.utilitycalendar.Helper.CateItem;
import com.example.utilitycalendar.Helper.ColorManager;
import com.example.utilitycalendar.Helper.DatePickerHelper;
import com.example.utilitycalendar.Helper.TimePickerHelper;
import com.example.utilitycalendar.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import java.util.ArrayList;
import java.util.List;
import android.graphics.Color;

public class CreateNoteBottomSheet extends BottomSheetDialog {

    private DatePickerHelper datePickerHelper;
    private TimePickerHelper timePickerHelper;
    private EditText dateText;
    private EditText timeText;
    private BottomSheetManager bottomSheetManager;
    private String selectedCategory;

    public CreateNoteBottomSheet(Context context, DatePickerHelper datePickerHelper, TimePickerHelper timePickerHelper, BottomSheetManager bottomSheetManager) {
        super(context);
        this.datePickerHelper = datePickerHelper;
        this.timePickerHelper = timePickerHelper;
        this.bottomSheetManager = bottomSheetManager;
        setupLayout();
    }

    private void setupLayout() {
        View createNoteView = getLayoutInflater().inflate(R.layout.create_note, null);
        setContentView(createNoteView);

        // Cài đặt DatePicker và TimePicker
        // datePickerHelper.initializeDatePicker(dateText);
        // Lấy Ngày
        dateText = createNoteView.findViewById(R.id.editTextDate);
        datePickerHelper.initializeDatePicker(dateText);
        createNoteView.findViewById(R.id.editTextDate).setOnClickListener(v -> datePickerHelper.showDatePicker());
        // Lấy giờ
        timeText = createNoteView.findViewById(R.id.editTextTime);
        createNoteView.findViewById(R.id.editTextTime).setOnClickListener(v -> timePickerHelper.openTimePicker(timeText));

        // Xử lý nút quay lại
        createNoteView.findViewById(R.id.backButton).setOnClickListener(v -> {
            dismiss(); // Đóng CreateNoteBottomSheet
            bottomSheetManager.showMainBottomSheet(); // Hiển thị lại BottomSheet chính
        });

        // Bật/Tắt layout của phần chọn thời gian
        Switch timeSwitch = createNoteView.findViewById(R.id.timeSwitch);
        LinearLayout timeOptionsLayout = createNoteView.findViewById(R.id.timeOptionsLayout);
        timeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                timeOptionsLayout.setVisibility(View.VISIBLE);
            } else {
                timeOptionsLayout.setVisibility(View.GONE);
            }
        });

        // Bật/Tắt layout của phần thêm danh mục
        ImageView AddCate = createNoteView.findViewById(R.id.AddCate);
        ImageView cancel = createNoteView.findViewById(R.id.cancelButton);
        FrameLayout LayoutAddCate = createNoteView.findViewById(R.id.LayoutAddCate);

        AddCate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutAddCate.setVisibility(View.VISIBLE); // Hiển thị layout thêm danh mục
                AddCate.setVisibility(View.GONE); // Ẩn nút AddCate sau khi click
            }
        });

        // Xử lý nút hủy
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutAddCate.setVisibility(View.GONE); // Ẩn layout thêm danh mục
                AddCate.setVisibility(View.VISIBLE); // Hiển thị lại nút AddCate
            }
        });
        // Cài đặt ColorPicker
        setupColorPicker(createNoteView);

        GridView gridView = createNoteView.findViewById(R.id.gridLayout);

        // Prepare data for GridView items
        List<CateItem> cateItems = new ArrayList<>();
        cateItems.add(new CateItem(R.drawable.ic_cate_study_60, "Học tập"));
        cateItems.add(new CateItem(R.drawable.ic_cate_work_60, "Công việc"));
        cateItems.add(new CateItem(R.drawable.ic_cate_entertain_60, "Giải trí"));
        cateItems.add(new CateItem(R.drawable.ic_cate_calendar_50, "Thường ngày"));

        // Set up adapter for GridView
        CateAdapter adapter = new CateAdapter(getContext(), cateItems);
        gridView.setAdapter(adapter);

        // Chuyển đổi icon (tùy chọn)
        //        ImageView notiIcon = createNoteView.findViewById(R.id.notiIcon);
        //        notiIcon.setOnClickListener(v -> toggleIcon(notiIcon));

        gridView.setOnItemClickListener((parent, view, position, id) -> {
            CateItem selectedCate = cateItems.get(position); // Lấy item được chọn
            String hiddenName = selectedCate.getHiddenName(); // Lấy tên ẩn của item
            // Thay đổi ImageView của nút AddCate thành icon của danh mục đã chọn
            AddCate.setImageResource(selectedCate.getIconResId());

            // Ẩn layout thêm danh mục và hiển thị lại nút AddCate với icon mới
            LayoutAddCate.setVisibility(View.GONE);
            AddCate.setVisibility(View.VISIBLE);

            // Hiển thị thông báo về danh mục đã chọn (tùy chọn)
            Toast.makeText(getContext(), "Bạn đã chọn: " + selectedCate.getHiddenName(), Toast.LENGTH_SHORT).show();
        });
    }
    //Tạo màu trong RadioGroup
    private void setupColorPicker(View createNoteView) {
        // Tìm RadioGroup trong layout
        RadioGroup colorRadioGroup = createNoteView.findViewById(R.id.radioGroup);
        // Tìm ImageView để thay đổi màu
        ImageView checkColor = createNoteView.findViewById(R.id.checkColor);
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
