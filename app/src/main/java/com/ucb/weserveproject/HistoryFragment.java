package com.ucb.weserveproject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class HistoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private HistoryPostAdapter historyPostAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.history_layout, container, false);

        recyclerView = view.findViewById(R.id.joined_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ArrayList<PostData> joinedPosts = loadJoinedPostsFromPreferences();

        historyPostAdapter = new HistoryPostAdapter(joinedPosts);
        recyclerView.setAdapter(historyPostAdapter);

        return view;
    }

    private ArrayList<PostData> loadJoinedPostsFromPreferences() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("JoinedPosts", requireContext().MODE_PRIVATE);
        ArrayList<PostData> joinedPostDataList = new ArrayList<>();

        for (String key : sharedPreferences.getAll().keySet()) {
            if (key.startsWith("joined_post_")) { // Only consider keys that start with "joined_post_"
                String serializedPost = sharedPreferences.getString(key, "");
                if (!serializedPost.isEmpty()) {
                    String[] postParts = serializedPost.split("\\|");
                    if (postParts.length == 4) {
                        PostData postData = new PostData(postParts[0], postParts[1], postParts[2], postParts[3]);
                        joinedPostDataList.add(postData);
                    }
                }
            }
        }
        return joinedPostDataList;
    }

    public static class PostData {
        private final String eventName;
        private final String address;
        private final String description;
        private final String imageUri;

        public PostData(String eventName, String address, String description, String imageUri) {
            this.eventName = eventName;
            this.address = address;
            this.description = description;
            this.imageUri = imageUri;
        }

        public String getEventName() {
            return eventName;
        }

        public String getAddress() {
            return address;
        }

        public String getDescription() {
            return description;
        }

        public String getImageUri() {
            return imageUri;
        }
    }

    public static class HistoryPostAdapter extends RecyclerView.Adapter<HistoryPostAdapter.HistoryPostViewHolder> {

        private final ArrayList<PostData> joinedPostDataList;

        public HistoryPostAdapter(ArrayList<PostData> joinedPostDataList) {
            this.joinedPostDataList = joinedPostDataList;
        }

        @NonNull
        @Override
        public HistoryPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item_layout, parent, false);
            return new HistoryPostViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull HistoryPostViewHolder holder, int position) {
            PostData postData = joinedPostDataList.get(position);

            holder.eventName.setText(postData.getEventName());
            holder.address.setText(postData.getAddress());
            holder.description.setText(postData.getDescription());

            if (postData.getImageUri() != null && !postData.getImageUri().isEmpty()) {
                Glide.with(holder.itemView.getContext())
                        .load(postData.getImageUri())
                        .into(holder.imageView);
            } else {
                holder.imageView.setImageResource(R.drawable.settings_container); // Default image
            }
        }

        @Override
        public int getItemCount() {
            return joinedPostDataList.size();
        }

        public static class HistoryPostViewHolder extends RecyclerView.ViewHolder {
            TextView eventName, address, description;
            ImageView imageView;

            public HistoryPostViewHolder(View itemView) {
                super(itemView);
                eventName = itemView.findViewById(R.id.joined_event_name);
                address = itemView.findViewById(R.id.joined_event_address);
                description = itemView.findViewById(R.id.joined_event_description);
                imageView = itemView.findViewById(R.id.joined_event_image);
            }
        }
    }
}