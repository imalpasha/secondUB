package com.app.comic.ui.Model.Request;

/**
 * Created by Dell on 11/4/2015.
 */
public class SignDriverRequest {

    /*Local Data Send To Server*/
    String studentID;
    String username;
    String password;
    String gender;
    String phone;
    String smoker;
    String prefGender;
    String platNumber;
    String carType;
    String licenseNumber;

    public String getPlatNumber() {
        return platNumber;
    }

    public void setPlatNumber(String platNumber) {
        this.platNumber = platNumber;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getPrefGender() {
        return prefGender;
    }

    public void setPrefGender(String prefGender) {
        this.prefGender = prefGender;
    }


    /*Initiate Class*/
    public SignDriverRequest() {
    }

    public SignDriverRequest(SignDriverRequest data) {

        this.gender = data.getGender();
        this.studentID = data.getStudentID();
        this.username = data.getUsername();
        this.password = data.getPassword();
        this.phone = data.getPhone();
        this.smoker = data.getSmoker();
        this.prefGender = data.getPrefGender();
        this.carType = data.getCarType();
        this.platNumber = data.getPlatNumber();
        this.licenseNumber = data.getLicenseNumber();

    }


}
