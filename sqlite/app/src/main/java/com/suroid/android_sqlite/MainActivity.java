package com.suroid.android_sqlite;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.suroid.android_sqlite.helper.DBHelperDelete;
import com.suroid.android_sqlite.helper.DBHelperInsert;
import com.suroid.android_sqlite.helper.DBHelperSelect;
import com.suroid.android_sqlite.model.Book;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBHelperInsert dbInsert = new DBHelperInsert(this);
        DBHelperSelect dbSelect = new DBHelperSelect(this);
        DBHelperDelete dbDelete = new DBHelperDelete(this);
        // get all books
        List<Book> list = dbSelect.getBookList();
        // delete one book
        dbDelete.deleteRecordFromBookMaster(list.get(0));

        dbInsert.addBook(new Book("SQL Antipatterns (Paperback)", "Bill Karwin"));
        dbInsert.addBook(new Book("An Introduction to Database Systems (Paperback)", "C.J. Date"));
        dbInsert.addBook(new Book("Elasticsearch in Action (ebook)", "Matthew Lee Hinman"));
        dbInsert.addBook(new Book("Beginning Android Games", " Mario Zechner"));
        dbInsert.addBook(new Book("Android Recipes: A Problem-Solution Approach", "Dave Smith & Jeff Friesen"));
    }

}
