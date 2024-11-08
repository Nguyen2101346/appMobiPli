package com.example.utilitycalendar.note;

public class NoteCategory {
    private String id;
    private String name;
    private int noteCount;

    public NoteCategory(String id, String name, int noteCount) {
        this.id = id;
        this.name = name;
        this.noteCount = noteCount;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getNoteCount() {
        return noteCount;
    }
}
