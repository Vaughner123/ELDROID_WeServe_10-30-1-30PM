package com.ucb.weserve;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Events extends AppCompatActivity {

    private Button addPostButton;
    private EditText eventName;
    private EditText eventAddress;
    private EditText eventDescription;
    private ImageView eventImage; // If you want to display the image selected
    private Uri imageUri; // Variable to store the image URI (to be set when selecting an image)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events_layout); // Ensure this matches your layout file name

        // Initialize views
        addPostButton = findViewById(R.id.addPostButton);
        eventName = findViewById(R.id.eventName);
        eventAddress = findViewById(R.id.eventAddress);
        eventDescription = findViewById(R.id.eventDescription);
        eventImage = findViewById(R.id.eventImage); // If you want to display the selected image

        // Initialize bottom navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set up the navigation item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                // Using if-else statements
                if (menuItem.getItemId() == R.id.navigation_home) {
                    startActivity(new Intent(Events.this, Dashboard.class));
                    finish();
                    return true;
                } else if (menuItem.getItemId() == R.id.navigation_history) {
                    startActivity(new Intent(Events.this, History.class));
                    finish();
                    return true;
                } else if (menuItem.getItemId() == R.id.navigation_post) {
                    startActivity(new Intent(Events.this, Post.class));
                    finish();
                    return true;
                } else if (menuItem.getItemId() == R.id.navigation_events) {
                    // Stay on Events, no action needed
                    return true; // Return true as it's the current activity
                } else if (menuItem.getItemId() == R.id.navigation_profile) {
                    startActivity(new Intent(Events.this, Profile.class));
                    finish();
                    return true;
                } else {
                    return false; // Return false if no valid menu item was selected
                }
            }
        });

        // Set OnClickListener for addPostButton
        addPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = eventName.getText().toString().trim();
                String address = eventAddress.getText().toString().trim();
                String description = eventDescription.getText().toString().trim();

                if (name.isEmpty() || address.isEmpty() || description.isEmpty() || imageUri == null) {
                    Toast.makeText(Events.this, "Please fill in all fields and select an image", Toast.LENGTH_SHORT).show();
                } else {
                    // Create intent to start DashboardActivity
                    Intent intent = new Intent(Events.this, Dashboard.class);
                    intent.putExtra("EVENT_NAME", name);
                    intent.putExtra("EVENT_ADDRESS", address);
                    intent.putExtra("EVENT_DESCRIPTION", description);
                    // Optional: Pass the image URI if needed
                    // intent.putExtra("EVENT_IMAGE", imageUri.toString());
                    startActivity(intent);
                }
            }
        });
    }
}
