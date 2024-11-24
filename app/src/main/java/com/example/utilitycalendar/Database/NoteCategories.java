package com.example.utilitycalendar.Database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

@Entity(tableName = "Note_category", indices = {@Index(value = "Note_id", unique = true)})
public class NoteCategories {
    @PrimaryKey
    @NonNull
    public String Note_id;
    public String Cate_name;
    public int Note_count;
    public String Icon;

    public String getCate_name() {
        return Cate_name;
    }

    public void setCate_name(String cate_name) {
        Cate_name = cate_name;
    }

    public String getIcon() {
        return Icon;
    }

    public void setIcon(String icon) {
        Icon = icon;
    }

    public int getNote_count() {
        return Note_count;
    }

    public void setNote_count(int note_count) {
        Note_count = note_count;
    }

    public String getNote_id() {
        return Note_id;
    }

    public void setNote_id(String note_id) {
        Note_id = note_id;
    }



    public NoteCategories(String note_id, String cate_name, int note_count, String icon) {
        Note_id = note_id;
        Icon = icon;
        Note_count = note_count;
        Cate_name = cate_name;
    }

    public NoteCategories() {
        // constructor này sẽ được sử dụng khi Room tạo đối tượng từ cơ sở dữ liệu
    }




}
