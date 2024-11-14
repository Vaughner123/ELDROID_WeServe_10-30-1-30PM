package com.ucb.weserveproject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Dashboard extends AppCompatActivity {

    @SuppressLint("UseCompatLoadingForColorStateLists")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set color change for the icons
        bottomNavigationView.setItemIconTintList(getResources().getColorStateList(R.color.colorSelected));
        bottomNavigationView.setItemTextColor(getResources().getColorStateList(R.color.colorUnselected));
        bottomNavigationView.setBackgroundColor(getResources().getColor(R.color.primary));
        ;

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            // Determine which fragment to load based on the item selected
            if (item.getItemId() == R.id.nav_dashboard) {
                selectedFragment = new DashboardFragment();
            } else if (item.getItemId() == R.id.nav_events) {
                selectedFragment = new EventsFragment();
            } else if (item.getItemId() == R.id.nav_post) {
                selectedFragment = new PostFragment();
            } else if (item.getItemId() == R.id.nav_history) {
                selectedFragment = new HistoryFragment();
            } else if (item.getItemId() == R.id.nav_profile) {
                selectedFragment = new ProfileFragment();
            }

            // Replace the fragment with animations
            if (selectedFragment != null) {
                // Start a new fragment transaction
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                // Apply custom enter and exit animations
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);

                // Replace the current fragment with the selected one
                transaction.replace(R.id.fragment_container, selectedFragment);
                transaction.commit();
            }

            return true;
        });

        // Set default selected item to Dashboard when the activity is first opened
        if (savedInstanceState == null) {
            bottomNavigationView.setSelectedItemId(R.id.nav_dashboard);
        }
    }
}
