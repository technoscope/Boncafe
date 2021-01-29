package com.example.boncafe.ui.accountscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.boncafe.R;

public class PasswordForgotActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_forgot);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


    }
}