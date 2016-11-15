package com.example.ahsaniqbal.contactlist.Models;

/**
 * Created by ahsaniqbal on 11/15/16.
 */

public class AIContact {

    public String name;

    public String email;

    public String phone;

    public String country;

    public String getDisplayName() {
        return name;
    }

    public String getDisplayEmail() {
        return email;
    }

    public String getDisplayPhone() {
        return phone;
    }

    public String getDisplayCountry() {
        return country;
    }
}
