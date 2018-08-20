package com.suroid.android_sqlite.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.suroid.android_sqlite.model.Book;

public class DBHelperDelete extends DBHelper {

    public DBHelperDelete(Context context) {
        super(context);
    }

    // function to insert record into BookDetail table
    public void deleteRecordFromBookMaster(Book book) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.beginTransaction();

            db.delete(BookMaster,
                    Key_BookId + " = ?",
                    new String[]{String.valueOf(book.getId())});

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
