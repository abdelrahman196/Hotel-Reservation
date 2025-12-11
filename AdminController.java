public class AdminController {
    private String employee;
    private String customers;
    private String reservations;
    public AdminController(String employee, String customers) {
        this.employee = employee;
        this.customers = customers;
        this.reservations = reservations;
    }
    public void addCustomer(String name,String phone){}
    public void deleteCustomer(int id){}
    public void updateCustomer(int id, String name,String phone){}
    public void addEmployee(String name,String phone,String password){}
    public void deleteEmployee(String name,String phone,String password){}
    public void updateEmployee(int id){}
    public void addRoom(String type,int price){}
    public void deleteRoom(int id){}
    public void updateRoom(String type,int price){}
    public  void addService(String type,int price,String description){}
    public void deleteService(int id){}
    public void updateService(String type,int price,String description){}
}
