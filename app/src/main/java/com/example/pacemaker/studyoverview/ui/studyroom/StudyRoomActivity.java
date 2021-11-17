package com.example.pacemaker.studyoverview.ui.studyroom;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.pacemaker.R;
import com.example.pacemaker.auth.AuthActivity;
import com.example.pacemaker.studyoverview.enums.OverviewFragTypes;
import com.example.pacemaker.studyoverview.service.StudyOverviewRequest;
import com.example.pacemaker.studyoverview.service.StudyOverviewService;
import com.example.pacemaker.studyoverview.ui.overview.OverviewFragment;
import com.example.pacemaker.util.service.ServiceGenerator;

public class StudyRoomActivity extends AppCompatActivity {
    StudyOverviewRequest studyOverviewRequest;
    OverviewFragment memberFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.studyroom_fragment);

        SharedPreferences.Editor editor = getSharedPreferences("auth", MODE_PRIVATE).edit();
        StudyOverviewService studyOverviewService = ServiceGenerator.createService(StudyOverviewService.class,
                getSharedPreferences("auth", MODE_PRIVATE).getString(AuthActivity.ACCESS_TOKEN, null),
                getSharedPreferences("auth", MODE_PRIVATE).getString(AuthActivity.REFRESH_TOKEN, null),
                editor);

        studyOverviewRequest = new StudyOverviewRequest(studyOverviewService);

        Button btnStart = findViewById(R.id.btn_start);
        btnStart.setOnClickListener(view -> {
            Intent intent = new Intent(this, StudyCameraActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }
}