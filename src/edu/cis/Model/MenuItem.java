package edu.cis.Model;

public class MenuItem {
    private String name;
    private String description;
    private double price;
    private String id;
    private int amountAvailable = 10;
    private String type;

    public MenuItem(String name, String description, double price, String id, String type) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.id = id;
        this.type = type;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Setter for name
    public void setName(String name) {
        this.name = name;
    }

    // Getter for description
    public String getDescription() {
        return description;
    }

    // Setter for description
    public void setDescription(String description) {
        this.description = description;
    }

    // Getter for price
    public double getPrice() {
        return price;
    }

    // Setter for price
    public void setPrice(double price) {
        this.price = price;
    }

    // Getter for id
    public String getId() {
        return id;
    }

    // Setter for id
    public void setId(String id) {
        this.id = id;
    }

    // Getter for amountAvailable
    public int getAmountAvailable() {
        return amountAvailable;
    }

    // Setter for amountAvailable
    public void setAmountAvailable(int amountAvailable) {
        this.amountAvailable = amountAvailable;
    }

    // Getter for type
    public String getType() {
        return type;
    }

    // Setter for type
    public void setType(String type) {
        this.type = type;
    }

}
