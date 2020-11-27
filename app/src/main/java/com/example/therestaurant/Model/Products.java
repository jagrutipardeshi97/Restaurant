package com.example.therestaurant.Model;

public class Products
{
    public String date,description,image,pid,pname,price,time;

    public Products() {
    }

    public Products(String date, String description, String image, String pid, String pname, String price, String time) {
        this.date = date;
        this.description = description;
        this.image = image;
        this.pid = pid;
        this.pname = pname;
        this.price = price;
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
