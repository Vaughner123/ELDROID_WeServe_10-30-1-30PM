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
    private ImageView eventImage; 
    private Uri imageUri; 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events_layout); 

        
        addPostButton = findViewById(R.id.addPostButton);
        eventName = findViewById(R.id.eventName);
        eventAddress = findViewById(R.id.eventAddress);
        eventDescription = findViewById(R.id.eventDescription);
        eventImage = findViewById(R.id.eventImage); 

      
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                
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
                   
                    return true; 
                } else if (menuItem.getItemId() == R.id.navigation_profile) {
                    startActivity(new Intent(Events.this, Profile.class));
                    finish();
                    return true;
                } else {
                    return false; 
                }
            }
        });

        
        addPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = eventName.getText().toString().trim();
                String address = eventAddress.getText().toString().trim();
                String description = eventDescription.getText().toString().trim();

                if (name.isEmpty() || address.isEmpty() || description.isEmpty() || imageUri == null) {
                    Toast.makeText(Events.this, "Please fill in all fields and select an image", Toast.LENGTH_SHORT).show();
                } else {
                    
                    Intent intent = new Intent(Events.this, Dashboard.class);
                    intent.putExtra("EVENT_NAME", name);
                    intent.putExtra("EVENT_ADDRESS", address);
                    intent.putExtra("EVENT_DESCRIPTION", description);
                    
                    
                    startActivity(intent);
                }
            }
        });
    }
}
