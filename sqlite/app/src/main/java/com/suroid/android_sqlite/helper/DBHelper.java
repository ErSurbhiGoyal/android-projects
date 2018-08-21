package com.suroid.android_sqlite.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 5;
    public static final String DATABASE_NAME = "suroidBook.db";

    //Book Master
    public static final String BookMaster = "BookDetail";
    public static final String Key_BookId = "Id";
    public static final String BookName = "BookName";
    public static final String BookAuthor = "BookAuthor";
    public static final String CreatedDate_Book = "BookCreateDate";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            String CREATE_BOOK_TABLE = " Create Table If Not Exists "
                    + BookMaster + " ( " + Key_BookId
                    + " Integer Primary Key AutoIncrement, " + BookName + " Text, " +BookAuthor + " Text, " + CreatedDate_Book
                    + " Text )";

            db.execSQL(CREATE_BOOK_TABLE);

        } catch (Exception e) {
            Log.e("error ", "" + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Upgrade Table here
        if (newVersion > oldVersion) {
            try {

            } catch (Exception e) {
                Log.d("Error", e.getMessage());
            }
        }
        onCreate(db);
    }

    public void dropTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.beginTransaction();
            db.execSQL("DROP TABLE IF EXISTS '" + BookMaster + "'");
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
        } catch (Exception e) {
            db.endTransaction();
            db.close();
            Log.e("Error ", e.getMessage());
        }
    }

    public void truncateTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.beginTransaction();
            db.execSQL("delete from " + BookMaster);
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
        } catch (Exception e) {
            db.endTransaction();
            db.close();
            Log.e("Error ", e.getMessage());
        }
    }
}



