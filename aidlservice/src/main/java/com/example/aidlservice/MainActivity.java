package com.example.aidlservice;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Uri uri = Uri.parse("content://com.example.contentprovidersample.BookProvider");
        getContentResolver().query(uri, null, null, null, null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
