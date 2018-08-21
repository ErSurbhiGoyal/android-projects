package com.suroid.android_sqlite.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.suroid.android_sqlite.model.Book;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DBHelperSelect extends DBHelper {

    public DBHelperSelect(Context context) {
        super(context);
    }

    public List<Book> getBookList() {
        List<Book> bookList = new ArrayList<Book>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * From BookDetail";
        Book book = null;
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    book = new Book();
                    book.setTitle(cursor.getString(0));
                    book.setAuthor(cursor.getString(1));
                    book.setCreatedDate(getCurrentDateNTime());
                    bookList.add(book);
                } while (cursor.moveToNext());
                cursor.close();
                db.close();
            }
        } catch (Exception e) {
            cursor.close();
            db.close();
            Log.e("error  ", e.getMessage());
        }
        return bookList;
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
