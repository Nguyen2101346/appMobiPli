package com.example.utilitycalendar.note;

public class NoteCategory {
    private String id;
    private String name;
    private int noteCount;
    private String icon;

    public NoteCategory(String id, String name, int noteCount, String icon) {
        this.id = id;
        this.name = name;
        this.noteCount = noteCount;
        this.icon = icon;
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

    public String getIcon() {
        return icon;
    }

}
