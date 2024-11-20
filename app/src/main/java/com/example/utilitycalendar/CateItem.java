package com.example.utilitycalendar;
public class CateItem {
    private final int iconResId;
    private final String hiddenName; // Tên ẩn

    public CateItem(int iconResId, String hiddenName) {
        this.iconResId = iconResId;
        this.hiddenName = hiddenName;
    }

    public int getIconResId() {
        return iconResId;
    }

    public String getHiddenName() {
        return hiddenName;
    }
}
