import app.*;
import java.util.*;
import java.io.*;


public static void main(String[] args) {
    Reservation reservation =RoomManagement.checkOut(1);
    System.out.println(reservation.parsePrint());

}
