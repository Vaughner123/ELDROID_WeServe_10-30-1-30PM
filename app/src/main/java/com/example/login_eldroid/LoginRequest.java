package com.example.login_eldroid;

public class LoginRequest {
    private String email;
    private String password;

    // Constructor
    public LoginRequest(String username, String password) {
        this.email = username;
        this.password = password;
    }

    // Getters
    public String getUsername() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    // Setters
    public void setUsername(String username) {
        this.email = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
