package com.app.comic.ui.Model.Request;

/**
 * Created by Dell on 11/4/2015.
 */
public class SelectRequest {


    /*Local Data Send To Server*/
    String driverID;
    String username;
    String page;

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDriverID() {
        return driverID;
    }

    public void setDriverID(String driverID) {
        this.driverID = driverID;
    }

    /*Initiate Class*/
    public SelectRequest() {

    }


    public SelectRequest(SelectRequest data) {
        this.username = data.getUsername();
        this.driverID = data.getDriverID();
    }


}
