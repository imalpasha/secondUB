package com.app.comic.ui.Model.Request;

/**
 * Created by Dell on 11/4/2015.
 */
public class DriverInfoRequest {

    /*Local Data Send To Server*/
    String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /*Initiate Class*/
    public DriverInfoRequest() {
    }

    public DriverInfoRequest(DriverInfoRequest data) {
        username = data.getUsername();
    }


}
