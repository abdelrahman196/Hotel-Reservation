package app;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

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
        return ("ID:" + String.valueOf(this.id) +"\nName:"+ name + "\nPhone:" + phone + "\nCurrentRoomID:" + String.valueOf(currentRoom));
    }
}
