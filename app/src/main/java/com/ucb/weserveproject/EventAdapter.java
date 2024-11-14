package com.ucb.weserveproject;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ucb.weserveproject.Viewmodel.Event;
import com.bumptech.glide.Glide;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private List<Event> eventList;

    public EventAdapter(List<Event> eventList) {
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the event_item layout
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_item, parent, false);
        return new EventViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event event = eventList.get(position);

        // Set text for event name, address, and description
        holder.eventName.setText(event.getName());
        holder.eventAddress.setText(event.getAddress());
        holder.eventDescription.setText(event.getDescription());

        // Load image using Glide for better handling of image URIs
        if (event.getImageUri() != null && !event.getImageUri().isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(Uri.parse(event.getImageUri()))  // Load image from URI
                    .into(holder.eventImage);  // Set the image in the ImageView
        } else {
            // Set a default image in case the URI is empty or null
            holder.eventImage.setImageResource(R.drawable.ic_launcher_foreground);
        }
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    // Method to update the event list in the adapter
    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
        notifyDataSetChanged();  // Notify adapter to refresh data
    }

    // ViewHolder for event item
    public static class EventViewHolder extends RecyclerView.ViewHolder {
        TextView eventName, eventAddress, eventDescription;
        ImageView eventImage;

        public EventViewHolder(View itemView) {
            super(itemView);
            eventName = itemView.findViewById(R.id.event_name);
            eventAddress = itemView.findViewById(R.id.event_address);
            eventDescription = itemView.findViewById(R.id.event_description);
            eventImage = itemView.findViewById(R.id.event_image);
        }
    }
}
