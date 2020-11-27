package com.example.therestaurant.Model;

public class Admin
{
    String address,name,phone_no,password,image;

    public Admin() {
    }

    public Admin(String address, String name, String phone_no, String password, String image, String Pin) {
        this.address = address;
        this.name = name;
        this.phone_no = phone_no;
        this.password = password;
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
