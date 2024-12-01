package com.ucb.weserveproject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class ParticipantsActivity extends AppCompatActivity {

    private ListView participantsListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participants);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Participants");
        }

        participantsListView = findViewById(R.id.participants_list_view);

        String eventName = getIntent().getStringExtra("event_name");

        List<String> participants = loadParticipants(eventName);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, participants);
        participantsListView.setAdapter(adapter);
    }

    private List<String> loadParticipants(String eventName) {
        SharedPreferences joinedPostsPrefs = getSharedPreferences("JoinedPosts", MODE_PRIVATE);
        SharedPreferences userPrefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);

        List<String> participants = new ArrayList<>();

        for (String key : joinedPostsPrefs.getAll().keySet()) {
            String serializedPost = joinedPostsPrefs.getString(key, "");
            if (!serializedPost.isEmpty() && serializedPost.contains(eventName)) {
                String[] postParts = serializedPost.split("\\|");
                if (postParts.length == 4) {
                    String userKey = postParts[3].trim();

                    String firstName = userPrefs.getString("first_name_" + userKey, "Unknown");
                    String lastName = userPrefs.getString("last_name_" + userKey, "User");

                    String participantName = firstName + " " + lastName;
                    participants.add(participantName);
                }
            }
        }
        return participants;
    }
}
