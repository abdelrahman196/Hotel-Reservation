package app;

public class Service {

    private int id;
    private String name;
    private int price;
    private boolean available;
    private int usageCount;


    public Service(int id, String name, double price) {
        this(id, name, price, 0);
    }

    public Service(int id, String name, double price, int usageCount) {
        this.id = id;
        this.name = name;
        this.price = (int)price;
        this.usageCount = usageCount;
    }

    public void setUsageCount(int usageCount) {
        this.usageCount = usageCount;
    }

    public int getUsageCount() {
        return usageCount;
    }

    public void incrementUsage() {
        this.usageCount++;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
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
        return ("ID:" + id + "\nName:" + name + "\nprice:" + String.valueOf(price));
    }
}
