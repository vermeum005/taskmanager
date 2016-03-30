package com.example.mauro.taskmanager;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.sql.SQLException;

public class DetailsActivity extends AppCompatActivity {

    public TextView detailTitle, detailDatum, detailTime;
    public Button button;

    private DataSource datasource;
    private long taskId;
    private Task taskItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        button = (Button) findViewById(R.id.button);
        detailTitle = (TextView) findViewById(R.id.detailTitle);
        detailDatum = (TextView) findViewById(R.id.detailDatum);
        detailTime = (TextView) findViewById(R.id.detailTime);

        datasource = new DataSource(this);
        taskId = getIntent().getLongExtra(MainActivity.EXTRA_TASK_ID, -1);
        try {
            taskItem = datasource.getTask(taskId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        detailTitle.setText(taskItem.getTask());
        detailDatum.setText(taskItem.getTaskDatum());
        detailTime.setText(taskItem.getTaskTime());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailsActivity.this, UpdateActivity.class);
                intent.putExtra(MainActivity.EXTRA_TASK_ID, taskId);
                startActivityForResult(intent, 2);

            }

        });



    }
}
