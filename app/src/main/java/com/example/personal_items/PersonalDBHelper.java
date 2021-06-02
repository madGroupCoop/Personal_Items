package com.example.personal_items;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.personal_items.PersonalContract.*;

public class PersonalDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "personalItems.db";
    public static final int DATABASE_VERSION = 1;

    public PersonalDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_PERSONALITEMS_TABLE = "CREATE TABLE " +
                PersonalEntry.TABLE_NAME + " (" +
                PersonalEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PersonalEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                PersonalEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ");";
        db.execSQL(SQL_CREATE_PERSONALITEMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PersonalEntry.TABLE_NAME);
        onCreate(db);
    }


}
