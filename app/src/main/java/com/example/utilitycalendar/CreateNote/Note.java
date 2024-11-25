package com.example.utilitycalendar.CreateNote;

public class Note {
    private String name;             // Tên sự kiện
    private String date;             // Ngày của thông báo
    private String time;             // Thời gian của thông báo
    private String notes;          // Chi tiết sự kiện
    private String colorHex;         // Mã màu của thông báo
    private String Categories;       // Danh mục ghi chú muốn lưu

    public Note(String name, String date, String time, String notes, String colorHex, String Categories) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.notes = notes;
        this.colorHex = colorHex;
        this.Categories = Categories;
    }

    // Getter và Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDetails() {
        return notes;
    }

    public void setDetails(String details) {
        this.notes = notes;
    }

    public String getColorHex() {
        return colorHex;
    }

    public void setColorHex(String colorHex) {
        this.colorHex = colorHex;
    }

    public String getCategories() {
        return Categories;
    }

    public void setCategories(String Categories) {
        this.Categories = Categories;
    }

    @Override
    public String toString() {
        return "Noti{" +
                "name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", details='" + notes + '\'' +
                ", colorHex='" + colorHex + '\'' +
                ", bellIconResId=" + Categories +
                '}';
    }
}
