package com.example.mauro.taskmanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by mauro on 21/03/2016.
 */
public class MySQLiteManager extends SQLiteOpenHelper {
    // Database info
    private static final String DATABASE_NAME = "mytaskmanager.db";
    private static final int DATABASE_VERSION = 1;

    // Assignments
    public static final String TABLE_TASKS = "tasks";
    public static final String COLUMN_TASK_ID = "task_id";
    public static final String COLUMN_TASK = "task";
    public static final String COLUMN_TASKDATUM = "taskdatum";
    public static final String COLUMN_TASKTIME = "tasktime";

    // Creating the table
    private static final String DATABASE_CREATE_TASKS =
            "CREATE TABLE " + TABLE_TASKS +
                    "( " +
                    COLUMN_TASK_ID + " integer primary key autoincrement, " +
                    COLUMN_TASK + " text not null, " +
                    COLUMN_TASKDATUM + " text not null, " +
                    COLUMN_TASKTIME + " text not null " +
                    ");";

    // Mandatory constructor which passes the context, database name and database version and passes it to the parent
    public MySQLiteManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        // Execute the sql to create the table tasks
        database.execSQL(DATABASE_CREATE_TASKS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    	/*
     	* When the database gets upgraded you should handle the update to make sure there is no data loss.
     	* This is the default code you put in the upgrade method, to delete the table and call the oncreate again.
     	* */
        Log.w(MySQLiteManager.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        onCreate(db);
    }
}
