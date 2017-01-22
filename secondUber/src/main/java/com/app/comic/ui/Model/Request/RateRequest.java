package com.app.comic.ui.Model.Request;

public class RateRequest {

    /*Local Data Send To Server*/
    String username;
    String rate;
    String driverID;

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getDriverID() {
        return driverID;
    }

    public void setDriverID(String driverID) {
        this.driverID = driverID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /*Initiate Class*/
    public RateRequest() {
    }

    public RateRequest(RateRequest data) {
        username = data.getUsername();
        rate = data.getRate();
        driverID = data.getDriverID();
    }

}
