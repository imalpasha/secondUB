package com.app.comic.ui.Model.Receive;

/*
 * Created by ImalPasha on 11/6/2015.
 */

 /* Response From API */

import com.app.comic.ui.Model.JSON.Driver;
import com.app.comic.ui.Model.JSON.Rate;

import java.util.ArrayList;

public class ListRidesReceive {

    private String status;
    private String message;
    private String type;
    private ArrayList<Passenger> passenger;
    private ArrayList<Driver> driver;
    private ArrayList<Rate> rate;

    public ArrayList<Rate> getRate() {
        return rate;
    }

    public void setRate(ArrayList<Rate> rate) {
        this.rate = rate;
    }

    public class Passenger{

        private String student_id;
        private String id;
        private String username;
        private String gender;
        private String phone;
        private String smoker;
        private String pref_gender;
        private String user_image;

        public String getUser_image() {
            return user_image;
        }

        public void setUser_image(String user_image) {
            this.user_image = user_image;
        }

        public String getStudent_id() {
            return student_id;
        }

        public void setStudent_id(String student_id) {
            this.student_id = student_id;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getSmoker() {
            return smoker;
        }

        public void setSmoker(String smoker) {
            this.smoker = smoker;
        }

        public String getPref_gender() {
            return pref_gender;
        }

        public void setPref_gender(String pref_gender) {
            this.pref_gender = pref_gender;
        }
    }

    public ListRidesReceive(ListRidesReceive data) {

        this.status = data.getStatus();
        this.message = data.getMessage();
        this.type = data.getType();
        this.passenger = data.getPassenger();
        this.driver = data.getDriver();

    }

    public ArrayList<Passenger> getPassenger() {
        return passenger;
    }

    public void setPassenger(ArrayList<Passenger> passenger) {
        this.passenger = passenger;
    }

    public ArrayList<Driver> getDriver() {
        return driver;
    }

    public void setDriver(ArrayList<Driver> driver) {
        this.driver = driver;
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
