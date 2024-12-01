package com.ucb.weserveproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    private static final String TAG = "ProfileFragment";

    private ImageView profileImage;
    private TextView username;
    private Button btnEditProfile, btnPostedActivities, btnSettings, btnLogout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_layout, container, false);

        profileImage = view.findViewById(R.id.profile_image);
        username = view.findViewById(R.id.username);
        btnEditProfile = view.findViewById(R.id.btn_edit_profile);
        btnPostedActivities = view.findViewById(R.id.btn_posted_activities);
        btnSettings = view.findViewById(R.id.btn_settings);
        btnLogout = view.findViewById(R.id.btn_logout);

        btnEditProfile.setOnClickListener(v -> navigateToActivity(EditProfileActivity.class));
        btnPostedActivities.setOnClickListener(v -> navigateToDashboard());
        btnSettings.setOnClickListener(v -> navigateToActivity(SettingsActivity.class));
        btnLogout.setOnClickListener(v -> logoutUser());

        return view;
    }


    private void navigateToDashboard() {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).navigateTo(R.id.navigation_dashboard);
            Log.d(TAG, "Navigating to Dashboard.");
        } else {
            Log.e(TAG, "Failed to navigate: Activity is not MainActivity.");
        }
    }

    private void navigateToActivity(Class<?> activityClass) {
        if (getContext() != null) {
            Intent intent = new Intent(getContext(), activityClass);
            startActivity(intent);
            Log.d(TAG, "Navigated to activity: " + activityClass.getSimpleName());
        }
    }

    private void logoutUser() {
        if (getActivity() != null) {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            requireActivity().finish();
            Log.d(TAG, "User logged out.");
        }
    }
}
