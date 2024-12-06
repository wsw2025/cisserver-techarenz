package edu.cis.Model;

public class Order {
    private String itemID;
    private String type;
    private String orderID;

    public Order(String itemID, String type, String orderID) {
        this.itemID = itemID;
        this.type = type;
        this.orderID = orderID;
    }

    // Getter for itemID
    public String getItemID() {
        return itemID;
    }

    // Setter for itemID
    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    // Getter for type
    public String getType() {
        return type;
    }

    // Setter for type
    public void setType(String type) {
        this.type = type;
    }

    // Getter for orderID
    public String getOrderID() {
        return orderID;
    }

    // Setter for orderID
    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String toString(){
        return this.itemID + " " + this.type + " " + this.orderID;
    }

}
