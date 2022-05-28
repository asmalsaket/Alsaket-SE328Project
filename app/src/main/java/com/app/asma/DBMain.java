package com.app.asma;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBMain  extends SQLiteOpenHelper {


    private static final String DBNAME = "DB";
    private static final String TABLE = "student";
    private static final int VER = 2;

    private static final String ID = "ID";
    private static final String Name = "Name";
    private static final String Surname = "Surname";
    private static final String Fathers_name = "Fathers_name";
    private static final String National_ID = "National_ID";
    private static final String date_of_birth = "date_of_birth";
    private static final String Gender = "Gender";

    public DBMain(@Nullable Context context) {
        super(context, DBNAME, null, VER);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE + " ("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Name + " TEXT,"
                + Surname + " TEXT,"
                + Fathers_name + " TEXT,"
                + National_ID + " TEXT,"
                + date_of_birth + " TEXT,"
                + Gender + " TEXT)";

        // at last we are calling a exec sql
        // method to execute above sql query
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "drop table if exists " + TABLE + "";
        db.execSQL(query);
        onCreate(db);
    }
}
