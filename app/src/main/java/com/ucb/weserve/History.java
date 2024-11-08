package com.ucb.weserve;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class History extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_layout); 

        
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        ListView listView = findViewById(R.id.history_listview);

        
        bottomNavigationView.setSelectedItemId(R.id.navigation_history);

        
        ArrayList<String> names = new ArrayList<>();
        names.add("John Doe - Description 1");
        names.add("Jane Smith - Description 2");
        names.add("Sam Wilson - Description 3");

       
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, names);
        listView.setAdapter(adapter);

       
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               
                String selectedItem = names.get(position);
                Intent intent = new Intent(History.this, DetailActivity.class); // Replace with your detail activity
                intent.putExtra("selectedItem", selectedItem); // Pass data to the next activity
                startActivity(intent);
            }
        });

        
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.navigation_home) {
                    startActivity(new Intent(History.this, Dashboard.class));
                    finish(); 
                    return true;
                } else if (menuItem.getItemId() == R.id.navigation_history) {
                    return true; 
                } else if (menuItem.getItemId() == R.id.navigation_post) {
                    startActivity(new Intent(History.this, Post.class));
                    finish(); 
                    return true;
                } else if (menuItem.getItemId() == R.id.navigation_events) {
                    startActivity(new Intent(History.this, Events.class));
                    finish(); 
                    return true;
                } else if (menuItem.getItemId() == R.id.navigation_profile) {
                    startActivity(new Intent(History.this, Profile.class));
                    finish(); 
                    return true;
                } else {
                    return false; 
                }
            }
        });
    }
}
