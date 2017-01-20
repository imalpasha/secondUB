package com.app.comic.ui.Model.Request;

/**
 * Created by Dell on 11/4/2015.
 */
public class LoginRequest {

    /*Local Data Send To Server*/
    String username;
    String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /*Initiate Class*/
    public LoginRequest() {
    }

    public LoginRequest(LoginRequest data) {
        username = data.getUsername();
        password = data.getPassword();
    }


}
