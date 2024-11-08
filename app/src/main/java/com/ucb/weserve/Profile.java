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

       
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                
                if (menuItem.getItemId() == R.id.navigation_home) {
                
                    startActivity(new Intent(Profile.this, Dashboard.class));
                    finish(); 
                    return true;
                } else if (menuItem.getItemId() == R.id.navigation_history) {
                   
                    startActivity(new Intent(Profile.this, History.class));
                    finish(); 
                    return true;
                } else if (menuItem.getItemId() == R.id.navigation_post) {
                   
                    startActivity(new Intent(Profile.this, Post.class));
                    finish(); 
                    return true;
                } else if (menuItem.getItemId() == R.id.navigation_events) {
                    
                    startActivity(new Intent(Profile.this, Events.class));
                    finish(); 
                    return true;
                } else if (menuItem.getItemId() == R.id.navigation_profile) {
                    
                    return true; 
                } else {
                    return false; 
                }
            }
        });
    }
}
