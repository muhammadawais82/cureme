package com.example.unknown.cureme;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.support.annotation.Nullable;

import java.util.HashMap;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "cure_mess.db";
    public static final String TABLE_NAME = "Patient_Info";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "BLOOD";
    public static final String COL_4 = "SUGAR";
    public static final String COL_5 = "TEMPERATURE";
    public static final String COL_6 = "BP";
    public static final String COL_7 = "HEARTBEAT";

    //for 2nd table for registration
    public static final String TABLE_REGISTER = "Login_info";
    public static final String COL_8 = "USERNAME";
    public static final String COL_9 = "PASSWORD";
    public static final String COL_10 = "USER_TYPE";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, patient_ID TEXT,NAME TEXT, BLOOD TEXT, SUGAR TEXT, TEMPERATURE INTEGER, BP INTEGER, HEARTBEAT INTEGER)");
        db.execSQL("create table " + TABLE_REGISTER + "(user_ID INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME TEXT, PASSWORD TEXT, USER_TYPE TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REGISTER);
        onCreate(db);
    }


    public void signUpData(String name, String pass, String type) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO " + TABLE_REGISTER + " VALUES(NULL, ? , ?, ? )";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, name);
        statement.bindString(2, pass);
        statement.bindString(3, type);
        statement.executeInsert();
    }


    public boolean insertData(String patient_ID, String name, String blood, String sugar, String temperature, String bp, String heartbeat) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO " + TABLE_NAME + " VALUES(NULL, ? , ?, ?, ? , ?, ? ,? )";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, patient_ID);
        statement.bindString(2, name);
        statement.bindString(3, blood);
        statement.bindString(4, sugar);
        statement.bindString(5, temperature);
        statement.bindString(6, bp);
        statement.bindString(7, heartbeat);
        statement.executeInsert();
        return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }

    public boolean updateData(String id, String name, String blood, String sugar, String temperature, String bp, String heartbeat) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, id);
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, blood);
        contentValues.put(COL_4, sugar);
        contentValues.put(COL_5, temperature);
        contentValues.put(COL_6, bp);
        contentValues.put(COL_7, heartbeat);
        db.update(TABLE_NAME, contentValues, "ID = ?", new String[]{id});
        return true;
    }

    public Integer deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ? ", new String[]{id});
    }

    //REGISTER TABLE
    public void insertContact(Contact c) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String query = "select * from " + TABLE_REGISTER;
        Cursor cursor = db.rawQuery(query, null);
        values.put(COL_8, c.getUsername());
        values.put(COL_9, c.getPassword());

        db.insert(TABLE_REGISTER, null, values);
        db.close();
    }

    public String searchPass(String username, String pass) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from " + TABLE_REGISTER + " where PASSWORD='" + pass + "' and USERNAME='" + username + "'";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            return cursor.getString(0) + "_" + cursor.getString(1) + "_" + cursor.getString(3);
        } else {
            return "Error";
        }
    }

    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("name", cursor.getString(1));
            user.put("email", cursor.getString(2));
            user.put("uid", cursor.getString(3));
            user.put("created_at", cursor.getString(4));
        }
        cursor.close();
        db.close();
        return user;
    }

    public String searchUser(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from " + TABLE_REGISTER + " where USERNAME='" + username + "'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() > 0) {
            return "Username Already Exists";
        } else {
            return "Welcome";
        }
    }
}