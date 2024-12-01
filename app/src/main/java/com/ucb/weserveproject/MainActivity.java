package com.ucb.weserveproject;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        if (savedInstanceState == null) {
            setToolbarTitle("Dashboard");
            loadFragment(new DashboardFragment());  // Default fragment
        }

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            String title = "";

            int itemId = item.getItemId();
            if (itemId == R.id.navigation_dashboard) {
                selectedFragment = new DashboardFragment();
                title = "Dashboard";
            } else if (itemId == R.id.navigation_events) {
                selectedFragment = new EventFragment();
                title = "Events";
            } else if (itemId == R.id.navigation_post) {
                selectedFragment = new PostFragment();
                title = "Post";
            } else if (itemId == R.id.navigation_history) {
                selectedFragment = new HistoryFragment();
                title = "History";
            } else if (itemId == R.id.navigation_profile) {
                selectedFragment = new ProfileFragment();
                title = "Profile";
            }

            if (selectedFragment != null) {
                loadFragment(selectedFragment);
                setToolbarTitle(title);
            }

            return true;
        });
    }


    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }


    private void setToolbarTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }


    public void navigateTo(int navigationId) {
        bottomNavigationView.setSelectedItemId(navigationId);
    }
}

