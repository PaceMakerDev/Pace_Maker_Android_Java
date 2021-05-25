package com.example.pacemaker.study;

import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.example.pacemaker.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNavigationItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener{
    Menu menu;
    public BottomNavigationItemSelectedListener(Menu menu) {
        super();
        this.menu = menu;
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.nav_my_study) {
            item.setIcon(R.drawable.my_study_filled);
            menu.findItem(R.id.nav_study_search).setIcon(R.drawable.study_search_line);
            menu.findItem(R.id.nav_study_league).setIcon(R.drawable.study_league_line);
            menu.findItem(R.id.nav_my_page).setIcon(R.drawable.my_page_line);
        } else if (itemId == R.id.nav_study_search) {
            item.setIcon(R.drawable.study_search_filled);
            menu.findItem(R.id.nav_my_study).setIcon(R.drawable.my_study_line);
            menu.findItem(R.id.nav_study_league).setIcon(R.drawable.study_league_line);
            menu.findItem(R.id.nav_my_page).setIcon(R.drawable.my_page_line);
        } else if (itemId == R.id.nav_study_league) {
            item.setIcon(R.drawable.study_league_filled);
            menu.findItem(R.id.nav_my_study).setIcon(R.drawable.my_study_line);
            menu.findItem(R.id.nav_study_search).setIcon(R.drawable.study_search_line);
            menu.findItem(R.id.nav_my_page).setIcon(R.drawable.my_page_line);
        } else if (itemId == R.id.nav_my_page) {
            item.setIcon(R.drawable.my_page_filled);
            menu.findItem(R.id.nav_my_study).setIcon(R.drawable.my_study_line);
            menu.findItem(R.id.nav_study_search).setIcon(R.drawable.study_search_line);
            menu.findItem(R.id.nav_study_league).setIcon(R.drawable.study_league_line);
        }
        return true;
    }
}
