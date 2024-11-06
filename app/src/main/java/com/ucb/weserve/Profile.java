package com.ucb.weserve;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout); // Ensure this matches your layout file name

        // Initialize bottom navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set up the navigation item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                // Using if-else statements
                if (menuItem.getItemId() == R.id.navigation_home) {
                    // Navigate to Dashboard
                    startActivity(new Intent(Profile.this, Dashboard.class));
                    finish(); // Optional: Finish the current activity
                    return true;
                } else if (menuItem.getItemId() == R.id.navigation_history) {
                    // Navigate to History
                    startActivity(new Intent(Profile.this, History.class));
                    finish(); // Optional: Finish the current activity
                    return true;
                } else if (menuItem.getItemId() == R.id.navigation_post) {
                    // Navigate to Post
                    startActivity(new Intent(Profile.this, Post.class));
                    finish(); // Optional: Finish the current activity
                    return true;
                } else if (menuItem.getItemId() == R.id.navigation_events) {
                    // Navigate to Events
                    startActivity(new Intent(Profile.this, Events.class));
                    finish(); // Optional: Finish the current activity
                    return true;
                } else if (menuItem.getItemId() == R.id.navigation_profile) {
                    // Stay on Profile, no action needed
                    return true; // Return true as it's the current activity
                } else {
                    return false; // Return false if no valid menu item was selected
                }
            }
        });
    }
}
