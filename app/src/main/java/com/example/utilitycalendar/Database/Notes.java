package com.example.utilitycalendar.Database;

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
}
