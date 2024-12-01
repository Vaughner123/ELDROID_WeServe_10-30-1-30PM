package com.ucb.weserveproject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class PostFragment extends Fragment {

    private EditText eventNameEditText, addressEditText, descriptionEditText;
    private ImageView imageView;
    private Button postButton;
    private Uri uploadedImageUri;
    private PostViewModel postViewModel;
    private static final int IMAGE_PICKER_REQUEST_CODE = 1001; // Request code for image picker

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.post_layout, container, false);

        eventNameEditText = view.findViewById(R.id.name_of_event_input);
        addressEditText = view.findViewById(R.id.address_input);
        descriptionEditText = view.findViewById(R.id.description_input);
        imageView = view.findViewById(R.id.upload_image_icon);
        postButton = view.findViewById(R.id.add_post_button);

        postViewModel = new ViewModelProvider(requireActivity()).get(PostViewModel.class);

        imageView.setOnClickListener(v -> openImagePicker());

        postButton.setOnClickListener(v -> {
            String eventName = eventNameEditText.getText().toString().trim();
            String address = addressEditText.getText().toString().trim();
            String description = descriptionEditText.getText().toString().trim();

            if (eventName.isEmpty() || address.isEmpty() || description.isEmpty()) {
                Toast.makeText(getActivity(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            DashboardFragment.PostData newPost = new DashboardFragment.PostData(eventName, address, description,
                    uploadedImageUri != null ? uploadedImageUri.toString() : "");
            postViewModel.addPost(newPost);

            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).navigateTo(R.id.navigation_dashboard);
            }
        });

        return view;
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICKER_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_PICKER_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            uploadedImageUri = data.getData();
            // Show the selected image in ImageView
            imageView.setImageURI(uploadedImageUri);
        }
    }
}
