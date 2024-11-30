package com.example.login_eldroid;

public class LoginResponse {
    private boolean success;
    private String message;
    private UserData user;

    // Constructor
    public LoginResponse(boolean success, String message, UserData user) {
        this.success = success;
        this.message = message;
        this.user = user;
    }

    // Getters
    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public UserData getUser() {
        return user;
    }

    // Setters
    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setUser(UserData user) {
        this.user = user;
    }
}
