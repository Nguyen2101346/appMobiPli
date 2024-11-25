package com.example.utilitycalendar.Database;

import android.graphics.Color;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "Notes", indices = {@Index(value = "Note_id", unique = true)})
public class Notes {
    @PrimaryKey(autoGenerate = true)
    public int Note_id;



    public String Cate_name;
    public String Tittle;
    public String Content;

    @ColumnInfo(name = "Note_Date")
    public Date NoteDate;

    @ColumnInfo(name = "Note_Time")
    public Date NoteTime;

    public int Pinned;

    public String Color;
    public int getNote_id() {
        return Note_id;
    }

    public void setNote_id(int note_id) {
        Note_id = note_id;
    }

    public String getCate_name() {
        return Cate_name;
    }

    public void setCate_name(String cate_name) {
        Cate_name = cate_name;
    }

    public String getTittle() {
        return Tittle;
    }

    public void setTittle(String tittle) {
        Tittle = tittle;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public Date getNoteDate() {
        return NoteDate;
    }

    public void setNoteDate(Date noteDate) {
        NoteDate = noteDate;
    }

    public Date getNoteTime() {
        return NoteTime;
    }

    public void setNoteTime(Date noteTime) {
        NoteTime = noteTime;
    }

    public int getPinned() {
        return Pinned;
    }

    public void setPinned(int pinned) {
        Pinned = pinned;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public Notes(){

    }


    public Notes(int note_id, String cate_name, String tittle, String content, Date noteDate, Date noteTime, int pinned, String color) {
        Note_id = note_id;
        Cate_name = cate_name;
        Tittle = tittle;
        Content = content;
        NoteDate = noteDate;
        NoteTime = noteTime;
        Pinned = pinned;
        Color = color;
    }
}
