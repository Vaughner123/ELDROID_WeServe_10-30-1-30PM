package com.ucb.weserve;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail); 

       
        String selectedItem = getIntent().getStringExtra("selectedItem");

        
        TextView textView = findViewById(R.id.text_view_detail); 
        textView.setText(selectedItem);
    }
}
