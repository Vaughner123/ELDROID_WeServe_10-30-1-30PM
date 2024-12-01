package com.ucb.weserveproject;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SettingsActivity extends AppCompatActivity {

    private TextView tvEnableNotifications, tvSoundNotifications, tvVibrationNotifications;
    private Switch switchEnableNotifications, switchSoundNotifications, switchVibrationNotifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Settings");
        }

        tvEnableNotifications = findViewById(R.id.tv_enable_notifications);
        tvSoundNotifications = findViewById(R.id.tv_sound_notifications);
        tvVibrationNotifications = findViewById(R.id.tv_vibration_notifications);

        switchEnableNotifications = findViewById(R.id.switch_enable_notifications);
        switchSoundNotifications = findViewById(R.id.switch_sound_notifications);
        switchVibrationNotifications = findViewById(R.id.switch_vibration_notifications);

        switchEnableNotifications.setOnCheckedChangeListener(this::onEnableNotificationsChanged);
        switchSoundNotifications.setOnCheckedChangeListener(this::onSoundNotificationsChanged);
        switchVibrationNotifications.setOnCheckedChangeListener(this::onVibrationNotificationsChanged);
    }

    private void onEnableNotificationsChanged(CompoundButton buttonView, boolean isChecked) {
        showToast("Enable Notifications: " + (isChecked ? "ON" : "OFF"));
    }

    private void onSoundNotificationsChanged(CompoundButton buttonView, boolean isChecked) {
        showToast("Sound Notifications: " + (isChecked ? "ON" : "OFF"));
    }

    private void onVibrationNotificationsChanged(CompoundButton buttonView, boolean isChecked) {
        showToast("Vibration Notifications: " + (isChecked ? "ON" : "OFF"));
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
