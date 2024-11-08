package com.example.utilitycalendar.note;

public class Note {


    private String id;
    private String title;
    private String content;
    private String date;


    public Note(String id, String title, String content, String date) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
    }


    public String getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }



}
