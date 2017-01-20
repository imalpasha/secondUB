package com.app.comic.ui.Model.Request;

/**
 * Created by Dell on 11/4/2015.
 */
public class ListRidesRequest {

    String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /*Initiate Class*/
    public ListRidesRequest() {

    }

    public ListRidesRequest(ListRidesRequest data) {
        username = data.getUsername();
    }


}
