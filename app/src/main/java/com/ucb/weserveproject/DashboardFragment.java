package com.ucb.weserveproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private PostViewModel postViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dashboard_layout, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        postViewModel = new ViewModelProvider(requireActivity()).get(PostViewModel.class);

        ArrayList<PostData> savedPosts = loadPostsFromPreferences();
        for (PostData post : savedPosts) {
            postViewModel.addPost(post);
        }

        postViewModel.getPostDataList().observe(getViewLifecycleOwner(), postDataList -> {
            postAdapter = new PostAdapter(postDataList, this::onJoinActivity);
            recyclerView.setAdapter(postAdapter);
        });

        Bundle bundle = getArguments();
        if (bundle != null) {
            String eventName = bundle.getString("event_name", "Default Event Name");
            String address = bundle.getString("address", "No Address");
            String description = bundle.getString("description", "No Description");
            String imageUri = bundle.getString("image_uri", "");

            // Create a new post and add it to ViewModel
            PostData newPost = new PostData(eventName, address, description, imageUri);
            postViewModel.addPost(newPost);

            // Save the new post to SharedPreferences
            savePostToPreferences(newPost);
        }

        return view;
    }

    private void onJoinActivity(PostData postData) {
        Toast.makeText(getContext(), "You joined this activity!", Toast.LENGTH_SHORT).show();

        saveJoinedPost(postData);

        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).navigateTo(R.id.navigation_events);
        } else {
            Toast.makeText(getContext(), "Unable to navigate!", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveJoinedPost(PostData postData) {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("JoinedPosts", requireContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String serializedPost = postData.eventName + "|" + postData.address + "|" + postData.description + "|" + postData.imageUri;

        String key = "joined_post_" + postData.eventName + "_" + System.currentTimeMillis();
        editor.putString(key, serializedPost);
        editor.apply();
    }

    private void savePostToPreferences(PostData postData) {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("PostPrefs", requireContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String serializedPost = postData.eventName + "|" + postData.address + "|" + postData.description + "|" + postData.imageUri;

        String key = "post_" + System.currentTimeMillis();
        editor.putString(key, serializedPost);
        editor.apply();
    }

    private ArrayList<PostData> loadPostsFromPreferences() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("PostPrefs", requireContext().MODE_PRIVATE);
        ArrayList<PostData> postDataList = new ArrayList<>();

        for (String key : sharedPreferences.getAll().keySet()) {
            if (key.startsWith("post_")) { // Only consider keys that start with "post_"
                String serializedPost = sharedPreferences.getString(key, "");
                if (!serializedPost.isEmpty()) {
                    // Deserialize the data
                    String[] postParts = serializedPost.split("\\|");
                    if (postParts.length == 4) {
                        PostData postData = new PostData(postParts[0], postParts[1], postParts[2], postParts[3]);
                        postDataList.add(postData);
                    }
                }
            }
        }
        return postDataList;
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

    public static class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

        private final ArrayList<PostData> postDataList;
        private final OnJoinClickListener joinClickListener;

        public interface OnJoinClickListener {
            void onJoinClick(PostData postData);
        }

        public PostAdapter(ArrayList<PostData> postDataList, OnJoinClickListener joinClickListener) {
            this.postDataList = postDataList;
            this.joinClickListener = joinClickListener;
        }

        @Override
        public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view, parent, false);
            return new PostViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(PostViewHolder holder, int position) {
            PostData postData = postDataList.get(position);

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

            holder.joinButton.setOnClickListener(view -> joinClickListener.onJoinClick(postData));

            holder.participantsIcon.setOnClickListener(view -> {
                Intent intent = new Intent(holder.itemView.getContext(), ParticipantsActivity.class);
                intent.putExtra("event_name", postData.eventName); // Pass the event name to filter participants
                holder.itemView.getContext().startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return postDataList.size();
        }

        public static class PostViewHolder extends RecyclerView.ViewHolder {
            TextView eventName, address, description;
            ImageView imageView, participantsIcon;
            Button joinButton;

            public PostViewHolder(View itemView) {
                super(itemView);
                eventName = itemView.findViewById(R.id.name_of_event_text_view);
                address = itemView.findViewById(R.id.address_input);
                description = itemView.findViewById(R.id.description_text_view);
                imageView = itemView.findViewById(R.id.post_image);
                participantsIcon = itemView.findViewById(R.id.participants_icon); // Reference icon
                joinButton = itemView.findViewById(R.id.join_button);
            }
        }
    }

    public void logout() {
        SharedPreferences userPrefs = requireActivity().getSharedPreferences("UserPrefs", requireContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = userPrefs.edit();

        editor.clear();
        editor.apply();

        Intent intent = new Intent(requireActivity(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        requireActivity().finish();
    }
}
