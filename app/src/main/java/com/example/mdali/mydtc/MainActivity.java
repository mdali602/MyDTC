package com.example.mdali.mydtc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    DataBaseHelper myDbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.myDbHelper = new DataBaseHelper(this);
        try {
            this.myDbHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
