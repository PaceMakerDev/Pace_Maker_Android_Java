package com.example.pacemaker.study;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.pacemaker.R;
import com.example.pacemaker.study.ui.mystudy.chart.ChartService;
import com.github.mikephil.charting.charts.BarChart;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavHost;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class StudyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.study_activity_main);
        setUpNavigationBar();
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
}