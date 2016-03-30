package com.example.mauro.taskmanager;

/**
 * Created by mauro on 21/03/2016.
 */
public class Task {
    private long id;
    private String task, taskDatum, taskTime;

    public Task(){

    }
    public Task(String task, String taskDatum, String taskTime){
        this.task = task;
        this.taskDatum = taskDatum;
        this.taskTime = taskTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getTaskDatum() {
        return taskDatum;
    }

    public void setTaskDatum(String taskDatum) {
        this.taskDatum = taskDatum;
    }

    public String getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(String taskTime) {
        this.taskTime = taskTime;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return task + taskDatum + taskTime;
    }

}

