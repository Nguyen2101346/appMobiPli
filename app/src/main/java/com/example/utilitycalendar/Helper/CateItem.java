package com.example.utilitycalendar.Helper;
public class CateItem {
    private final int iconResId;
    private final String hiddenName; // Tên ẩn
    private final String selectedCategory;

    public CateItem(int iconResId, String hiddenName, String selectedCategory) {
        this.iconResId = iconResId;
        this.hiddenName = hiddenName;
        this.selectedCategory = selectedCategory;
    }

    public int getIconResId() {
        return iconResId;
    }

    public String getHiddenName() {
        return hiddenName;
    }

    public String getSelectedCategory() {
        return selectedCategory;
    }
}