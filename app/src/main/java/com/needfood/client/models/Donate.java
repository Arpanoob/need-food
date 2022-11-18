package com.needfood.client.models;

import com.google.firebase.firestore.GeoPoint;

import java.io.Serializable;

public class Donate implements Serializable {
    private String address, by;
    private GeoPoint location;
    private String name, phone, photo;

    public Donate() {
    }

    public Donate(String address, String by, GeoPoint location, String name, String phone, String photo) {
        this.address = address;
        this.by = by;
        this.location = location;
        this.name = name;
        this.phone = phone;
        this.photo = photo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getPhone() {
        return phone;
    }


}
