package com.example.pacemaker.studyoverview;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

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

public class StudyOverviewActivity extends AppCompatActivity {
    StudyOverviewRequest studyOverviewRequest;
    OverviewFragment memberFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_activity);

        SharedPreferences.Editor editor = getSharedPreferences("auth", MODE_PRIVATE).edit();
        StudyOverviewService studyOverviewService = ServiceGenerator.createService(StudyOverviewService.class,
                getSharedPreferences("auth", MODE_PRIVATE).getString(AuthActivity.ACCESS_TOKEN, null),
                getSharedPreferences("auth", MODE_PRIVATE).getString(AuthActivity.REFRESH_TOKEN, null),
                editor);

        studyOverviewRequest = new StudyOverviewRequest(studyOverviewService);

        showOverview();
    }

    public void setFragment(OverviewFragTypes frag, Bundle bundle) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (frag == OverviewFragTypes.MEMBERS) {
            memberFragment = new OverviewFragment();
            memberFragment.setArguments(bundle);
            transaction.replace(R.id.fragment_container, memberFragment);
        }
        else {
            transaction.setCustomAnimations(
                    R.anim.from_right_to_center,
                    R.anim.from_center_to_left,
                    R.anim.from_left_to_center,
                    R.anim.from_center_to_right);

            transaction.addToBackStack(null);
            transaction.setReorderingAllowed(true);
            Fragment fragment = null;
            switch(frag) {
                //
            }
            if (bundle != null)
                fragment.setArguments(bundle);
            transaction.replace(R.id.fragment_container, fragment);
        }
        transaction.commit();
    }

    public void requestMembers(String studyId) {
        studyOverviewRequest.requestUserRanking(studyId, this, memberFragment);
    }

    private void showOverview() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        setFragment(OverviewFragTypes.MEMBERS, bundle);
    }
}