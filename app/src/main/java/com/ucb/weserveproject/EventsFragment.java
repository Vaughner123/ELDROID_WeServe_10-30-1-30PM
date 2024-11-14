package com.ucb.weserveproject;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ucb.weserveproject.Viewmodel.DashboardViewModel;

public class EventsFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dashboardViewModel = new ViewModelProvider(requireActivity()).get(DashboardViewModel.class);

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_events, container, false);

        TextView notificationText = rootView.findViewById(R.id.text_events);
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), notificationText::setText);
        if (getActivity() != null) {
            // Access the toolbar from the activity
            androidx.appcompat.widget.Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
            if (toolbar != null) {
                toolbar.setTitle("Events"); // Set the title for the toolbar
            }
        }
        return rootView;
    }
}
