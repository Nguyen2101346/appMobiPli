package com.example.utilitycalendar.CreateNote;

import android.content.Context;
import android.graphics.drawable.Drawable;
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
import androidx.core.content.ContextCompat;

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
import com.example.utilitycalendar.Helper.TimePickerHelper;
import com.example.utilitycalendar.MainActivity;
import com.example.utilitycalendar.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import android.graphics.Color;

public class CreateNoteBottomSheet extends BottomSheetDialog {

    private DatePickerHelper datePickerHelper;
    private TimePickerHelper timePickerHelper;
    private EditText dateText;
    private EditText timeText;
    private BottomSheetManager bottomSheetManager;
    private String selectedCategory = "Hoctap";
    private String selectedColorHex = "#EB8585";
    private int pinned = 1;

    public interface OnSaveListener {
        void onSave(Note newNoteData);
    }

    private OnSaveListener onSaveListener;

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

        // Lấy các trường thông tin
        EditText editTextName = createNoteView.findViewById(R.id.editTextName);
        dateText = createNoteView.findViewById(R.id.editTextDate);
        timeText = createNoteView.findViewById(R.id.editTextTime);
        EditText editTextNote = createNoteView.findViewById(R.id.editTextNote);

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
        //  Bật/Tắt của phần chọn ghim
        Switch pinSwitch = createNoteView.findViewById(R.id.PinSwitch);
        pinSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                pinned = 1;
            } else {
                pinned = 0;
            }

            Log.d("Check pin", "Pinned value: " + pinned);
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

        // Xử lý nút Lưu
        ImageView btnSave = createNoteView.findViewById(R.id.btn_Save);
        btnSave.setOnClickListener(v -> {

            String name = editTextName.getText().toString();
            String date = dateText.getText().toString();
            String time = timeText.getText().toString();
            String Notes = editTextNote.getText().toString();

            // Kiểm tra thông tin nhập vào
            if (name.isEmpty() || Notes.isEmpty()) {
                Toast.makeText(getContext(), "Hãy điền tiêu đề và tên ghi chú!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Định dạng ngày và giờ
            String dateInputFormat = "EEEE - dd/MM/yyyy";  // Định dạng ngày
            String timeInputFormat = "hh 'giờ' mm 'phút' a"; // Định dạng giờ (Sáng/Chiều)
            String dateOutputFormat = "dd-MM-yyyy";  // Định dạng ngày đầu ra
            String timeOutputFormat = "HH:mm";  // Định dạng giờ đầu ra
            Date Finaldate;


            SimpleDateFormat dateInputFormatter = new SimpleDateFormat(dateInputFormat,new Locale("vi", "VN"));
            SimpleDateFormat timeInputFormatter = new SimpleDateFormat(timeInputFormat,Locale.ENGLISH);
            SimpleDateFormat dateOutputFormatter = new SimpleDateFormat(dateOutputFormat,Locale.ENGLISH);
            SimpleDateFormat timeOutputFormatter = new SimpleDateFormat(timeOutputFormat,Locale.ENGLISH);

            try {
                // Làm sạch chuỗi ngày và giờ trước khi phân tích
                date = date.trim();
                time = time.trim();
                time = time.replace("Sáng", "AM").replace("Chiều", "PM");
//                Log.d("Time After Replace", "Giờ sau khi thay thế: " + time);
//                Log.d("Date After Replace", "Ngày sau khi thay thế: " + date);
                String formattedDate;
                String formattedTime;
                // Kiểm tra nếu định dạng ngày khớp với mẫu
                try {
                    Date Tdate = dateInputFormatter.parse(date);  // Kiểm tra ngày
                    formattedDate = dateOutputFormatter.format(Tdate);
                    Log.d("Fuck u","Chuỗi ngày đã chuyển đổi: " + formattedDate);
                } catch (ParseException e) {
                    Toast.makeText(getContext(), "Định dạng ngày không hợp lệ! Hãy kiểm tra lại.", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                    return;  // Dừng lại nếu ngày sai
                }
                try {
                    // Định dạng đúng và phân tích chuỗi giờ
//                    SimpleDateFormat timeInputFormatter = new SimpleDateFormat("hh 'giờ' mm 'phút' a", Locale.ENGLISH);
                    Date TTime = timeInputFormatter.parse(time);
                    // Lưu thời gian đã phân tích
                    formattedTime = timeOutputFormatter.format(TTime);
                    Log.d("Fuck u","Chuỗi ngày đã chuyển đổi: " + formattedTime);
                    // Kiểm tra giờ
                } catch (ParseException e) {
                    Toast.makeText(getContext(), "Định dạng giờ không hợp lệ! Hãy kiểm tra lại.", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                    return;  // Dừng lại nếu giờ sai
                }

                String dataDate = formattedDate + " " + formattedTime;
                Log.d("Data Date", "Ngày và giờ lưu: " + dataDate);

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
                Finaldate = dateFormat.parse(dataDate);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }

            // Tạo đối tượng Noti
            Note newNoteData = new Note(name, date, time, Notes, selectedColorHex, selectedCategory);

            // Gửi dữ liệu qua listener
            if (onSaveListener != null) {
                onSaveListener.onSave(newNoteData);
            }

            // Lưu vào cơ sở dữ liệu
            Executor executor = Executors.newSingleThreadExecutor();
            executor.execute(() -> {
                Database database = MainActivity.appDatabase;

                Notes Note = new Notes(0, selectedCategory, name, Notes, Finaldate , new Date(), pinned);
                database.notesDao().insertNotes(Note);

                List<Notes> listNote = database.notesDao().getAllNotes();
                for (Notes ItemNote : listNote) {
                    Log.d("Notification Check", "Ngày: " + ItemNote.getNoteDate() + " | Giờ: " + ItemNote.getNoteTime() + " | Danh mục:  " + ItemNote.getCate_name());
                }
            });

            // Hiển thị thông báo thành công
            Toast.makeText(getContext(), "Đã lưu ghi chú: " + name, Toast.LENGTH_SHORT).show();
            dismiss(); // Đóng BottomSheet sau khi lưu
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

            selectedColorHex = selectedColor.getHexCode();

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

    // Các lớp wrapper cho Date và Time
    class DateWrapper {
        private Date date;
        public Date getDate() { return date; }
        public void setDate(Date date) { this.date = date; }
    }

    class TimeWrapper {
        private Date time;
        public Date getTime() { return time; }
        public void setTime(Date time) { this.time = time; }
    }
}
