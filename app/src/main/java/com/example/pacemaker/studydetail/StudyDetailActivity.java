package com.example.pacemaker.studydetail;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.pacemaker.R;
import com.example.pacemaker.auth.AuthActivity;
import com.example.pacemaker.study.BottomNavigationItemSelectedListener;
import com.example.pacemaker.study.commonservice.CommonRequest;
import com.example.pacemaker.study.commonservice.CommonService;
import com.example.pacemaker.study.enums.FragmentTypes;
import com.example.pacemaker.study.ui.mystudy.MyStudyFragment;
import com.example.pacemaker.study.ui.mystudy.enums.GraphType;
import com.example.pacemaker.study.ui.mystudy.request.MyStudyRequest;
import com.example.pacemaker.study.ui.mystudy.service.MyStudyService;
import com.example.pacemaker.study.ui.studysearch.StudySearchFragment;
import com.example.pacemaker.study.ui.studysearch.StudySearchMediator;
import com.example.pacemaker.study.ui.studysearch.models.StudyCreateRequestDto;
import com.example.pacemaker.study.ui.studysearch.service.StudySearchRequest;
import com.example.pacemaker.study.ui.studysearch.service.StudySearchService;
import com.example.pacemaker.study.ui.studysearch.studycreate.StudyCreateFragment;
import com.example.pacemaker.study.ui.studysearch.studycreate.StudyCreateMediator;
import com.example.pacemaker.study.ui.studysearch.studycreate.StudyCreateSuccessFragment;
import com.example.pacemaker.util.service.ServiceGenerator;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class StudyDetailActivity extends AppCompatActivity {
    private MyStudyRequest myStudyRequest;
    private StudySearchRequest studySearchRequest;
    private CommonRequest commonRequest;
    private StudyCreateMediator studyCreateMediator;
    private StudySearchMediator studySearchMediator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.study_activity_main);
        setUpNavigationBar();
        SharedPreferences.Editor editor = getSharedPreferences("auth", MODE_PRIVATE).edit();
        MyStudyService myStudyService = ServiceGenerator.createService(MyStudyService.class,
                getSharedPreferences("auth", MODE_PRIVATE).getString(AuthActivity.ACCESS_TOKEN, null),
                getSharedPreferences("auth", MODE_PRIVATE).getString(AuthActivity.REFRESH_TOKEN, null),
                editor);
        CommonService commonService = ServiceGenerator.createService(CommonService.class,
                getSharedPreferences("auth", MODE_PRIVATE).getString(AuthActivity.ACCESS_TOKEN, null),
                getSharedPreferences("auth", MODE_PRIVATE).getString(AuthActivity.REFRESH_TOKEN, null),
                editor);
        StudySearchService studySearchService = ServiceGenerator.createService(StudySearchService.class,
                getSharedPreferences("auth", MODE_PRIVATE).getString(AuthActivity.ACCESS_TOKEN, null),
                getSharedPreferences("auth", MODE_PRIVATE).getString(AuthActivity.REFRESH_TOKEN, null),
                editor);
        // API : userid바꿔주기
        int userId = getSharedPreferences(AuthActivity.SHARED_AUTH_ID, MODE_PRIVATE).getInt(AuthActivity.USER_ID, -1);
        Log.d("MyStudy", userId + " id");
        myStudyRequest = new MyStudyRequest(myStudyService, userId);
        commonRequest = new CommonRequest(commonService, getSharedPreferences("auth", MODE_PRIVATE));
        studySearchRequest = new StudySearchRequest(studySearchService);

        studyCreateMediator = new StudyCreateMediator();
        studySearchMediator = new StudySearchMediator();
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

    public void hideNavView() {
        View view = findViewById(R.id.nav_view);
        view.setVisibility(View.GONE);
    }

    public void showNavView() {
        View view = findViewById(R.id.nav_view);
        view.setVisibility(View.VISIBLE);
    }

    public void requestDrawGraph(GraphType type) {
        MyStudyFragment fragment = (MyStudyFragment)getCurrentFragment();
        myStudyRequest.drawChart(fragment, type);
    }

    public void requestTodayRecord() {
        MyStudyFragment fragment = (MyStudyFragment)getCurrentFragment();
        myStudyRequest.showTodayRecord(fragment);
    }

    public void requestStudyList() {
        MyStudyFragment fragment = (MyStudyFragment)getCurrentFragment();
        myStudyRequest.showUserStudy(fragment);
    }

    public void requestStudyCreate(StudyCreateRequestDto studyCreateRequestDto, Context context) {
        studySearchRequest.createStudy(studyCreateRequestDto, context, this);
    }

    public void requestGetRecentStudy(Context context) {
        studySearchRequest.requestRecentStudy(context, (StudySearchFragment)getCurrentFragment());
    }

    public void requestGetRecommendStudy(Context context) {
        studySearchRequest.requestRecommendStudy(context, (StudySearchFragment)getCurrentFragment());
    }

    public void requestStudySearch(Context context, String input) {
        studySearchRequest.requestStudySearch(context, (StudySearchFragment)getCurrentFragment(), input);
    }

    public void logout() {
        SharedPreferences.Editor editor = getSharedPreferences("auth", MODE_PRIVATE).edit();
        editor.remove(AuthActivity.ACCESS_TOKEN);
        editor.remove(AuthActivity.REFRESH_TOKEN);
        editor.remove(AuthActivity.USER_ID);
        editor.remove(AuthActivity.USER_NAME);
        editor.apply();
        Intent intent = new Intent(this, AuthActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public StudyCreateMediator getStudyCreateMediator() {
        return studyCreateMediator;
    }

    public StudySearchMediator getStudySearchMediator() {
        StudySearchFragment searchFragment = (StudySearchFragment)getCurrentFragment();
        studySearchMediator.setStudySearchFragment(searchFragment);
        return studySearchMediator;
    }

    public void setFragment(FragmentTypes type) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = null;
        switch (type) {
            case STUDY_CREATE:
                fragment = new StudyCreateFragment();
                StudyCreateMediator studyCreateMediator = getStudyCreateMediator();
                studyCreateMediator.setStudyCreateFragment((StudyCreateFragment)fragment);
                transaction.addToBackStack(null);
                break;
            case STUDY_CREATE_SUCCESS:
                fragment = new StudyCreateSuccessFragment();
        }

        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.commit();
    }

    private Fragment getCurrentFragment() {
        NavHostFragment navHostFragment = (NavHostFragment)getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        Fragment fragment = navHostFragment.getChildFragmentManager().getFragments().get(0);

        return fragment;
    }


}