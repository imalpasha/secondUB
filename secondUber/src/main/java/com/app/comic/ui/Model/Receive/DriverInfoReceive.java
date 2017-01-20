package com.app.comic.ui.Model.Receive;

public class DriverInfoReceive {


    private String status;
    private String message;
    private Driver info;


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

    public Driver getInfo() {
        return info;
    }

    public void setInfo(Driver info) {
        this.info = info;
    }


    public class Driver {

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

        private String student_id;
        private String id;
        private String username;
        private String gender;
        private String phone;
        private String smoker;
        private String pref_gender;
        private String license_number;
        private String plat_number;
        private String car_type;

        public String getLicense_number() {
            return license_number;
        }

        public void setLicense_number(String license_number) {
            this.license_number = license_number;
        }

        public String getPlat_number() {
            return plat_number;
        }

        public void setPlat_number(String plat_number) {
            this.plat_number = plat_number;
        }

        public String getCar_type() {
            return car_type;
        }

        public void setCar_type(String car_type) {
            this.car_type = car_type;
        }


    }

    public DriverInfoReceive(DriverInfoReceive data) {


        this.message = data.getMessage();
        this.status = data.getStatus();
        this.info = data.getInfo();


    }


}
