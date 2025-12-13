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
    public static ArrayList<Customer> getCustomer(){
        ArrayList<Customer> arr1=new ArrayList<>();
        try{
            File data = new File("app/src/app/DataBase/Customers.txt");
            Scanner dataS = new Scanner(data);
            String tmp=dataS.nextLine();
            String[] arr= tmp.split(",");
            for(int i=0;i< arr.length;i++){
                String[] tmp2 = arr[i].split("/");
                arr1.add(new Customer(Integer.parseInt(tmp2[0]), tmp2[1],tmp2[2], Integer.parseInt(tmp2[3])));
            }

        } catch (FileNotFoundException e) {
            System.out.println("IDC");
        }
        return arr1;
    }
    public static int exist(){
        return 1;
    }
}
