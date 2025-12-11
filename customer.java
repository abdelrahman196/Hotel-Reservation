import java.util.ArrayList;

public class customer {
    protected int id;
    protected String name;
    protected String phone;
    private ArrayList<Integer> history;
    private int currentRoom;
    public customer(int id, String name, String phone, ArrayList<Integer> history, int currentRoom) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.history = new ArrayList<>();
        this.currentRoom = currentRoom;
    }
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public int getCurrentRoom() {
        return currentRoom;
    }
    public void setCurrentRoom(int currentRoom) {
        this.currentRoom = currentRoom;
    }
    public ArrayList<Integer> getHistory() {
        return history;
    }
    public void addReservation(int reservation){
        this.history.add(new Integer(reservation) );
    }
    public void addReservation(ArrayList<Integer> reservation){
        ArrayList<String> mergedList = new ArrayList<>();
        mergedList.addAll(this.history);
        mergedList.addAll(reservation);
        this.history=mergedList;
    }
}
