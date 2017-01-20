package com.app.comic.ui.Model.Receive;

public class PassengerInfoReceive {


    private String status;
    private String message;
    private Passenger info;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Passenger getInfo() {
        return info;
    }

    public void setInfo(Passenger info) {
        this.info = info;
    }


    public class Passenger {

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

        public String getUser_image() {
            return user_image;
        }

        public void setUser_image(String user_image) {
            this.user_image = user_image;
        }

        private String student_id;
        private String id;
        private String username;
        private String gender;
        private String phone;
        private String smoker;
        private String pref_gender;
        private String user_image;

    }

    public PassengerInfoReceive(PassengerInfoReceive data) {


        this.message = data.getMessage();
        this.status = data.getStatus();
        this.info = data.getInfo();


    }


}
