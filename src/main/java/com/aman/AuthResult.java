package com.aman;


public class AuthResult {
    
    AuthResult(String username, Exception error) {
        this.error = error;
        this.username = username;
    }
    
    Exception error;
    String username;
}
