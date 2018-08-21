package com.suroid.android_sqlite.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.suroid.android_sqlite.model.Book;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
            /*values.put(Key_BookId, book.getId());*/
            values.put(BookName, book.getTitle());
            values.put(BookAuthor, book.getAuthor());
            values.put(CreatedDate_Book, getCurrentDateNTime());
            long i = db.insert(BookMaster, null, values);

            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
        } catch (Exception e) {
            db.endTransaction();
            db.close();
            Log.e("Error ", e.getMessage());
        }
    }
    public String getCurrentDateNTime(){
        String date="";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            date = dtf.format(now);
        }

        return date;
    }


}
