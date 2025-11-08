package com.example.myapplication.models;

public class LoginResponse {
    private String message;
    private String token; // Token-ul JWT

    public String getToken() {
        return token;
    }

    public String getMessage() {
        return message;
    }
}