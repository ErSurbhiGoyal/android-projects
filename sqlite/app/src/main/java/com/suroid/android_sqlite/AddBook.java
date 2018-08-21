package com.suroid.android_sqlite;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.suroid.android_sqlite.helper.DBHelperInsert;
import com.suroid.android_sqlite.model.Book;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class AddBook extends AppCompatActivity {

    @InjectView(R.id.addBookBtn) Button addBookBtn;
    @InjectView(R.id.bookName) EditText bookName;
    @InjectView(R.id.authorName) EditText authorName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        ButterKnife.inject(this);

    }

    @OnClick(R.id.addBookBtn)
    public void buttonClick(){
        Book book = new Book();
        book.setTitle(bookName.getText().toString());
        book.setAuthor(authorName.getText().toString());

        DBHelperInsert dbInsert = new DBHelperInsert(this);
        dbInsert.addBook(book);

        bookName.setText("");
        authorName.setText("");
        finish();
    }

}
