package com.ucb.weserveproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class EventFragment extends Fragment {

    private RecyclerView recyclerView;
    private JoinedPostAdapter joinedPostAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.events_layout, container, false);

        recyclerView = view.findViewById(R.id.joined_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ArrayList<PostData> joinedPosts = loadJoinedPostsFromPreferences();

        joinedPostAdapter = new JoinedPostAdapter(joinedPosts);
        recyclerView.setAdapter(joinedPostAdapter);

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
        String eventName;
        String address;
        String description;
        String imageUri;

        public PostData(String eventName, String address, String description, String imageUri) {
            this.eventName = eventName;
            this.address = address;
            this.description = description;
            this.imageUri = imageUri;
        }
    }

    public static class JoinedPostAdapter extends RecyclerView.Adapter<JoinedPostAdapter.JoinedPostViewHolder> {

        private final ArrayList<PostData> joinedPostDataList;

        public JoinedPostAdapter(ArrayList<PostData> joinedPostDataList) {
            this.joinedPostDataList = joinedPostDataList;
        }

        @NonNull
        @Override
        public JoinedPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item_layout, parent, false);
            return new JoinedPostViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull JoinedPostViewHolder holder, int position) {
            PostData postData = joinedPostDataList.get(position);

            holder.eventName.setText(postData.eventName);
            holder.address.setText(postData.address);
            holder.description.setText(postData.description);

            if (postData.imageUri != null && !postData.imageUri.isEmpty()) {
                Glide.with(holder.itemView.getContext())
                        .load(postData.imageUri)
                        .into(holder.imageView);
            } else {
                holder.imageView.setImageResource(R.drawable.settings_container); // Default image
            }

            holder.cancelButton.setOnClickListener(v -> {
                removeItem(position, holder.itemView.getContext());
            });
        }

        @Override
        public int getItemCount() {
            return joinedPostDataList.size();
        }

        private void removeItem(int position, Context context) {
            PostData postData = joinedPostDataList.get(position);

            SharedPreferences sharedPreferences = context.getSharedPreferences("JoinedPosts", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            String keyToRemove = "joined_post_" + postData.eventName; // Example key format
            editor.remove(keyToRemove);
            editor.apply();

            joinedPostDataList.remove(position);

            notifyItemRemoved(position);
            notifyItemRangeChanged(position, joinedPostDataList.size());
        }

        public static class JoinedPostViewHolder extends RecyclerView.ViewHolder {
            TextView eventName, address, description;
            ImageView imageView;
            ImageButton cancelButton;

            public JoinedPostViewHolder(View itemView) {
                super(itemView);
                eventName = itemView.findViewById(R.id.joined_event_name);
                address = itemView.findViewById(R.id.joined_event_address);
                description = itemView.findViewById(R.id.joined_event_description);
                imageView = itemView.findViewById(R.id.joined_event_image);
                cancelButton = itemView.findViewById(R.id.cancel_button);
            }
        }
    }
}
