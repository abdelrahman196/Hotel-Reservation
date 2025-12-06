public class Room {
    private int id;
    private String type;
    private double price;
    private boolean isOccupied;

    public Room(int id, String type, double price, boolean isOccupied) {
        this.id = id;
        this.type = type;
        this.price = price;
        this.isOccupied = isOccupied;
    }

    public int getId() {
        return id;
    }
    public String getType() {
        return type;
    }
    public double getPrice() {
        return price;
    }
    public boolean isOccupied() {
        return isOccupied;
    }
    public void setOccupied(boolean isOccupied) {
        this.isOccupied = isOccupied;
    }
}
