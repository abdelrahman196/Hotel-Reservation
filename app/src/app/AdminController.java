package app;

import java.io.*;
import java.util.*;
public class AdminController {
    private String employees;
    private String customers;
    private String reservations;
    public AdminController(String employee, String customers,String reservations) {
        this.employees = employee;
        this.customers = customers;
        this.reservations = reservations;
    }

    public static void main(String[] args){
            try{
                File customers = new File("DataBase/Customers");

                if(customers.exists()){
                    Scanner customers_file = new Scanner(customers);
                    String data = customers_file.nextLine();
                    System.out.println(data);
                }
            } catch (IOException e) {
                System.out.println("fuck u");
            }
    }

    public void addCustomer(String name,String phone){

    }
    public void deleteCustomer(int id){

    }
    public void updateCustomer(int id, String name,String phone){

    }
    public void addEmployee(String name,String phone,String password){

    }
    public void deleteEmployee(int id){

    }
    public void updateEmployee(int id,String name,String phone,String password){

    }
    public void addRoom(String type,int price){

    }
    public void deleteRoom(int id){

    }
    public void updateRoom(int id,String type,int price){

    }
    public  void addService(String type,int price,String description){

    }
    public void deleteService(int id){

    }
    public void updateService(int id,String type,int price,String description){

    }
}
