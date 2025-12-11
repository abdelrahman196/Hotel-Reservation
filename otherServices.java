
import java.util.ArrayList;

public class OtherService {

    
    private String services;
    private int currentID;

    
    public OtherService(String services) {
        this.services=services;
        currentID=1;
    }

    

    public void addService(String name, String description, int price, boolean available) {

    }

    public void updateService(int id, String name, String description, int price, boolean available) {
    }

    public void deleteService(int id) {
    }

    public Service getService(int serviceID) {

    }
    public Arraylist<Service> getServices(Arraylist<Integer> services) {

    }


    public ArrayList<Service> getAvailableServices() {
        return null;
    }

    public void addServiceToClient(int clientID, int serviceID) {
    }

    public String getStaticReportServiceID(int serviceID) {
        return null;
    }
}
