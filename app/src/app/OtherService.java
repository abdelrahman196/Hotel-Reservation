package app;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class OtherService {


    private static String service="src/app/DataBase/Services";
    private static int currentID=5;


    public OtherService(String services) {

    }



    public static void addService(String name, String description, int price, boolean available) {


        File service_file = new File(OtherService.service);
        try {
            Scanner input = new Scanner(service_file);
            String tmp = input.nextLine();
            input.close();
            try(PrintWriter writer = new PrintWriter(new FileWriter(service_file))){

                String tmp1= String.valueOf(currentID++) +  "/" + name + "/" +description +"/" + String.valueOf(price) +  "/" + (available?"true":"false")+","  ;

                writer.print(tmp+tmp1);
                writer.close();

            }

            catch (IOException e){
                System.out.println("nothing");
            }
        } catch (FileNotFoundException e) {
            System.out.println("bla ");
        }



    }

    public static void updateService(int id, String name, String description, int price, boolean available) {
        File service_file = new File(OtherService.service);
        try {
            Scanner input = new Scanner(service_file);
            String tmp=input.nextLine();
            input.close();

            try(PrintWriter writer = new PrintWriter(new FileWriter(service_file))){

                String tmp1=  String.valueOf(id) +"/"+ name + "/" +description +"/" + String.valueOf(price) + "/" + (available?"true":"false")+",";

                String[] Services = tmp.split(",");

                for (int i=0 ;i<Services.length;i++){
                    System.out.println(Services[i]);
                    String[] current = Services[i].split("/");

                    if(Integer.parseInt(current[0])==id){
                        Services[i]=tmp1;
                    }
                }
                StringBuilder result= new StringBuilder();
                for (int i=0 ;i<Services.length;i++){
                    System.out.println(Services[i]);
                    result.append(Services[i]).append(",");
                }
                writer.write(result.toString());
                writer.close();

            }
            catch (IOException e){
                System.out.println("nothing");
            }
        }catch (FileNotFoundException e){
            System.out.println("f");
        }


    }

    public static void deleteService(int id) {
        File service_file = new File(OtherService.service);
        try {
            Scanner input = new Scanner(service_file);
            String tmp=input.nextLine();
            input.close();

            try(PrintWriter writer = new PrintWriter(new FileWriter(service_file))){
                StringBuilder result= new StringBuilder();
                String[] Services = tmp.split(",");
                for (int i=0 ;i<Services.length;i++){
                    String[] current = Services[i].split("/");

                    if(!(Integer.parseInt(current[0])==id)){
                        result.append(Services[i]).append(',');
                    }
                    else {
                        currentID--;
                    }


                }


                writer.write(result.toString());
                writer.close();

            }
            catch (IOException e){
                System.out.println("nothing");
            }
        }catch (FileNotFoundException e){
            System.out.println("f");
        }

    }

    public static Service getService(int id) {
        Service result1 = new Service(1,"sofa","rich",1000,false);
        File service_file = new File(OtherService.service);
        try {
            Scanner input = new Scanner(service_file);
            String tmp=input.nextLine();
            input.close();

            try(PrintWriter writer = new PrintWriter(new FileWriter(service_file))){
                StringBuilder result= new StringBuilder();
                String[] Services = tmp.split(",");
                for (int i=0 ;i<Services.length;i++){
                    String[] current = Services[i].split("/");

                    if((Integer.parseInt(current[0])==id)){
                        result1 = new Service(Integer.parseInt(current[0]),current[1],current[2],Integer.parseInt(current[3]),(current[4]=="true")?true:false);
                    }


                }

                for (int i=0 ;i<Services.length;i++){
                    System.out.println(Services[i]);
                    result.append(Services[i]).append(",");
                }
                writer.write(result.toString());
                writer.close();

            }
            catch (IOException e){
                System.out.println("nothing");
            }
        }catch (FileNotFoundException e){
            System.out.println("f");
        }
        return  result1;
    }
    public static ArrayList<Service> getServices(ArrayList<Integer> services) {
        ArrayList<Service> result1 = new ArrayList<>();
        File service_file = new File(OtherService.service);
        try {
            Scanner input = new Scanner(service_file);
            String tmp=input.nextLine();
            input.close();

            try(PrintWriter writer = new PrintWriter(new FileWriter(service_file))){
                StringBuilder result= new StringBuilder();
                String[] Services = tmp.split(",");
                for (int i=0 ;i<Services.length;i++){
                    String[] current = Services[i].split("/");

                    if(services.contains((Integer.parseInt(current[0]))) ){
                        result1.add(new Service(Integer.parseInt(current[0]),current[1],current[2],Integer.parseInt(current[3]),(current[4]=="true")?true:false));
                    }


                }

                for (int i=0 ;i<Services.length;i++){
                    System.out.println(Services[i]);
                    result.append(Services[i]).append(",");
                }
                writer.write(result.toString());
                writer.close();

            }
            catch (IOException e){
                System.out.println("nothing");
            }
        }catch (FileNotFoundException e){
            System.out.println("f");
        }
        return  result1;
    }


    public static ArrayList<Service> getAvailableServices() {
        ArrayList<Service> result1 = new ArrayList<>();
        File service_file = new File(OtherService.service);
        try {
            Scanner input = new Scanner(service_file);
            String tmp=input.nextLine();
            input.close();

            try(PrintWriter writer = new PrintWriter(new FileWriter(service_file))){
                StringBuilder result= new StringBuilder();
                String[] Services = tmp.split(",");
                for (int i=0 ;i<Services.length;i++){
                    String[] current = Services[i].split("/");

                    if(current[4].equals("true") ){
                        result1.add(new Service(Integer.parseInt(current[0]),current[1],current[2],Integer.parseInt(current[3]),(current[4]=="true")?true:false));
                    }


                }

                for (int i=0 ;i<Services.length;i++){
                    System.out.println(Services[i]);
                    result.append(Services[i]).append(",");
                }
                writer.write(result.toString());
                writer.close();

            }
            catch (IOException e){
                System.out.println("nothing");
            }
        }catch (FileNotFoundException e){
            System.out.println("f");
        }
        return  result1;
    }

    public void addServiceToClient(int clientID, int serviceID) {

    }

    public String getStaticReportServiceID(int serviceID) {
        Service service1 = getService(serviceID);
        return "Name:" + service1.getName() + "\nDescription:" + service1.getDescription() + "\nPrice:" + service1.getPrice() + "$/day\n" + (service1.isAvailable()?"service is available":"service is not available");
    }
}
