/*
package com.suroid.android_sqlite.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.suroid.android_sqlite.R;
import com.suroid.android_sqlite.model.Book;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ListAdapter extends ArrayAdapter<Book> {
    private static final String TAG = "ListAdapter";
    private List<Book> bookList = new ArrayList<Book>();

    static class BookViewHolder {
        @InjectView(R.id.bookName)
        TextView bookName;
        @InjectView(R.id.authorName)
        TextView authorName;

        public BookViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    public ListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    @Override
    public void add(Book object) {
        bookList.add(object);
        super.add(object);
    }

    @Override
    public int getCount() {
        return this.bookList.size();
    }

    @Override
    public Book getItem(int index) {
        return this.bookList.get(index);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BookViewHolder holder;
        if (convertView != null) {
            holder = (BookViewHolder) convertView.getTag();
        } else {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.whatever, parent, false);
            holder = new BookViewHolder(convertView);
            convertView.setTag(holder);
        }

        holder.bookName.setText("John Doe");

        return convertView;
    }

}
*/
