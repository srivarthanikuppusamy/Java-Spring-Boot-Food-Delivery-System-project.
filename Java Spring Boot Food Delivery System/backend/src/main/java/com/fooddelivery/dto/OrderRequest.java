package com.fooddelivery.dto;

public class OrderRequest {
    private String item;
    private int quantity;
    private double price;
    private String pincode;
    private String coupon;
    private String address;
    private double payment;

    // Getters and Setters
    public String getItem() { return item; }
    public void setItem(String item) { this.item = item; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getPincode() { return pincode; }
    public void setPincode(String pincode) { this.pincode = pincode; }

    public String getCoupon() { return coupon; }
    public void setCoupon(String coupon) { this.coupon = coupon; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public double getPayment() { return payment; }
    public void setPayment(double payment) { this.payment = payment; }
}