package com.example.pacemaker.auth;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.pacemaker.R;

public class AuthActivity extends AppCompatActivity {
    private String TAG = "Auth";


    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        MainViewModel viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        final Observer<Integer> numObserver = new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable final Integer integer) {
                mTextView.setText(integer);
            }
        };

        viewModel.getMutableData().observe(this, numObserver);

        mTextView = (TextView) findViewById(R.id.text);


    }
}