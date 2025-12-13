package app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class Reservation {

    private int ID;

    private int roomID;

    private Date startDate;
    private Date endDate;
    private int price;
    private ArrayList<Service> services;
    private ArrayList<Customer> customers;
    private static int currentID = 1;
    private static String reservations="src/app/DataBase/reservations";
    public Reservation( int roomID, Date startDate, Date endDate, int price,ArrayList<Customer> customers,ArrayList<Service> services){
        this.ID = currentID++;
        this.customers = customers;
        this.roomID = roomID;
        this.services = services;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        Reservation.addReservation(this);
    }
    public Reservation(int ID, ArrayList<Customer> customers, int roomID, Date startDate, Date endDate, int price){
        this.ID = ID;
        this.customers = customers;
        this.roomID = roomID;
        this.services = new ArrayList<>();
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
    }

    public int getID() {
        return ID;
    }



    public ArrayList<Customer> getCustomerID() {
        return customers;
    }

    public void addCustomers(Customer customerID) {
        this.customers.add(customerID);
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public ArrayList<Service> getServices() {
        return services;
    }

    public void addServices(Service serviceID) {
        this.services.add(serviceID);
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



    public void addService(Service serviceID) {
        services.add(serviceID);
    }

    public ArrayList<Service> getServices(List<Integer> serviceIDs) {
        return services;
    }
    public String parsePrint(){
        StringBuilder Residents = new StringBuilder();
        StringBuilder Services =new StringBuilder();

            for(int i=0;i<customers.size();i++){
                Residents.append(customers.get(i).getName()).append(",");
            }
            for(int i=0;i<services.size();i++){
                Services.append(services.get(i).getName()).append(",");
            }
            return "Clients:" +  Residents.toString() +"\nRoom:" + roomID + "\nServices:" + Services + "\nentryDate: "+startDate.toString() + "\nendingDate:" + endDate.toString()+"\nPrice:"+ String.valueOf(price)+"$ per day";


    }
    public static void addReservation(Reservation reservation){
        File file= new File(reservations);
        try {Scanner input = new Scanner(file);
            String tmp;
            try {
                tmp=input.nextLine();
            }catch (NoSuchElementException e){
                tmp="";
            }

            input.close();
            ArrayList<Customer>  Clients= reservation.getCustomerID();
            ArrayList<Service>  Services= reservation.getServices();
            StringBuilder clients = new StringBuilder();
            StringBuilder services = new StringBuilder();
            for (int i=0;i<Clients.size();i++){
                clients.append(String.valueOf(Clients.get(i).getId())).append(":");
            }
            for (int i=0;i<Services.size();i++){
                services.append(String.valueOf(Services.get(i).getID())).append(":");
            }
            String tmp1=String.valueOf(Reservation.currentID-1)+ "/" + reservation.getRoomID() + "/" + reservation.getPrice()+ "/" + reservation.getStartDate().getTime() + "/" +reservation.getEndDate().getTime()+"/"+clients.toString()+"/"+services.toString()+",";
            try (PrintWriter writer = new PrintWriter(file)){
                writer.write(tmp+tmp1);
            }
        } catch (FileNotFoundException e) {
            System.out.println("IDC");
        }
    }

}
