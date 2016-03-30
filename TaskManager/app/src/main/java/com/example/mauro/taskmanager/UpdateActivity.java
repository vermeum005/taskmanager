package com.example.mauro.taskmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLException;

public class UpdateActivity extends AppCompatActivity {

    public EditText updateTitle, updateDatum, updateTime;

    private DataSource datasource;
    private long taskId;

    private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        updateTitle = (EditText) findViewById(R.id.updateEditTitle);
        updateDatum = (EditText) findViewById(R.id.updateEditDatum);
        updateTime = (EditText) findViewById(R.id.updateEditTime);

        datasource = new DataSource(this);
        taskId = getIntent().getLongExtra(MainActivity.EXTRA_TASK_ID, -1);
        try {
            task = datasource.getTask(taskId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String title = task.getTask();

        updateTitle.setText(task.getTask());
        updateDatum.setText(task.getTaskDatum());
        updateTime.setText(task.getTaskTime());



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                task.setTask(updateTitle.getText().toString());
                task.setTaskDatum(updateDatum.getText().toString());
                task.setTaskTime(updateTime.getText().toString());

                try {
                    datasource.updateTask(task);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                Toast.makeText(UpdateActivity.this, "Task updated", Toast.LENGTH_LONG).show();

                Intent resultIntent = new Intent(UpdateActivity.this, MainActivity.class);
                setResult(Activity.RESULT_OK, resultIntent);
                startActivity(resultIntent);
                finish();
            }
        });
    }

}
