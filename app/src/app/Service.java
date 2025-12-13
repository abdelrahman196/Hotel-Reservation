package app;

public class Service {

    private int ID;
    private String name;
    private String description;
    private int price;
    private boolean available;

    public Service(int ID, String name, String description, int price, boolean available) {
        this.ID = ID;
        this.name = name;
        this.description = description;
        this.price = price;
        this.available = available;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean isAvailable() {
        return available;
    }


    public String parsePrint(){
        return ("ID:" + ID + "\nName:" + name + "\ndescription:" + description + "\nprice:" + String.valueOf(price));
    }
}
