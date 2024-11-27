package com.example.utilitycalendar.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NoteCategoriesDao {
    @Insert
    void insertNoteCategories(NoteCategories noteCategories);


    @Query("SELECT * FROM Note_category")
    List<NoteCategories> getAllNoteCategories();

    @Query("DELETE FROM Note_category")
    void deleteAllNoteCategories();

    @Query("UPDATE Note_category SET Note_count = Note_count + 1 WHERE Note_id = :categoryId")
    void updateNoteCategories(String categoryId);

    @Query("UPDATE Note_category SET Note_count = Note_count - 1 WHERE Note_id = :categoryId")
    void updateRemoveNoteCategories(String categoryId);


}
