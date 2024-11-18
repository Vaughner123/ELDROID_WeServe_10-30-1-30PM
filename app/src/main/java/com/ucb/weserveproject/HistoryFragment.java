package com.ucb.weserveproject;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ucb.weserveproject.Viewmodel.DashboardViewModel;
import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private RecyclerView recyclerView;
    private HistoryAdapter historyAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dashboardViewModel = new ViewModelProvider(requireActivity()).get(DashboardViewModel.class);

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_history, container, false);

        // Set up the RecyclerView
        recyclerView = rootView.findViewById(R.id.history_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Set up the Adapter
        List<HistoryItem> historyList = new ArrayList<>();
        historyAdapter = new HistoryAdapter(historyList);
        recyclerView.setAdapter(historyAdapter);

        // Populate data (You can fetch this data from ViewModel or elsewhere)
        populateHistoryList(historyList);

        if (getActivity() != null) {
            // Access the toolbar from the activity
            androidx.appcompat.widget.Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
            if (toolbar != null) {
                toolbar.setTitle("History"); // Set the title for the toolbar
            }
        }

        return rootView;
    }

    private void populateHistoryList(List<HistoryItem> historyList) {
        // Add sample data or fetch it dynamically
        historyList.add(new HistoryItem("Bon Julius Gerolaga", "Clean Up Drive"));
        historyList.add(new HistoryItem("Reggel Campanilla", "Volunteer"));
        historyList.add(new HistoryItem("Lanz Silva", "Tree Planting"));

        // Notify the adapter about data changes
        historyAdapter.notifyDataSetChanged();
    }
}
