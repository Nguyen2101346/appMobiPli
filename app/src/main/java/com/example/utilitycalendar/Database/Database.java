package com.example.utilitycalendar.Database;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.content.Context;

import java.util.List;

@androidx.room.Database(entities = {Notification.class, Notes.class, NoteCategories.class}, version = 4, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class Database extends RoomDatabase {

    // Abstract methods to get DAO instances
    public abstract NotificationDao notificationDao();
    public abstract NotesDao notesDao();

    public abstract NoteCategoriesDao noteCategoriesDao();

    // Singleton instance of the database
    private static volatile Database INSTANCE;

    public static Database getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (Database.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    Database.class, "app_database")
                            .addCallback(new RoomDatabase.Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    // Thêm dữ liệu mẫu khi cơ sở dữ liệu được tạo
                                    new Thread(() -> {
                                        addDefaultData(INSTANCE);
                                    }).start();
                                }
                            })
                            .fallbackToDestructiveMigration()
                            .build();
                }
                }
        }
        return INSTANCE;
    }



    private static void addDefaultData(Database database) {
        // Kiểm tra nếu bảng còn trống và thêm dữ liệu mặc định
        List<NoteCategories> categories = database.noteCategoriesDao().getAllNoteCategories();
        if (categories.isEmpty()) {
            // Nếu bảng trống, thêm dữ liệu mẫu
            NoteCategories newCategory1 = new NoteCategories("hoctap", "Học tập", 5, "home_24px");
            NoteCategories newCategory2 = new NoteCategories("congviec", "Công việc", 5, "home_24px");
            NoteCategories newCategory3 = new NoteCategories("giaitri", "Giải trí", 5, "home_24px");
            NoteCategories newCategory4 = new NoteCategories("thuongngay", "Thường ngày", 5, "home_24px");
            database.noteCategoriesDao().insertNoteCategories(newCategory1);
            database.noteCategoriesDao().insertNoteCategories(newCategory2);
            database.noteCategoriesDao().insertNoteCategories(newCategory3);
            database.noteCategoriesDao().insertNoteCategories(newCategory4);
        }
    }



}