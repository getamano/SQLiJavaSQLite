package com.aman;


import java.sql.*;

public class PasswordDatabase {

    private static final String DB_CONNECTION_URL = "jdbc:sqlite:users.sqlite";


    private static final String PASSWORD_TABLE = "passwords";
    private static final String USERNAME_COL = "username";
    private static final String LOGIN_COL = "login";
    private static final String PASSWORD_COL = "password";

    PasswordDatabase() {
    
    }
    
    
    public AuthResult authenticateUser(String login, String password) {

        //Note the allowMultiQueries=true parameter. This is needed to allow more than one query in an executeQuery statement
        //Can be very useful... but also permits abuse.

        try ( Connection connection = DriverManager.getConnection(DB_CONNECTION_URL);
                            Statement statement = connection.createStatement()  ) {

            // Don't do this! Don't concatenate strings to create SQL statements!
            
            String authSQL = "SELECT * FROM " + PASSWORD_TABLE + " WHERE " + LOGIN_COL + " = '" + login + "' AND " + PASSWORD_COL + " = '" + password + "'";
            System.out.println(authSQL);   //just for testing!
            ResultSet rs = statement.executeQuery(authSQL);
            //If login is in the database, and password is the password for that account, then user is authenticated.

            String username;
    
            if (rs.next()) {
                username = rs.getString(USERNAME_COL);   //Return the user's name
            } else {
                //No results
                username = null;   //Use null to indicate user with this password not found - user not authenticated
            }
    
            rs.close();
            
            return new AuthResult(username, null);   // Return username and error - no error, so second parameter is null.

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return new AuthResult(null, sqle);  // Null for no user, but provide DB error.
        }
    }

}
