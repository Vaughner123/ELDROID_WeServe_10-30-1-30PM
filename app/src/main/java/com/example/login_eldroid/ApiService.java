package com.example.login_eldroid;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("loginApi.php")
    Call<LoginResponse> login(@Body LoginRequest request);
    @POST("registerApi.php")
    Call<RegisterResponse> register(@Body RegisterRequest request);
}