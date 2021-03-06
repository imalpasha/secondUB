package com.app.comic.ui.Model.Receive;

/*
 * Created by ImalPasha on 11/6/2015.
 */

 /* Response From API */

public class LoginReceive {

    private String status;
    private String message;
    private String type;
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public LoginReceive(LoginReceive data) {

        this.status = data.getStatus();
        this.message = data.getMessage();
        this.type = data.getType();
        this.phone = data.getPhone();

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
