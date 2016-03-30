package com.example.mauro.taskmanager;

/**
 * Created by mauro on 20/03/2016.
 */
public class ListItem {
    private String title;
    private String datum;
    private String time;

    //Constructor
    public ListItem(String title, String datum, String time) {
        this.title = title;
        this.datum = datum;
        this.time = time;
    }

    //Getters
    public String getTitle() {
        return title;
    }

    public String getDatum() {
        return datum;
    }

    public String getTimeResource() {
        return time;
    }
}
