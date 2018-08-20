package com.suroid.android_sqlite;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.suroid.android_sqlite.helper.DBHelper;
import com.suroid.android_sqlite.helper.DBHelperInsert;
import com.suroid.android_sqlite.model.Book;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.fab) FloatingActionButton floatingActionButton ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);

       /* // get all books
        List<Book> list = db.getAllBooks();

        // delete one book
        db.deleteBook(list.get(0));

        // get all books
        db.getAllBooks();*/

    }

    public void addBook(){
        DBHelperInsert dbInsert = new DBHelperInsert(this);

        dbInsert.addBook(new Book("Android Application Development Cookbook", "Wei Meng Lee"));
        dbInsert.addBook(new Book("Android Programming: The Big Nerd Ranch Guide", "Bill Phillips and Brian Hardy"));
        dbInsert.addBook(new Book("Learn Android App Development", "Wallace Jackson"));
    }

}
