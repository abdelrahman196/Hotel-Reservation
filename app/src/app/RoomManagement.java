package app;
import java.io.*;import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class RoomManagement {

    private static String rooms="src/app/DataBase/Rooms";
    private static int currentID;

    public RoomManagement(String roomsString, int newCurrentID){
        rooms = roomsString;
        currentID = newCurrentID;
    }

    public static String getRoomsString() {
        return rooms;
    }

    public static void setRoomsString(String roomsString) {
        rooms = roomsString;
    }

    public int getCurrentID() {
        return currentID;
    }

    public void setCurrentID(int newCurrentID) {
        currentID = newCurrentID;
    }


    public static ArrayList<Room> getRooms() {
        ArrayList<Room> result= new ArrayList<>();
        File file = new File(rooms);
        try {
            Scanner input = new Scanner(file);
            String tmp=input.nextLine();
            input.close();
            String[] rooms = tmp.split(",");
            for(int i=0;i<rooms.length;i++){
                String[] current= rooms[i].split("/");

                ArrayList<Integer> Residents=new ArrayList<>();
                ArrayList<Integer> Services=new ArrayList<>();
                if(current[3].equals("false")){
                    String[] ResidentsArr=current[6].split(":");
                    String[] ServicesArr=current[7].split(":");
                    for (int x=0;x<ResidentsArr.length;x++){
                        Residents.add(Integer.parseInt(ResidentsArr[x]));
                    }
                    for (int x=0;x<ServicesArr.length;x++){
                        Services.add(Integer.parseInt(ServicesArr[x]));
                    }
                    result.add(new Room(Integer.parseInt(current[0]),current[1],Integer.parseInt(current[2]),(current[3].equals("true"))?true:false,Long.parseLong(current[4]),Long.parseLong(current[5]),Residents,Services));
                }
                else{
                    result.add(new Room(Integer.parseInt(current[0]),current[1],Integer.parseInt(current[2]),(current[3].equals("true"))?true:false));
                }

            }

        } catch (FileNotFoundException e) {
            System.out.println("IDC");
        }
        return result;
    }
    public static Room getRoom(int roomid) {
        ArrayList<Room> result= new ArrayList<>();
        File file = new File(rooms);
        try {
            Scanner input = new Scanner(file);
            String tmp=input.nextLine();
            input.close();
            String[] rooms = tmp.split(",");
            for(int i=0;i<rooms.length;i++){
                String[] current= rooms[i].split("/");
                if (current[0].equals(String.valueOf(roomid))){
                    ArrayList<Integer> Residents = new ArrayList<>();
                    ArrayList<Integer> Services = new ArrayList<>();
                    if (current[3].equals("false")) {
                        String[] ResidentsArr = current[6].split(":");
                        String[] ServicesArr = current[7].split(":");
                        for (int x = 0; x < ResidentsArr.length; x++) {
                            Residents.add(Integer.parseInt(ResidentsArr[x]));
                        }
                        for (int x = 0; x < ServicesArr.length; x++) {
                            Services.add(Integer.parseInt(ServicesArr[x]));
                        }
                        result.add(new Room(Integer.parseInt(current[0]), current[1], Integer.parseInt(current[2]), (current[3].equals("true")) ? true : false, Long.parseLong(current[4]), Long.parseLong(current[5]), Residents, Services));
                    } else {
                        result.add(new Room(Integer.parseInt(current[0]), current[1], Integer.parseInt(current[2]), (current[3].equals("true")) ? true : false));
                    }
                    break;
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("IDC");
        }
        return result.get(0);
    }
    public static Room getRoomWithClient(int clientid) {
        ArrayList<Room> result= new ArrayList<>();
        File file = new File(rooms);
        try {
            Scanner input = new Scanner(file);
            String tmp=input.nextLine();
            input.close();
            String[] rooms = tmp.split(",");
            for(int i=0;i<rooms.length;i++){
                String[] current= rooms[i].split("/");



                if(current[3].equals("false")){
                    String[] tmp1 = current[6].split(":");
                    for (int x=0;x<tmp1.length;x++){
                        if(tmp1[x].equals(String.valueOf(clientid))){
                            String[] ResidentsArr = current[6].split(":");
                            String[] ServicesArr = current[7].split(":");
                            ArrayList<Integer> Residents = new ArrayList<>();
                            ArrayList<Integer> Services = new ArrayList<>();
                            for (int y = 0; y < ResidentsArr.length; y++) {
                                Residents.add(Integer.parseInt(ResidentsArr[y]));
                            }
                            for (int y = 0; y < ServicesArr.length; y++) {
                                Services.add(Integer.parseInt(ServicesArr[y]));
                            }
                            result.add(new Room(Integer.parseInt(current[0]),current[1],Integer.parseInt(current[2]),false,Long.parseLong(current[4]),Long.parseLong(current[5]),Residents,Services));
                            break;
                        }
                    }
                }



            }

        } catch (FileNotFoundException e) {
            System.out.println("IDC");
        }
        return result.get(0);

    }

    public static void setRoomDate(int id,Date date) {

        File file = new File(rooms);
        try {
            Scanner input = new Scanner(file);
            String tmp=input.nextLine();
            input.close();
            String[] rooms = tmp.split(",");
            for(int i=0;i<rooms.length;i++){
                String[] current= rooms[i].split("/");

                ArrayList<Integer> Residents=new ArrayList<>();
                ArrayList<Integer> Services=new ArrayList<>();
                if(Integer.parseInt(current[0]) == id){
                    String result;
                    String[] ResidentsArr=current[6].split(":");
                    String[] ServicesArr=current[7].split(":");
                    for (int x=0;x<ResidentsArr.length;x++){
                        Residents.add(Integer.parseInt(ResidentsArr[x]));
                    }
                    for (int x=0;x<ServicesArr.length;x++){
                        Services.add(Integer.parseInt(ServicesArr[x]));
                    }
                    result =(new Room(Integer.parseInt(current[0]),current[1],Integer.parseInt(current[2]),(current[3].equals("true"))?true:false,Long.parseLong(current[4]), date.getTime(), Residents,Services)).parseStore();
                    rooms[i]=result;
                }


            }
            StringBuilder result=new StringBuilder();
            for (int i=0;i<rooms.length;i++){
                result.append(rooms[i]).append(",");
            }
            try (PrintWriter writer = new PrintWriter(file)){
                writer.write(result.toString());
            }

        } catch (FileNotFoundException e) {
            System.out.println("IDC");
        }
    }

    public static ArrayList<Room> busyRooms() {
        ArrayList<Room> result= new ArrayList<>();
        File file = new File(rooms);
        try {
            Scanner input = new Scanner(file);
            String tmp=input.nextLine();
            input.close();
            String[] rooms = tmp.split(",");
            for(int i=0;i<rooms.length;i++){
                String[] current= rooms[i].split("/");

                ArrayList<Integer> Residents=new ArrayList<>();
                ArrayList<Integer> Services=new ArrayList<>();
                if(current[3].equals("false")){
                    String[] ResidentsArr=current[6].split(":");
                    String[] ServicesArr=current[7].split(":");
                    for (int x=0;x<ResidentsArr.length;x++){
                        Residents.add(Integer.parseInt(ResidentsArr[x]));
                    }
                    for (int x=0;x<ServicesArr.length;x++){
                        Services.add(Integer.parseInt(ServicesArr[x]));
                    }
                    result.add(new Room(Integer.parseInt(current[0]),current[1],Integer.parseInt(current[2]),(current[3].equals("true"))?true:false,Long.parseLong(current[4]),Long.parseLong(current[5]),Residents,Services));
                }


            }

        } catch (FileNotFoundException e) {
            System.out.println("IDC");
        }
        return result;
    }

    public static ArrayList<Room> freeRooms() {
        ArrayList<Room> result= new ArrayList<>();
        File file = new File(rooms);
        try {
            Scanner input = new Scanner(file);
            String tmp=input.nextLine();
            input.close();
            String[] rooms = tmp.split(",");
            for(int i=0;i<rooms.length;i++){
                String[] current= rooms[i].split("/");

                ArrayList<Integer> Residents=new ArrayList<>();
                ArrayList<Integer> Services=new ArrayList<>();
                if(current[3].equals("true")){
                    result.add(new Room(Integer.parseInt(current[0]),current[1],Integer.parseInt(current[2]),(current[3].equals("true"))?true:false));
                }


            }

        } catch (FileNotFoundException e) {
            System.out.println("IDC");
        }
        return result;
    }

    public static ArrayList<Room> RoomWithType(String type) {
        ArrayList<Room> result= new ArrayList<>();
        File file = new File(rooms);
        try {
            Scanner input = new Scanner(file);
            String tmp=input.nextLine();
            input.close();
            String[] rooms = tmp.split(",");
            for(int i=0;i<rooms.length;i++){
                String[] current= rooms[i].split("/");

                if(current[1].equals(type)){
                    ArrayList<Integer> Residents = new ArrayList<>();
                    ArrayList<Integer> Services = new ArrayList<>();
                    if (current[3].equals("false")) {
                        String[] ResidentsArr = current[6].split(":");
                        String[] ServicesArr = current[7].split(":");
                        for (int x = 0; x < ResidentsArr.length; x++) {
                            Residents.add(Integer.parseInt(ResidentsArr[x]));
                        }
                        for (int x = 0; x < ServicesArr.length; x++) {
                            Services.add(Integer.parseInt(ServicesArr[x]));
                        }
                        result.add(new Room(Integer.parseInt(current[0]), current[1], Integer.parseInt(current[2]), (current[3].equals("true")) ? true : false, Long.parseLong(current[4]), Long.parseLong(current[5]), Residents, Services));
                    } else {
                        result.add(new Room(Integer.parseInt(current[0]), current[1], Integer.parseInt(current[2]), (current[3].equals("true")) ? true : false));
                    }
                }

            }

        } catch (FileNotFoundException e) {
            System.out.println("IDC");
        }
        return result;
    }

    public static ArrayList<Room> RoomWithService(int serviceID) {
        ArrayList<Room> result= new ArrayList<>();
        File file = new File(rooms);
        try {
            Scanner input = new Scanner(file);
            String tmp=input.nextLine();
            input.close();
            String[] rooms = tmp.split(",");
            for(int i=0;i<rooms.length;i++){
                String[] current= rooms[i].split("/");


                    ArrayList<Integer> Residents = new ArrayList<>();
                    ArrayList<Integer> Services = new ArrayList<>();
                    if (current[3].equals("false")) {
                        String[] ResidentsArr = current[6].split(":");
                        String[] ServicesArr = current[7].split(":");
                        boolean ok = false;
                        for (int x=0;x<ServicesArr.length;x++){
                            if(Integer.parseInt(ServicesArr[x])==serviceID){
                                ok=true;
                                break;
                            }
                        }

                        if(!ok){
                            continue;
                        }
                        for (int x = 0; x < ResidentsArr.length; x++) {
                            Residents.add(Integer.parseInt(ResidentsArr[x]));
                        }
                        for (int x = 0; x < ServicesArr.length; x++) {
                            Services.add(Integer.parseInt(ServicesArr[x]));
                        }
                        result.add(new Room(Integer.parseInt(current[0]), current[1], Integer.parseInt(current[2]), (current[3].equals("true")) ? true : false, Long.parseLong(current[4]), Long.parseLong(current[5]), Residents, Services));
                    }


            }

        } catch (FileNotFoundException e) {
            System.out.println("IDC");
        }
        return result;
    }

    public static void AssignRoom(int clientID, int roomID) {
        File file = new File(rooms);
        try {
            Scanner input = new Scanner(file);
            String tmp=input.nextLine();
            input.close();
            String[] rooms = tmp.split(",");
            for(int i=0;i<rooms.length;i++){
                String[] current= rooms[i].split("/");

                ArrayList<Integer> Residents=new ArrayList<>();
                ArrayList<Integer> Services=new ArrayList<>();
                Residents.add(clientID);
                Services.add(1);
                if(Integer.parseInt(current[0]) == roomID){
                    String result;
                    if(current[3].equals("true")){
                        result =(new Room(Integer.parseInt(current[0]),current[1],Integer.parseInt(current[2]),false,Instant.now().toEpochMilli(),Instant.now().toEpochMilli()+100000, Residents,Services)).parseStore();
                    }else {
                        String[] ResidentsArr=current[6].split(":");
                        String[] ServicesArr=current[7].split(":");
                        for (int x=0;x<ResidentsArr.length;x++){
                            Residents.add(Integer.parseInt(ResidentsArr[x]));
                        }

                        for (int x=0;x<ServicesArr.length;x++){
                            Services.add(Integer.parseInt(ServicesArr[x]));
                        }
                        result =(new Room(Integer.parseInt(current[0]),current[1],Integer.parseInt(current[2]),false,Long.parseLong(current[4]),Long.parseLong(current[5]), Residents,Services)).parseStore();

                    }
                    rooms[i]=result;



                }


            }
            StringBuilder result=new StringBuilder();
            for (int i=0;i<rooms.length;i++){
                result.append(rooms[i]).append(",");
            }
            try (PrintWriter writer = new PrintWriter(file)){
                writer.write(result.toString());
            }

        } catch (FileNotFoundException e) {
            System.out.println("IDC");
        }
        Customer customer=AdminController.getCustomer(clientID);
        AdminController.updateCustomer(clientID,customer.getName(),customer.getPhone(),roomID);
    }

    public static Reservation checkOut(int roomID) {
        Room  room = RoomManagement.getRoom(roomID);
        return new Reservation(roomID,room.getEntryDate(),room.getExpirationDate(),room.getPrice(),room.getResidents(),room.getServices());

    }

    public static String getDetailedInvoice(int clientID) {
        int result=0;
        File file = new File(rooms);
        try {
            Scanner input = new Scanner(file);
            String tmp=input.nextLine();
            input.close();
            String[] rooms = tmp.split(",");
            for(int i=0;i<rooms.length;i++){
                String[] current= rooms[i].split("/");



                if(current[3].equals("false")){
                    String[] tmp1 = current[6].split(":");
                    for (int x=0;x<tmp1.length;x++){
                        if(tmp1[x].equals(String.valueOf(clientID))){
                            result=Integer.parseInt(current[0]);
                            break;
                        }
                    }
                }



            }

        } catch (FileNotFoundException e) {
            System.out.println("IDC");
        }
        return RoomManagement.getRoom(result).parsePrint();
    }
    public static void AssignRoomWithService(int serviceID, int roomID) {
        File file = new File(rooms);
        try {
            Scanner input = new Scanner(file);
            String tmp=input.nextLine();
            input.close();
            String[] rooms = tmp.split(",");
            for(int i=0;i<rooms.length;i++){
                String[] current= rooms[i].split("/");

                ArrayList<Integer> Residents=new ArrayList<>();
                ArrayList<Integer> Services=new ArrayList<>();
                Residents.add(1);
                Services.add(serviceID);
                if(Integer.parseInt(current[0]) == roomID){
                    String result;
                    if(current[3].equals("true")){
                        result =(new Room(Integer.parseInt(current[0]),current[1],Integer.parseInt(current[2]),false,Instant.now().toEpochMilli(),Instant.now().toEpochMilli()+100000, Residents,Services)).parseStore();
                    }else {
                        String[] ResidentsArr=current[6].split(":");
                        String[] ServicesArr=current[7].split(":");
                        for (int x=0;x<ResidentsArr.length;x++){
                            Residents.add(Integer.parseInt(ResidentsArr[x]));
                        }

                        for (int x=0;x<ServicesArr.length;x++){
                            Services.add(Integer.parseInt(ServicesArr[x]));
                        }
                        result =(new Room(Integer.parseInt(current[0]),current[1],Integer.parseInt(current[2]),false,Long.parseLong(current[4]),Long.parseLong(current[5]), Residents,Services)).parseStore();

                    }
                    rooms[i]=result;



                }


            }
            StringBuilder result=new StringBuilder();
            for (int i=0;i<rooms.length;i++){
                result.append(rooms[i]).append(",");
            }
            try (PrintWriter writer = new PrintWriter(file)){
                writer.write(result.toString());
            }

        } catch (FileNotFoundException e) {
            System.out.println("IDC");
        }

    }

}
