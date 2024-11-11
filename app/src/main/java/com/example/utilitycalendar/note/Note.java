package com.example.utilitycalendar.note;

public class Note {
    private String id;
    private String title;
    private String content;
    private String date;
    private boolean pin;



    public Note(String id, String title, String content, String date, boolean pin) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
        this.pin = pin;
    }



    public String getTitle() {
        return title;
    }

    public boolean isPin() {
        return pin;
    }

    public String getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }

    public String getId() {
        return id;
    }







}
