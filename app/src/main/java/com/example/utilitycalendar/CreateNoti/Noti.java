package com.example.utilitycalendar.CreateNoti;

public class Noti {

    private String name;             // Tên sự kiện
    private String date;             // Ngày của thông báo
    private String time;             // Thời gian của thông báo
    private String details;          // Chi tiết sự kiện
    private String colorHex;         // Mã màu của thông báo
    private int bellIconResId;       // Resource ID cho biểu tượng chuông

    public Noti(String name, String date, String time, String details, String colorHex, int bellIconResId) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.details = details;
        this.colorHex = colorHex;
        this.bellIconResId = bellIconResId;
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
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getColorHex() {
        return colorHex;
    }

    public void setColorHex(String colorHex) {
        this.colorHex = colorHex;
    }

    public int getBellIcon() {
        return bellIconResId;
    }

    public void setBellIcon(int bellIconResId) {
        this.bellIconResId = bellIconResId;
    }

    @Override
    public String toString() {
        return "Noti{" +
                "name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", details='" + details + '\'' +
                ", colorHex='" + colorHex + '\'' +
                ", bellIconResId=" + bellIconResId +
                '}';
    }
}