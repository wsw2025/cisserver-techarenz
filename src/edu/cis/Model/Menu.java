package edu.cis.Model;

import java.util.ArrayList;

public class Menu {
    private ArrayList<MenuItem> eatriumItems = new ArrayList<>();
    private String adminID;

//    public Menu(ArrayList<MenuItem> eatriumItems) {
//        this.eatriumItems = eatriumItems;
//    }

    // Getter and Setter for eatriumItems
    public ArrayList<MenuItem> getEatriumItems() {
        return eatriumItems;
    }

    public void setEatriumItems(ArrayList<MenuItem> orders) {
        this.eatriumItems = eatriumItems;
    }

    // Getter and Setter for adminID
    public String getAdminID() {
        return adminID;
    }

    public void setAdminID(String name) {
        this.adminID = adminID;
    }

}
