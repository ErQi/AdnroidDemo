package com.example.contentprovidersample;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onQuery(View view) {
        Uri bookUri = Uri.parse("content://com.example.contentprovidersample.BookProvider/book");
        ContentValues values = new ContentValues();
        values.put("_id", 6);
        values.put("name", "程序设计艺术");
        getContentResolver().insert(bookUri, values);
        Cursor bookCursor = getContentResolver().query(bookUri, new String[]{"_id", "name"}, null, null, null);
        while (bookCursor != null && bookCursor.moveToNext()) {
            Book book = new Book();
            book.setBookId(bookCursor.getInt(0));
            book.setBookName(bookCursor.getString(1));
            Log.e(TAG, book.toString());
        }
        if (bookCursor != null) {
            bookCursor.close();
        }

        Uri userUri = Uri.parse("content://com.example.contentprovidersample.BookProvider/user");
        Cursor userCursor = getContentResolver().query(userUri, new String[]{"_id", "name", "sex"}, null, null, null);
        while (userCursor != null && userCursor.moveToNext()) {
            User user = new User();
            user.setUserId(userCursor.getInt(0));
            user.setName(userCursor.getString(1));
            user.setMale(userCursor.getInt(2) == 1);
            Log.e(TAG, user.toString());
        }
        if (userCursor != null) {
            userCursor.close();
        }
    }
}
