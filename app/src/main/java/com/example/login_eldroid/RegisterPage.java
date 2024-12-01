package com.example.login_eldroid;

import android.app.MediaRouteButton;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.login_eldroid.LoginRequest;
import com.example.login_eldroid.LoginResponse;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterPage extends AppCompatActivity {

    private EditText etFirstName, etLastName, etEmail, etPassword;
    private Button btnRegister;
    private ProgressBar progressBar;
    private TextView tvError;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_page);

        etFirstName = findViewById(R.id.editFirstName);
        etLastName = findViewById(R.id.editLastName);
        etEmail = findViewById(R.id.editEmail);
        etPassword = findViewById(R.id.editPassword);
        progressBar = findViewById(R.id.progressBar);
        btnRegister = findViewById(R.id.buttonRegister);
        tvError = findViewById(R.id.textViewError);

        btnRegister.setOnClickListener(view -> registerUser());
    }

    private void registerUser(){
        String Name = etFirstName.getText().toString();
        String LastName = etLastName.getText().toString();
        String Email = etEmail.getText().toString();
        String Password = etPassword.getText().toString();


        progressBar.setVisibility(View.VISIBLE);
        tvError.setVisibility(View.GONE);

        if (Name.isEmpty() || Password.isEmpty() || Email.isEmpty() || LastName.isEmpty()) {
            progressBar.setVisibility(View.GONE);
            tvError.setText("Name, Password , Email and Last Name cannot be empty");
            tvError.setVisibility(View.VISIBLE);
            return;
        }

        RegisterRequest RegisterRequest = new RegisterRequest(Name, Password, Email, LastName);

        // Make the API call using Retrofit
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<RegisterResponse> call = apiService.register(RegisterRequest);

        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                progressBar.setVisibility(View.GONE);
                Log.e("RegisterPage", "Response: " + response.toString());  // Log the response
                if (response.isSuccessful() && response.body() != null) {
                    RegisterResponse registerResponse = response.body();
                    if (registerResponse.isSuccess()) {
                        // Handle successful login (For example, move to another screen or show a success message)
                        Toast.makeText(RegisterPage.this, "Register successful", Toast.LENGTH_SHORT).show();
                        // Proceed to the next screen or perform further actions like saving the token
                        Intent intent = new Intent(RegisterPage.this, LoginPage.class); // Assuming HomePage is the next activity
                        startActivity(intent);  // Start HomePage activity

                        // Optional: Finish current activity to prevent returning to login screen when pressing back
                        finish();

                    } else {
                        tvError.setText("Lacking credentials");
                        tvError.setVisibility(View.VISIBLE);
                    }
                } else {
                    tvError.setText("Register failed. Try again.");
                    Log.d("RegisterPage", "Register failed: " + response.toString() + response.code() + response.message());
                    tvError.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                tvError.setText("Error: " + t.getMessage());
                tvError.setVisibility(View.VISIBLE);

                // Log the error and inputted password when the request fails
                Log.e("RegisterPage", "Error: " + t.getMessage() + " - Password entered: " + Password);  // Log the password here
            }
        });
    }

}