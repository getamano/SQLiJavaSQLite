package com.aman;


public class PasswordManager {

    static PasswordManager manager;

    static PasswordDatabase database;
    static PasswordUI passwordUI;


    public static void main(String[] args) {

        manager = new PasswordManager();
        database = new PasswordDatabase();
        passwordUI = new PasswordUI(manager);

    }

    public AuthResult authenticateUser(String login, String password) {

        AuthResult result = database.authenticateUser(login, password);
        
        // if error, log error message
        if (result.error != null) {
            System.out.println("Error trying to authenticate user:");
            result.error.printStackTrace();
        }
        return result;
        
    }

}
