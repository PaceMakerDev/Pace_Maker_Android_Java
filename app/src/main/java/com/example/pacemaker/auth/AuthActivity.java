package com.example.pacemaker.auth;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pacemaker.R;

public class AuthActivity extends AppCompatActivity {
    private String TAG = "Auth";


    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        mTextView = (TextView) findViewById(R.id.text);


    }
}