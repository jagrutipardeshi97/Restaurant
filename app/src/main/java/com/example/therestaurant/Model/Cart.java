package com.example.therestaurant.Model;

public class Cart
{
    private  String pid, pname, price, quantity, table, waiterName ,waiterPhone;

    public Cart() {
    }

    public Cart(String pid, String pname, String price, String quantity, String table, String waiterName, String waiterPhone) {
        this.pid = pid;
        this.pname = pname;
        this.price = price;
        this.quantity = quantity;
        this.table = table;
        this.waiterName = waiterName;
        this.waiterPhone = waiterPhone;
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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getWaiterName() {
        return waiterName;
    }

    public void setWaiterName(String waiterName) {
        this.waiterName = waiterName;
    }

    public String getWaiterPhone() {
        return waiterPhone;
    }

    public void setWaiterPhone(String waiterPhone) {
        this.waiterPhone = waiterPhone;
    }
}
