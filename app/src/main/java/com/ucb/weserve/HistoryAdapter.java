package com.ucb.weserve;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class HistoryAdapter extends ArrayAdapter<HistoryItem> {

    public HistoryAdapter(Context context, ArrayList<HistoryItem> historyItems) {
        super(context, 0, historyItems);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // Get the data item for this position
        HistoryItem historyItem = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_history, parent, false);
        }

        // Lookup view for data population
        TextView itemName = convertView.findViewById(R.id.item_name);
        TextView itemDescription = convertView.findViewById(R.id.item_description);

        // Populate the data into the template view using the data object
        itemName.setText(historyItem.getName());
        itemDescription.setText(historyItem.getDescription());

        // Return the completed view to render on screen
        return convertView;
    }
}
