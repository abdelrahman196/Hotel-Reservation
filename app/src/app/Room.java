package app;

import java.util.ArrayList;
import java.util.Date;

public class Room {
    private int id;
    private ArrayList<Customer> residents;
    private ArrayList<Service> services;
    private String type;
    private int price;
    private boolean available;
    private Date entryDate;
    private Date expirationDate;

    public Room(int id, String type, int price, boolean isAvailable) {
        this.id = id;
        this.type = type;
        this.price = price;
        this.available =isAvailable;
        this.residents = new ArrayList<>();
        this.services = new ArrayList<>();
        this.entryDate = new Date();
        this.expirationDate = new Date();
    }
    public Room(int id, String type, int price, boolean isAvailable,long entryDate,long expirationDate,ArrayList<Integer> Residents,ArrayList<Integer> Services) {
        this.id = id;
        this.type = type;
        this.price = price;
        this.available =isAvailable;
        this.services = OtherService.getServices(Services);
        this.entryDate = new Date(entryDate);
        this.expirationDate = new Date(expirationDate);
    }

    public int getId() {
        return id;
    }
    public String getType() {
        return type;
    }
    public int getPrice() {
        return price;
    }
    public void setAvailable(boolean available) {
        this.available = available;
    }
    public boolean isAvailable() {return available;}
    public void addResident(Customer customer) {
        residents.add(customer);
    }
    public ArrayList<Customer> getResidents() {
        return residents;
    }
    public void setType(String type) {
        this.type = type;
    }
    public void addService(int serviceID) {

    }
    public ArrayList<Service> getServices() {
        return services;
    }
    public Date getEntryDate() {
        return entryDate;
    }
    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }
    public Date getExpirationDate() {
        return expirationDate;
    }
    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
    public String parsePrint(){
        StringBuilder Residents = new StringBuilder();
        StringBuilder Services =new StringBuilder();
        if(!available){
            for(int i=0;i<residents.size();i++){
                Residents.append(residents.get(i).getName()).append(",");
            }
            for(int i=0;i<services.size();i++){
                Services.append(services.get(i).getName()).append(",");
            }
        }
        return "ID:"+id+ "\ntype:" + type + "\nprice" + String.valueOf(price) + ((available)?"\nthe room is available":("\nthe room is occupied since:"+entryDate.toString()+" until:" + expirationDate.toString()+"\nresidents:"+Residents.toString() +"\nServices:" + Services.toString()));
    }
    public String parseStore(){
        StringBuilder Residents = new StringBuilder();
        StringBuilder Services =new StringBuilder();
        if(!available){
            for(int i=0;i<residents.size();i++){
                Residents.append(residents.get(i).getId()).append(":");
            }
            for(int i=0;i<services.size();i++){
                Services.append(services.get(i).getId()).append(":");
            }
            return String.valueOf(this.getId()) + "/" +type +"/" +String.valueOf(price) +"/"+"false"+"/"+entryDate.getTime()+"/"+expirationDate.getTime()+"/"+Residents.toString()+"/"+Services.toString();
        }
        else{
            return String.valueOf(this.getId()) + "/" +type +"/" +String.valueOf(price) +"/"+"false"+"////";
        }
    }

    public void setPrice(double price) {
        this.price = (int) price;
    }
}
