package com.example.taskscheduler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TaskDatabase extends SQLiteOpenHelper {

    private static final String TAG = "TaskDatabase";

    private static final String TABLE_NAME = "tasks_db.db";
    private static final String COL1 = "id";
    private static final String COL2 = "user_id";
    private static final String COL3 = "taskname";
    private static final String COL4 = "taskdesc";
    private static final String COL5 = "duedate";
    private static final String COL6 = "progress";

    public TaskDatabase(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL2 + " TEXT," + COL3 + " TEXT," + COL4 + " TEXT," + COL5 + " TEXT," + COL6  + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP IF TABLE EXISTS '" + TABLE_NAME +"'");
        onCreate(db);
    }

    public boolean addData(String[] item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL2, item[0]);
        contentValues.put(COL3, item[1]);
        contentValues.put(COL4, item[2]);
        contentValues.put(COL5, item[3]);
        contentValues.put(COL6, item[4]);

        for(int i=0;i<item.length;i++)
            Log.d(TAG, "addData: Adding " + item[i] + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);

        //if data inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Returns all the data from database
     *
     * @return
     */
    public Cursor getDataOfUser(int userid) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME +
                        "WHERE " + COL2 + " = " + userid;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    /**
     * Returns only the ID that matches the name passed in
     *
     * @param name
     * @return
     */
    public Cursor getItemID(String name, int userid) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL1 + " FROM " + TABLE_NAME +
                " WHERE " + COL3 + " = '" + name + "'" + " AND " +
                COL2 + " = " + userid;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getItemField(int id, int userid, String col) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + col + " FROM " + TABLE_NAME +
                " WHERE " + COL1 + " = " + id + " AND " +
                COL2 + " = " + userid;
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    /**
     * Updates the a chosen field
     *
     * @param newField
     * @param id
     * @param oldField
     * @param userid
     * @param col
     */
    public void updateTask(String newField, String oldField, int id, int userid, String col) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + col +
                " = '" + newField + "' WHERE " + COL1 + " = '" + id + "'" +
                " AND " + COL2 + " = " + userid +
                " AND " + col + " = '" + oldField + "'";
        Log.d(TAG, "updateName: query: " + query);
        db.execSQL(query);
    }

    /**
     * Delete from database
     *
     * @param id
     * @param name
     */
    public void deleteTask(int id, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + COL1 + " = '" + id + "'" +
                " AND " + COL3 + " = '" + name + "'";
        Log.d(TAG, "deleteName: query: " + query);
        Log.d(TAG, "deleteName: Deleting " + name + " from database.");
        db.execSQL(query);
    }
}
