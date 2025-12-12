import java.util.ArrayList;
import java.util.Date;

public class Room {
    private int id;
    private ArrayList<Customer> residents;
    private ArrayList<Integer> services;
    private String type;
    private int price;
    private boolean available;
    private Date entryDate;
    private Date expirationDate;

    public Room(int id, String type, int price, boolean isOccupied) {
        this.id = id;
        this.type = type;
        this.price = price;
        this.available = available;
        this.residents = new ArrayList<>();
        this.services = new ArrayList<>();
        this.entryDate = new Date();
        this.expirationDate = new Date();
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
        services.add(serviceID);
    }
    public ArrayList<Integer> getService() {
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
}
