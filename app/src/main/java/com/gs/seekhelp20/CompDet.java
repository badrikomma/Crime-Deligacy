package com.gs.seekhelp20;

public class CompDet {

    String victimName,area,landmark,pincode,city,description,userid,imageUrl,date,time;

    public CompDet(){}

    public CompDet(String victimName, String area, String landmark, String pincode, String city, String description,String userid,String imageUrl,String date,String time) {
        this.victimName = victimName;
        this.area = area;
        this.landmark = landmark;
        this.pincode = pincode;
        this.city = city;
        this.description = description;
        this.userid = userid;
        this.imageUrl = imageUrl;
        this.date = date;
        this.time = time;
    }

    public String getVictimName() {
        return victimName;
    }

    public void setVictimName(String victimName) {
        this.victimName = victimName;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
