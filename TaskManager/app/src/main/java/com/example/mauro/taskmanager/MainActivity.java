package com.example.mauro.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;

    private SimpleCursorAdapter simpleCursorAdapter;
    private DataSource dataSource;
    public String[] columns;
    public int[] to;

    public static final String EXTRA_TASK_ID = "extraAssignmentId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.listView);

        dataSource = new DataSource(this);

        columns = new String[]{MySQLiteManager.COLUMN_TASK, MySQLiteManager.COLUMN_TASKDATUM,
                MySQLiteManager.COLUMN_TASKTIME};
        to = new int[]{R.id.title, R.id.datum, R.id.time};


        try {
            simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.row_item, dataSource.getAllTaskCursor(), columns, to, 0);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        listView.setAdapter(simpleCursorAdapter);
        updateTaskListView();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra(EXTRA_TASK_ID, simpleCursorAdapter.getItemId(position));
                startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        registerForContextMenu(listView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {

            long assignmentId = data.getLongExtra(EXTRA_TASK_ID, -1);
            if (assignmentId != -1) {
                updateTaskListView();
            }

        }
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                updateTaskListView();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void updateTaskListView() {

        try {
            simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.row_item, dataSource.getAllTaskCursor(), columns, to, 0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        listView.setAdapter(simpleCursorAdapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.setHeaderTitle("Select the action");
        menu.add(0, v.getId(), 0, "Delete");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        if (item.getTitle() == "Delete") {

            Toast.makeText(getApplicationContext(), "task deleted", Toast.LENGTH_LONG).show();

            try {
                dataSource.deleteTask(simpleCursorAdapter.getItemId(info.position));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                simpleCursorAdapter.changeCursor(dataSource.getAllTaskCursor());
            } catch (SQLException e) {
                e.printStackTrace();
            }

            updateTaskListView();

        } else {

            return false;
        }

        return true;

    }
}