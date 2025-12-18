package app;
import java.io.*;import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class RoomManagement {
    // Fix the file path - make sure it's consistent
    private static final String ROOMS_FILE = "app/src/app/DataBase/Rooms";

    public static ArrayList<Room> getRooms() {
        ArrayList<Room> rooms = new ArrayList<>();
        File file = new File(ROOMS_FILE);

        System.out.println("Looking for rooms at: " + file.getAbsolutePath());

        if (!file.exists()) {
            System.out.println("Room file doesn't exist, creating empty file");
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
                return rooms;
            } catch (IOException e) {
                System.err.println("Error creating room file: " + e.getMessage());
                return rooms;
            }
        }

        try (Scanner input = new Scanner(file)) {
            // Read ALL lines, not just first line
            while (input.hasNextLine()) {
                String line = input.nextLine().trim();
                if (!line.isEmpty()) {
                    // Debug: Show what we're parsing
                    System.out.println("Parsing room line: " + line);

                    String[] parts = line.split("/");

                    // SIMPLIFIED VERSION - Use the simpler Room constructor
                    if (parts.length >= 4) {
                        try {
                            int id = Integer.parseInt(parts[0]);
                            String type = parts[1];
                            double price = Double.parseDouble(parts[2]);
                            boolean available = parts[3].equalsIgnoreCase("true") || parts[3].equals("1");

                            // Use simpler constructor that only needs basic info
                            Room room = new Room(id, type, (int) price, available);
                            rooms.add(room);

                        } catch (NumberFormatException e) {
                            System.err.println("Error parsing room line: " + line);
                            e.printStackTrace();
                        }
                    }
                }
            }
            System.out.println("Loaded " + rooms.size() + " rooms");

        } catch (FileNotFoundException e) {
            System.err.println("Room file not found: " + ROOMS_FILE);
        }
        return rooms;
    }

    // Save method
    public static void saveRooms(List<Room> rooms) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ROOMS_FILE))) {
            for (Room r : rooms) {
                // Simplified format: id/type/price/available
                writer.println(r.getId() + "/" +
                        r.getType() + "/" +
                        r.getPrice() + "/" +
                        (r.isAvailable() ? "1" : "0"));
            }
            System.out.println("Saved " + rooms.size() + " rooms to " + ROOMS_FILE);
        } catch (IOException e) {
            System.err.println("Error saving rooms: " + e.getMessage());
        }
    }
}