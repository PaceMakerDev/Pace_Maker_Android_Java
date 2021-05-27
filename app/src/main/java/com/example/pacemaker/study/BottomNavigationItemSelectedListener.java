package com.example.pacemaker.study;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;

import com.example.pacemaker.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNavigationItemSelectedListener implements NavController.OnDestinationChangedListener{
    Menu menu;
    public BottomNavigationItemSelectedListener(Menu menu) {
        super();
        this.menu = menu;
    }
    @Override
    public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
        int destId = destination.getId();
        if (destId == R.id.nav_my_study) {
            menu.findItem(R.id.nav_my_study).setIcon(R.drawable.my_study_filled);
            menu.findItem(R.id.nav_study_search).setIcon(R.drawable.study_search_line);
            menu.findItem(R.id.nav_study_league).setIcon(R.drawable.study_league_line);
            menu.findItem(R.id.nav_my_page).setIcon(R.drawable.my_page_line);
        } else if (destId == R.id.nav_study_search) {
            menu.findItem(R.id.nav_study_search).setIcon(R.drawable.study_search_filled);
            menu.findItem(R.id.nav_my_study).setIcon(R.drawable.my_study_line);
            menu.findItem(R.id.nav_study_league).setIcon(R.drawable.study_league_line);
            menu.findItem(R.id.nav_my_page).setIcon(R.drawable.my_page_line);
        } else if (destId == R.id.nav_study_league) {
            menu.findItem(R.id.nav_study_league).setIcon(R.drawable.study_league_filled);
            menu.findItem(R.id.nav_my_study).setIcon(R.drawable.my_study_line);
            menu.findItem(R.id.nav_study_search).setIcon(R.drawable.study_search_line);
            menu.findItem(R.id.nav_my_page).setIcon(R.drawable.my_page_line);
        } else if (destId == R.id.nav_my_page) {
            menu.findItem(R.id.nav_my_page).setIcon(R.drawable.my_page_filled);
            menu.findItem(R.id.nav_my_study).setIcon(R.drawable.my_study_line);
            menu.findItem(R.id.nav_study_search).setIcon(R.drawable.study_search_line);
            menu.findItem(R.id.nav_study_league).setIcon(R.drawable.study_league_line);
        }
    }
}
