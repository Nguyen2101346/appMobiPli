package com.example.utilitycalendar.Helper;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.List;

public class ColorManager {

    // Class để lưu thông tin màu
    public static class ColorItem {
        private String name;
        private String hexCode;

        public ColorItem(String name, String hexCode) {
            this.name = name;
            this.hexCode = hexCode;
        }

        public String getName() {
            return name;
        }

        public String getHexCode() {
            return hexCode;
        }
    }

    public interface OnColorSelectedListener {
        void onColorSelected(ColorItem selectedColor);
    }

    // Tạo RadioGroup với danh sách màu
    public void createColorRadioGroup(Context context, RadioGroup radioGroup, OnColorSelectedListener listener) {
        List<ColorItem> colors = List.of(
                new ColorItem("Red", "#EB8585"),
                new ColorItem("Green", "#A4E5B6"),
                new ColorItem("Blue", "#77F9F9"),
                new ColorItem("Yellow", "#F6DF9B"),
                new ColorItem("Purple", "#D37FFD")
        );

        for (ColorItem color : colors) {
            RadioButton radioButton = new RadioButton(context);
            radioButton.setButtonDrawable(null); // Loại bỏ icon mặc định của RadioButton
            radioButton.setBackground(createColorCircle(color.getHexCode())); // Thêm vòng tròn màu
            // Cài đặt kích thước và padding cho RadioButton
            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(
                    30,
//                    100,
//                    100
//                    RadioGroup.LayoutParams.WRAP_CONTENT,
                RadioGroup.LayoutParams.WRAP_CONTENT
            );
//            params.setMargins(20, 20, 20, 20);
            params.setMargins(15, 20, 20, 15);
            radioButton.setPadding(20, 20, 20, 20);
            radioButton.setLayoutParams(params);

            // Gán sự kiện khi chọn màu
            radioButton.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onColorSelected(color);
                }
            });

            // Thêm RadioButton vào RadioGroup
            radioGroup.addView(radioButton);
        }
    }

    // Tạo drawable vòng tròn màu
    private GradientDrawable createColorCircle(String hexCode) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.OVAL);
        drawable.setColor(Color.parseColor(hexCode));
//        drawable.setSize(50, 50); // Kích thước vòng tròn
        drawable.setSize(30, 30);
        return drawable;
    }
}
