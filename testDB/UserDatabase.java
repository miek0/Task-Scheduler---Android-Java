package com.example.taskscheduler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class UserDatabase extends SQLiteOpenHelper {

    private static final String TAG = "UserDatabase";

    private static final String TABLE_NAME = "users_db";
    private static final String COL1 = "id";
    private static final String COL2 = "username";
    private static final String COL3 = "password";
    private static final String COL4 = "firstname";
    private static final String COL5 = "lastname";
    private static final String COL6 = "email";
    private static final String COL7 = "phone";

    public UserDatabase(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL2 + " TEXT," + COL3 + " TEXT," + COL4 + " TEXT," + COL5 + " TEXT," + COL6 + " TEXT," + COL7 + " TEXT)";
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
        contentValues.put(COL7, item[5]);

        for(int i=0;i<item.length;i++)
            Log.d(TAG, "addData: Adding " + item[i] + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);

        //if date as inserted incorrectly it will return -1
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
    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    /**
     * Returns only the ID that matches the name passed in
     *
     * @param name
     * @return
     */
    public Cursor getItemID(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL1 + " FROM " + TABLE_NAME +
                " WHERE " + COL2 + " = '" + name + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    /**
     * Returns a selected field
     *
     * @param id
     * @param col
     */
    public Cursor getItemField(int id, String col) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + col + " FROM " + TABLE_NAME +
                " WHERE " + COL1 + " = " + id;
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    /**
     * Updates the name field
     *
     * @param newField
     * @param id
     * @param oldField
     * @param col
     */
    public void updateField(String newField, String oldField, int id, String col) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + col +
                " = '" + newField + "' WHERE " + COL1 + " = '" + id + "'" +
                " AND " + col + " = '" + oldField + "'";
        Log.d(TAG, "updateName: query: " + query);
        db.execSQL(query);
    }

    /*************************** THIS WILL BE DELETED SOON ************************/
    public void updateName(String newName, int id, String oldName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL2 +
                " = '" + newName + "' WHERE " + COL1 + " = '" + id + "'" +
                " AND " + COL2 + " = '" + oldName + "'";
        Log.d(TAG, "updateName: query: " + query);
        Log.d(TAG, "updateName: Setting name to " + newName);
        db.execSQL(query);
    }

    /**
     * Delete from database
     *
     * @param id
     * @param name
     */
    public void deleteName(int id, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + COL1 + " = '" + id + "'" +
                " AND " + COL2 + " = '" + name + "'";
        Log.d(TAG, "deleteName: query: " + query);
        Log.d(TAG, "deleteName: Deleting " + name + " from database.");
        db.execSQL(query);
    }
}
