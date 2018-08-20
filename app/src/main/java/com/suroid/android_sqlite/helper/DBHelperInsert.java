package com.suroid.android_sqlite.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.suroid.android_sqlite.model.Book;

public class DBHelperInsert extends DBHelper {

    public DBHelperInsert(Context context) {
        super(context);
    }

    // function to insert record into BookDetail table
    public void addBook(Book book) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            db.beginTransaction();
            values.put(Key_BookId, book.getId());
            values.put(BookName, book.getTitle());
            values.put(BookAuthor, book.getAuthor());
            values.put(CreatedDate_Book, book.getCreatedDate());
            db.insert(BookMaster, null, values);

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
