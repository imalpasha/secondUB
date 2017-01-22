package com.app.comic.ui.Model.Receive;

public class RateReceive {

    private String status;
    private String message;

    public RateReceive(RateReceive data) {

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
