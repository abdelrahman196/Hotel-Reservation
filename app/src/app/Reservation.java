package app;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class Reservation {

    private int ID;
    private int customerID;
    private int roomID;
    private List<Integer> services;
    private Date startDate;
    private Date endDate;
    private int price;

    public Reservation(int ID, int customerID, int roomID, Date startDate, Date endDate, int price){
        this.ID = ID;
        this.customerID = customerID;
        this.roomID = roomID;
        this.services = new ArrayList<>();
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public List<Integer> getServices() {
        return services;
    }

    public void setServices(List<Integer> services) {
        this.services = services;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }



    public void addService(int serviceID) {
    }

    public void addServices(List<Integer> serviceIDs) {
    }

    public int getClient() {
        return 0;
    }
}
