package edu.cis.Model;

import java.util.ArrayList;

public class CISUser {
    private String userID;
    private String name;
    private String yearLevel;
    private ArrayList<Order> orders = new ArrayList<>();
    private double money=50;

    public CISUser(String userID, String name, String yearLevel){
        this.userID = userID;
        this.name = name;
        this.yearLevel = yearLevel;
    }

    // Getter and Setter for userID
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    // Getter and Setter for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and Setter for yearLevel
    public String getYearLevel() {
        return yearLevel;
    }

    public void setYearLevel(String yearLevel) {
        this.yearLevel = yearLevel;
    }

    // Getter and Setter for orders
    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    // Getter and Setter for money
    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String toString(){
        return userID + " " + name + " " + yearLevel + " " + orders.toString() + " " + money;
    }

}
