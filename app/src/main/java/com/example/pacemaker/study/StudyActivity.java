package com.example.pacemaker.study;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.example.pacemaker.R;
import com.example.pacemaker.auth.AuthActivity;
import com.example.pacemaker.study.commonservice.CommonRequest;
import com.example.pacemaker.study.commonservice.CommonService;
import com.example.pacemaker.study.ui.mystudy.MyStudyFragment;
import com.example.pacemaker.study.ui.mystudy.enums.GraphType;
import com.example.pacemaker.study.ui.mystudy.request.MyStudyRequest;
import com.example.pacemaker.study.ui.mystudy.service.MyStudyService;
import com.example.pacemaker.util.service.ServiceGenerator;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class StudyActivity extends AppCompatActivity {
    private MyStudyRequest myStudyRequest;
    private CommonRequest commonRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.study_activity_main);
        setUpNavigationBar();

        MyStudyService myStudyService = ServiceGenerator.createService(MyStudyService.class,
                getSharedPreferences("auth", MODE_PRIVATE).getString("accessToken", null));
        CommonService commonService = ServiceGenerator.createService(CommonService.class,
                getSharedPreferences("auth", MODE_PRIVATE).getString("accessToken", null));
        // API : userid바꿔주기
        myStudyRequest = new MyStudyRequest(myStudyService, 2);
        commonRequest = new CommonRequest(commonService, getSharedPreferences("auth", MODE_PRIVATE));
        

    }

    private void setUpNavigationBar() {
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setItemIconTintList(null);
        navView.setSelectedItemId(R.id.nav_my_study);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_my_study, R.id.nav_study_search, R.id.nav_study_league, R.id.nav_my_page)
                .build();
        NavHostFragment navHostFragment =
                (NavHostFragment)getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        navController.addOnDestinationChangedListener(new BottomNavigationItemSelectedListener(navView.getMenu()));
        NavigationUI.setupWithNavController(navView, navController);

    }

    public void requestDrawGraph(GraphType type) {
        NavHostFragment navHostFragment = (NavHostFragment)getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        MyStudyFragment fragment = (MyStudyFragment)navHostFragment.getChildFragmentManager().getFragments().get(0);
        Log.d("mystudy", "requestdrawgraph");
        myStudyRequest.drawChart(fragment, type);
    }

    public void refreshToken() {
        String oldToken = getSharedPreferences("auth", MODE_PRIVATE).getString("refreshToken", null);
        if (oldToken != null)
            commonRequest.refreshToken(this);
    }

    public void logout() {
        SharedPreferences.Editor editor = getSharedPreferences("auth", MODE_PRIVATE).edit();
        editor.remove(AuthActivity.ACCESS_TOKEN);
        editor.remove(AuthActivity.REFRESH_TOKEN);
        editor.remove(AuthActivity.USER_ID);
        editor.remove(AuthActivity.USER_NAME);
        editor.apply();
        Intent intent = new Intent(this, com.example.pacemaker.auth.AuthActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}