package app;

import java.io.*;
import java.util.*;
public class AdminController {
    private static String employees="src/app/DataBase/Employees";
    private static String customers="src/app/DataBase/Customers";
    private String reservations;
    public AdminController(String employee, String customers,String reservations) {
        this.employees = employee;
        this.customers = customers;
        this.reservations = reservations;
    }



    public void addCustomer(String name,String phone){

    }
    public void deleteCustomer(int id){

    }
    public  static void updateCustomer(int id, String name,String phone,int currentRoomID){
        Customer arr1=new Customer(1,"SOFA","01013292553",0);
        try{
            File data = new File(customers);
            Scanner dataS = new Scanner(data);
            String tmp=dataS.nextLine();
            String[] arr= tmp.split(",");
            for(int i=0;i< arr.length;i++){
                String[] tmp2 = arr[i].split("/");
                if(Integer.parseInt(tmp2[0])==id){
                    arr[i] = String.valueOf(id) + "/" + name + "/" + phone + "/"+String.valueOf(currentRoomID) ;
                    break;
                }

            }
            StringBuilder result = new StringBuilder();
            for(int i=0;i< arr.length;i++){
                result.append(arr[i]).append(",");
            }
            try(PrintWriter writer = new PrintWriter(data)){
                writer.write(result.toString());
                writer.close();
            }


        } catch (FileNotFoundException e) {
            System.out.println("IDC");
        }

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

    public static Employee getEmployee(int id) {
        Employee employee =new Employee(1,"sofa","01013292553","12345678");
        try{
            File file = new File(employees);
            Scanner dataSS = new Scanner(file);
            String read = dataSS.nextLine();
            String[] records = read.split(",");
            for(int i=0;i< records.length;i++){
                String[] read2 = records[i].split("/");
                if(id==Integer.parseInt(read2[0])){
                    employee= new Employee(Integer.parseInt(read2[0]),read2[1],read2[2],read2[3]);
                }

            }

        } catch (FileNotFoundException e) {
            System.out.println("IDC");
        }
        return employee;
    }
    public static ArrayList<Employee> getEmployees(){
        ArrayList<Employee> attributes =new ArrayList<>();
        try{
            File file = new File(employees);
            Scanner dataSS = new Scanner(file);
            String read = dataSS.nextLine();
            String[] records = read.split(",");
            for(int i=0;i< records.length;i++){
                String[] read2 = records[i].split("/");
                attributes.add(new Employee(Integer.parseInt(read2[0]), read2[1],read2[2], read2[3]));
            }

        } catch (FileNotFoundException e) {
            System.out.println("IDC");
        }
        return attributes;
    }
    public static ArrayList<Customer> getCustomers(){
        ArrayList<Customer> arr1=new ArrayList<>();
        try{
            File data = new File(customers);
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
    public static Customer getCustomer(int id){
        Customer arr1=new Customer(1,"SOFA","01013292553",0);
        try{
            File data = new File(customers);
            Scanner dataS = new Scanner(data);
            String tmp=dataS.nextLine();
            String[] arr= tmp.split(",");
            for(int i=0;i< arr.length;i++){
                String[] tmp2 = arr[i].split("/");
                if(Integer.parseInt(tmp2[0])==id){
                    arr1 = new Customer(Integer.parseInt(tmp2[0]), tmp2[1],tmp2[2], Integer.parseInt(tmp2[3]));
                    break;
                }

            }

        } catch (FileNotFoundException e) {
            System.out.println("IDC");
        }
        return arr1;
    }

    public static boolean exists(String name,String password){
        boolean exist=false;
        ArrayList<Employee> EMPLOYEES=AdminController.getEmployees();
        for (int i=0;i<EMPLOYEES.size();i++){
            if(EMPLOYEES.get(i).getName().equals(name) && EMPLOYEES.get(i).getPassword().equals(password) ){
                exist =true;
                break;
            }
        }
        return exist;
    }
    public static ArrayList<Customer> getCustomers(ArrayList<Integer> wanted){
        ArrayList<Customer> arr1=new ArrayList<>();
        try{
            File data = new File(customers);
            Scanner dataS = new Scanner(data);
            String tmp=dataS.nextLine();
            String[] arr= tmp.split(",");
            for(int i=0;i< arr.length;i++){
                String[] tmp2 = arr[i].split("/");
                if (wanted.contains(Integer.parseInt(tmp2[0]))){
                    arr1.add(new Customer(Integer.parseInt(tmp2[0]), tmp2[1],tmp2[2], Integer.parseInt(tmp2[3])));
                }

            }

        } catch (FileNotFoundException e) {
            System.out.println("IDC");
        }
        return arr1;
    }
}
