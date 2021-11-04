package com.example.pacemaker.studyoverview;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pacemaker.R;

public class StudyOverviewActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.studyoverview_fragment_overview);


    }

    private void showOverview() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
    }


}