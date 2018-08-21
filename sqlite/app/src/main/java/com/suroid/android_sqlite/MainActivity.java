package com.suroid.android_sqlite;

import android.content.Intent;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;

import com.suroid.android_sqlite.helper.DBHelper;
import com.suroid.android_sqlite.helper.DBHelperDelete;
import com.suroid.android_sqlite.helper.DBHelperInsert;
import com.suroid.android_sqlite.helper.DBHelperSelect;
import com.suroid.android_sqlite.model.Book;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnLongClick;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.fab) FloatingActionButton floatingActionButton ;
    @InjectView(R.id.listBooks) ListView listView;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setTitle("SQLITE");
        }
        getList();
    }

    public void getList(){
        DBHelperSelect dbHelperSelect = new DBHelperSelect(this);
        final List<String> bookList =dbHelperSelect.getBookList();
        if(!bookList.isEmpty()){
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, bookList);
            listView.setAdapter(adapter);
        }else{
            addBook();
        }

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                DBHelperDelete dbHelperDelete = new DBHelperDelete(MainActivity.this);
                dbHelperDelete.deleteRecordFromBookMaster(bookList.get(position));
                getList();
                return true;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getList();
    }

    @OnClick(R.id.fab)
    public void buttonClick() {
        startActivity(new Intent(MainActivity.this,AddBook.class));
    }

    public void addBook(){
        DBHelperInsert dbInsert = new DBHelperInsert(this);

        dbInsert.addBook(new Book("SQL Antipatterns (Paperback)", "Bill Karwin"));
        dbInsert.addBook(new Book("An Introduction to Database Systems (Paperback)", "C.J. Date"));
        dbInsert.addBook(new Book("Elasticsearch in Action (ebook)", "Matthew Lee Hinman"));
        dbInsert.addBook(new Book("Beginning Android Games", " Mario Zechner"));
        dbInsert.addBook(new Book("Android Recipes: A Problem-Solution Approach", "Dave Smith & Jeff Friesen"));
    }
}
