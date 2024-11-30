package com.example.login_eldroid;

public class RegisterRequest {
    private String name;
    private String password;
    private String email;
    private String lastName;

    public RegisterRequest(String name, String password, String email, String lastName) {
        this.name = name;
        this.lastName = name;
        this.email = email;
        this.password = password;
    }
    public String getName() {
        return name;
    }
    public String getLastName() {
        return lastName;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
}
