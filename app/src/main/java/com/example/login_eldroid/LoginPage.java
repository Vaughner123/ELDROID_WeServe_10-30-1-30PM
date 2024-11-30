package com.example.login_eldroid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;  // Add this import to use logging
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.login_eldroid.LoginRequest;
import com.example.login_eldroid.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPage extends AppCompatActivity {
    private EditText etUsername, etPassword;
    private Button btnLogin;
    private Button btnRegister;
    private ProgressBar progressBar;
    private TextView tvError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        progressBar = findViewById(R.id.progressBar);
        tvError = findViewById(R.id.textViewError);

        btnLogin.setOnClickListener(view -> loginUser());

        btnRegister.setOnClickListener(view -> {
            Intent intent = new Intent(LoginPage.this, RegisterPage.class);
            startActivity(intent);
        });
    }

    private void loginUser() {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        // Show the progress bar while logging in
        progressBar.setVisibility(View.VISIBLE);
        tvError.setVisibility(View.GONE);

        // Validate input
        if (username.isEmpty() || password.isEmpty()) {
            progressBar.setVisibility(View.GONE);
            tvError.setText("Username or password cannot be empty");
            tvError.setVisibility(View.VISIBLE);
            return;
        }

        // Log the entered password (for debugging)
        Log.d("LoginPage", "Entered Password: " + password);  // This will output the password in the Logcat

        // You can also show the password in a Toast (be careful in production code with showing passwords like this)
        // Toast.makeText(LoginPage.this, "Entered Password: " + password, Toast.LENGTH_SHORT).show();

        // Create LoginRequest object to send
        LoginRequest loginRequest = new LoginRequest(username, password);

        // Make the API call using Retrofit
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<LoginResponse> call = apiService.login(loginRequest);

        // Handle the API response
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                progressBar.setVisibility(View.GONE);
                Log.e("LoginPage", "Response: " + response.toString());  // Log the response
                if (response.isSuccessful() && response.body() != null) {
                    LoginResponse loginResponse = response.body();
                    if (loginResponse.isSuccess()) {
                        // Handle successful login (For example, move to another screen or show a success message)
                        Toast.makeText(LoginPage.this, "Login successful", Toast.LENGTH_SHORT).show();
                        // Proceed to the next screen or perform further actions like saving the token
                        Intent intent = new Intent(LoginPage.this, MainActivity.class); // Assuming HomePage is the next activity
                        startActivity(intent);  // Start HomePage activity

                        // Optional: Finish current activity to prevent returning to login screen when pressing back
                        finish();

                    } else {
                        tvError.setText("Invalid credentials");
                        tvError.setVisibility(View.VISIBLE);
                    }
                } else {
                    tvError.setText("Login failed. Try again.");
                    Log.d("LoginPage", "Login failed: " + response.toString() + response.code() + response.message());
                    tvError.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                tvError.setText("Error: " + t.getMessage());
                tvError.setVisibility(View.VISIBLE);

                // Log the error and inputted password when the request fails
                Log.e("LoginPage", "Error: " + t.getMessage() + " - Password entered: " + password);  // Log the password here
            }
        });
    }
}
