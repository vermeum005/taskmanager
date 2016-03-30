package com.example.mauro.taskmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;

import com.example.mauro.taskmanager.MySQLiteManager;
import com.example.mauro.taskmanager.Task;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mauro on 19/03/2016.
 */
public class DataSource {
    private SQLiteDatabase database;
    private MySQLiteManager dbHelper;
    private String[] assignmentAllColumns = {
            MySQLiteManager.COLUMN_TASK_ID,
            MySQLiteManager.COLUMN_TASK,
            MySQLiteManager.COLUMN_TASKDATUM,
            MySQLiteManager.COLUMN_TASKTIME };

    public DataSource(Context context) {
        dbHelper = new MySQLiteManager(context);
        database = dbHelper.getWritableDatabase();
        dbHelper.close();
    }

    // Opens the database to use it
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    // Closes the database when you no longer need it
    public void close() {

        dbHelper.close();
    }
    public long createTask(String task, String datum, String time) throws SQLException {
        // If the database is not open yet, open it
        if (!database.isOpen())
            open();

        ContentValues values = new ContentValues();
        values.put(MySQLiteManager.COLUMN_TASK, task);
        values.put(MySQLiteManager.COLUMN_TASKDATUM, datum);
        values.put(MySQLiteManager.COLUMN_TASKTIME, time);
        long insertId = database.insert(MySQLiteManager.TABLE_TASKS, null, values);

        // If the database is open, close it
        if (database.isOpen())
            close();

        return insertId;
    }
    public void deleteTask(long id) throws SQLException {
        if (!database.isOpen())
            open();

        database.delete(MySQLiteManager.TABLE_TASKS, MySQLiteManager.COLUMN_TASK_ID + " =?", new String[] {
                Long.toString(id)
        });

        if (database.isOpen())
            close();

    }
    public void updateTask(Task task) throws SQLException {
        if (!database.isOpen())
            open();

        ContentValues args = new ContentValues();
        args.put(MySQLiteManager.COLUMN_TASK, task.getTask());
        database.update(MySQLiteManager.TABLE_TASKS, args, MySQLiteManager.COLUMN_TASK_ID + "=?", new String[]{Long.toString(task.getId())});
        if (database.isOpen())
            close();
    }
    public List<Task> getAllTasks() throws SQLException {
        if (!database.isOpen())
            open();

        List<Task> assignments = new ArrayList<Task>();

        Cursor cursor = database.query(MySQLiteManager.TABLE_TASKS, assignmentAllColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Task task = cursorToTask(cursor);
            assignments.add(task);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();

        if (database.isOpen())
            close();

        return assignments;
    }
    private Task cursorToTask(Cursor cursor) {
        try {
            Task task = new Task();
            task.setId(cursor.getLong(cursor.getColumnIndexOrThrow(MySQLiteManager.COLUMN_TASK_ID)));
            task.setTask(cursor.getString(cursor.getColumnIndexOrThrow(MySQLiteManager.COLUMN_TASK)));
            task.setTaskDatum(cursor.getString(cursor.getColumnIndexOrThrow(MySQLiteManager.COLUMN_TASKDATUM)));
            task.setTaskTime(cursor.getString(cursor.getColumnIndexOrThrow(MySQLiteManager.COLUMN_TASKTIME)));
            return task;
        } catch(CursorIndexOutOfBoundsException exception) {
            exception.printStackTrace();
            return null;
        }
    }
    public Cursor getAllTaskCursor() throws SQLException {
        if (!database.isOpen())
            open();

        Cursor cursor = database.rawQuery(
                "SELECT " +
                        MySQLiteManager.COLUMN_TASK_ID + " AS _id, " +
                        MySQLiteManager.COLUMN_TASK + ", " +
                        MySQLiteManager.COLUMN_TASKDATUM + ", " +
                        MySQLiteManager.COLUMN_TASKTIME +
                        " FROM " + MySQLiteManager.TABLE_TASKS, null);

        cursor.moveToFirst();

//        while (!cursor.isAfterLast()) {
//
//            News news_item = cursorToNews(cursor);
//            assignments.add(news_item);
//            cursor.moveToNext();
//        }
//        cursor.close();

        if (database.isOpen())
            close();


        return cursor;
    }
    public Task getTask(long columnId) throws SQLException {
        if (!database.isOpen())
            open();

        Cursor cursor = database.query(MySQLiteManager.TABLE_TASKS, assignmentAllColumns, MySQLiteManager.COLUMN_TASK_ID + "=?", new String[] { Long.toString(columnId)}, null, null, null);

        cursor.moveToFirst();
        Task task = cursorToTask(cursor);
        cursor.close();

        if (database.isOpen())
            close();

        return task;
    }

}
