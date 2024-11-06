package com.ucb.weserve;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail); // Ensure this layout exists

        // Get the data passed from the History activity
        String selectedItem = getIntent().getStringExtra("selectedItem");

        // Display the selected item in a TextView
        TextView textView = findViewById(R.id.text_view_detail); // Ensure this ID matches your layout
        textView.setText(selectedItem);
    }
}
