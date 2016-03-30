package com.example.mauro.taskmanager;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by mauro on 26/03/2016.
 */
public class ViewHolder {

    private TextView task, taskDatum, taskTime;

    public ViewHolder(View view){
        task = (TextView) view.findViewById(R.id.title);
        taskDatum = (TextView) view.findViewById(R.id.datum);
        taskTime = (TextView) view.findViewById(R.id.time);
    }

    public void populateRow(Task task){

        this.task.setText(task.getTask());
        taskDatum.setText(task.getTaskDatum());
        taskTime.setText(task.getTaskTime());
    }
}
