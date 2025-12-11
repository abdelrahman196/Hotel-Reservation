

import java.util.Date;
import java.util.List;

public class RoomManagement {

    private String roomsString;
    private int currentID;

    public String getRoomsString() {
        return roomsString;
    }

    public void setRoomsString(String roomsString) {
        this.roomsString = roomsString;
    }

    public int getCurrentID() {
        return currentID;
    }

    public void setCurrentID(int currentID) {
        this.currentID = currentID;
    }


    public List<Room> getRooms() {
        return null;
    }

    public void setRoomDate(Date date) {
    }

    public List<Room> busyRooms() {
        return null;
    }

    public List<Room> freeRooms() {
        return null;
    }

    public List<Room> RoomWithType(String type) {
        return null;
    }

    public List<Room> RoomWithService(int serviceID) {
        return null;
    }

    public void AssignRoom(int clientID, int roomID) {
    }

    public Reservation checkOut() {
        return null;
    }

    public int getDetailedInvoice(int clientID) {
        return 0;
    }
}
