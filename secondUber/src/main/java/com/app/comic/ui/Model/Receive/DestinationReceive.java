package com.app.comic.ui.Model.Receive;

/*
 * Created by ImalPasha on 11/6/2015.
 */

 /* Response From API */

public class DestinationReceive {

    private String status;
    private String message;

    public DestinationReceive(DestinationReceive data) {

        this.status = data.getStatus();
        this.message = data.getMessage();

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
