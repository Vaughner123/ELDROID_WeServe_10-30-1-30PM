package com.ucb.weserveproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ucb.weserveproject.Viewmodel.DashboardViewModel;

public class PostFragment extends Fragment {

    private static final int IMAGE_PICK_REQUEST_CODE = 1001;
    private static final int STORAGE_PERMISSION_REQUEST_CODE = 1002;

    private ImageView eventImage;
    private EditText eventName, eventAddress, eventDescription;
    private Button btnUploadImage, btnPost;

    private Uri selectedImageUri;

    private DashboardViewModel dashboardViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_post, container, false);

        // Initialize ViewModel
        dashboardViewModel = new ViewModelProvider(requireActivity()).get(DashboardViewModel.class);

        eventImage = rootView.findViewById(R.id.event_image);
        eventName = rootView.findViewById(R.id.event_name);
        eventAddress = rootView.findViewById(R.id.event_address);
        eventDescription = rootView.findViewById(R.id.event_description);
        btnUploadImage = rootView.findViewById(R.id.btn_upload_image);
        btnPost = rootView.findViewById(R.id.btn_post);

        // Check for permissions on start
        checkPermissions();

        btnUploadImage.setOnClickListener(v -> openImagePicker());

        btnPost.setOnClickListener(v -> postEvent());

        return rootView;
    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    STORAGE_PERMISSION_REQUEST_CODE);
        }
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openImagePicker();
            } else {
                Toast.makeText(getContext(), "Permission denied to access storage", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_PICK_REQUEST_CODE && resultCode == getActivity().RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            eventImage.setImageURI(selectedImageUri);
        }
    }

    private void postEvent() {
        String name = eventName.getText().toString();
        String address = eventAddress.getText().toString();
        String description = eventDescription.getText().toString();
        String imageUri = selectedImageUri != null ? selectedImageUri.toString() : "";

        if (name.isEmpty() || address.isEmpty() || description.isEmpty()) {
            Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Post the event to the ViewModel
        dashboardViewModel.postEvent(name, address, description, imageUri);

        // Notify the user and reset fields
        Toast.makeText(getContext(), "Event posted successfully!", Toast.LENGTH_SHORT).show();
        eventName.setText("");
        eventAddress.setText("");
        eventDescription.setText("");
        eventImage.setImageURI(null);
        selectedImageUri = null;
    }
}
