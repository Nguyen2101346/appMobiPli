package com.example.utilitycalendar.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NotesDao {
    @Insert
    void insertNotes(Notes notes);

    @Update
    void updateNotes(Notes notes);

    @Delete
    void deleteNotes(Notes notes);

    @Query("SELECT * FROM Notes WHERE Note_id = :id")
    Notes getNotesById(int id);

    @Query("SELECT * FROM Notes WHERE Cate_name = :cate_id")
    List<Notes> getNotesByCateId(String cate_id);

    @Query("SELECT * FROM Notes")
    List<Notes> getAllNotes();

    // Phương thức lấy số lượng hàng
    @Query("SELECT COUNT(*) FROM Notes")
    int getNotesCount();
}
