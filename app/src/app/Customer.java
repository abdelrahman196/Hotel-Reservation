package app;

import java.util.ArrayList;

public class Customer {
    protected int id;
    protected String name;
    protected String phone;
    private int currentRoom;
    public Customer(int id, String name, String phone, int currentRoom) {
        this.id = id;
        this.name = name;
        this.phone = phone;
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
    public String parsePrint(){
        String tmp= "ID:"+ this.id +"\n" +"Name:"+ this.name +"\n"+"phone:"+ this.phone +"\n"+"CurrentRoom:"+ this.currentRoom +"\n";
        return tmp;
    }
    public static int exist(){
        return 1;
    }
}
