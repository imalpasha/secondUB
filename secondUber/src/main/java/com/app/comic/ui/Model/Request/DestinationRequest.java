package com.app.comic.ui.Model.Request;

import android.widget.EditText;
import android.widget.TextView;

import com.app.comic.R;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import butterknife.InjectView;

/**
 * Created by Dell on 11/4/2015.
 */
public class DestinationRequest {

    private String rideAddress;
    private String rideState;
    private String rideDate;
    private String rideTime;
    private String rideDestinationState;
    private String rideDestinationAddress;
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRideAddress() {
        return rideAddress;
    }

    public void setRideAddress(String rideAddress) {
        this.rideAddress = rideAddress;
    }

    public String getRideState() {
        return rideState;
    }

    public void setRideState(String rideState) {
        this.rideState = rideState;
    }

    public String getRideDate() {
        return rideDate;
    }

    public void setRideDate(String rideDate) {
        this.rideDate = rideDate;
    }

    public String getRideTime() {
        return rideTime;
    }

    public void setRideTime(String rideTime) {
        this.rideTime = rideTime;
    }

    public String getRideDestinationAddress() {
        return rideDestinationAddress;
    }

    public void setRideDestinationAddress(String rideDestinationAddress) {
        this.rideDestinationAddress = rideDestinationAddress;
    }

    public String getRideDestinationState() {
        return rideDestinationState;
    }

    public void setRideDestinationState(String rideDestinationState) {
        this.rideDestinationState = rideDestinationState;
    }


    /*Initiate Class*/
    public DestinationRequest() {
    }


    public DestinationRequest(DestinationRequest data) {
        this.rideAddress = data.getRideAddress();
        this.rideDestinationAddress = data.getRideDestinationAddress();
        this.rideDate = data.getRideDate();
        this.rideTime = data.getRideTime();
        this.rideState = data.getRideState();
        this.rideDestinationState = data.getRideDestinationState();
        this.username = data.getUsername();
    }

}
