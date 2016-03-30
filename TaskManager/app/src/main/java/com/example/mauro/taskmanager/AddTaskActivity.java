package com.example.mauro.taskmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLException;

public class AddTaskActivity extends AppCompatActivity {

    private EditText newTitle, newDatum, newTime;
    private DataSource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        newTitle = (EditText) findViewById(R.id.editTitleText);
        newDatum = (EditText) findViewById(R.id.editDateText);
        newTime = (EditText) findViewById(R.id.editTimeText);

        datasource = new DataSource(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(newTitle.getText()) && !TextUtils.isEmpty(newDatum.getText())
                        && !TextUtils.isEmpty(newTime.getText())) {

                    long assignmentId = 0;
                    try {
                        assignmentId = datasource.createTask(newTitle.getText().toString(), newDatum.getText().toString(), newTime.getText().toString());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    Intent resultIntent = new Intent();
                    resultIntent.putExtra(MainActivity.EXTRA_TASK_ID, assignmentId);
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();

                } else {
                    Toast.makeText(AddTaskActivity.this, "Please enter some text in the title and description fields", Toast.LENGTH_LONG).show();

                }
            }
        });
    }

}
