package app;

import java.io.*;
import java.util.*;

public class AdminController {
    // Use consistent file paths
    private static final String EMPLOYEES_FILE = "src/app/DataBase/Employees";
    private static final String CUSTOMERS_FILE = "src/app/DataBase/Customers";
    private static final String ROOMS_FILE = "app/src/app/DataBase/Rooms";
    private static final String SERVICES_FILE = "app/src/app/DataBase/Services";

    // Save methods
    public static void saveRooms(List<Room> rooms) {
        saveToFile(ROOMS_FILE, rooms, r ->
                r.getId() + "/" + r.getType() + "/" + r.getPrice() + "/" + (r.isAvailable() ? "1" : "0"));
    }

    public static void saveCustomers(List<Customer> customers) {
        saveToFile(CUSTOMERS_FILE, customers, c ->
                c.getId() + "/" + c.getName() + "/" + c.getPhone());
    }

    public static void saveServices(List<Service> services) {
        saveToFile(SERVICES_FILE, services, s ->
                s.getId() + "/" + s.getName() + "/" + s.getPrice() + "/" + s.getUsageCount());
    }

    public static void saveEmployees(List<Employee> employees) {
        saveToFile(EMPLOYEES_FILE, employees, e ->
                e.getId() + "/" + e.getName() + "/" + e.getPhone() + "/" + e.getJobTitle());
    }

    // Generic save method
    private static <T> void saveToFile(String filename, List<T> items, Serializer<T> serializer) {
        // Create directory if it doesn't exist
        File file = new File(filename);
        file.getParentFile().mkdirs();

        try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
            for (T item : items) {
                pw.println(serializer.serialize(item));
            }
            System.out.println("✓ Saved " + items.size() + " items to " + file.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("✗ Error saving to " + filename + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    interface Serializer<T> {
        String serialize(T item);
    }

    // Get all employees
    public static ArrayList<Employee> getEmployees() {
        ArrayList<Employee> employees = new ArrayList<>();
        try {
            File file = new File(EMPLOYEES_FILE);
            if (!file.exists()) {
                System.out.println("⚠ Employee file doesn't exist: " + EMPLOYEES_FILE);
                return employees;
            }

            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    String[] parts = line.split("/");
                    if (parts.length >= 4) {
                        int id = Integer.parseInt(parts[0]);
                        String name = parts[1];
                        String phone = parts[2];
                        String jobTitle = parts[3];
                        employees.add(new Employee(id, name, phone, jobTitle));
                    } else if (parts.length == 3) {
                        // Handle old format without jobTitle
                        int id = Integer.parseInt(parts[0]);
                        String name = parts[1];
                        String phone = parts[2];
                        employees.add(new Employee(id, name, phone, "Staff")); // Default job title
                    }
                }
            }
            scanner.close();

            System.out.println("✓ Loaded " + employees.size() + " employees from " + file.getAbsolutePath());

        } catch (FileNotFoundException e) {
            System.out.println("⚠ Employee file not found: " + EMPLOYEES_FILE);
        } catch (Exception e) {
            System.err.println("✗ Error reading employees: " + e.getMessage());
            e.printStackTrace();
        }

        return employees;

    }

    // Get all customers - FIXED VERSION
    public static ArrayList<Customer> getCustomers() {
        ArrayList<Customer> customers = new ArrayList<>();
        try {
            File file = new File(CUSTOMERS_FILE);  // Use the CONSTANT, not local variable
            System.out.println("Looking for customer file at: " + file.getAbsolutePath());

            if (!file.exists()) {
                System.out.println("⚠ Customer file doesn't exist yet. Creating empty file.");
                file.getParentFile().mkdirs();  // Create directories
                file.createNewFile();
                return customers;
            }

            if (file.length() == 0) {
                System.out.println("ℹ Customer file is empty");
                return customers;
            }

            Scanner scanner = new Scanner(file);
            int count = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    String[] parts = line.split("/");
                    if (parts.length >= 3) {
                        int id = Integer.parseInt(parts[0]);
                        String name = parts[1];
                        String phone = parts[2];
                        customers.add(new Customer(id, name, phone));
                        count++;
                    } else {
                        System.err.println("⚠ Invalid customer data: " + line);
                    }
                }
            }
            scanner.close();

            System.out.println("✓ Loaded " + count + " customers from " + file.getAbsolutePath());

        } catch (FileNotFoundException e) {
            System.out.println("⚠ Customer file not found: " + CUSTOMERS_FILE);
        } catch (Exception e) {
            System.err.println("✗ Error reading customers: " + e.getMessage());
            e.printStackTrace();
        }
        return customers;
    }

    // Get single employee by ID
    public static Employee getEmployee(int id) {
        ArrayList<Employee> employees = getEmployees();
        for (Employee emp : employees) {
            if (emp.getId() == id) {
                return emp;
            }
        }
        return new Employee(1, "Default", "0000000000", "Staff");
    }

    // Get single customer by ID
    public static Customer getCustomer(int id) {
        ArrayList<Customer> customers = getCustomers();
        for (Customer cust : customers) {
            if (cust.getId() == id) {
                return cust;
            }
        }
        return new Customer(1, "Default", "0000000000");
    }


    // Debug method to check file contents
    public static void debugFileContents(String filename) {
        System.out.println("\n=== Debug: " + filename + " ===");
        try {
            File file = new File(filename);
            System.out.println("File exists: " + file.exists());
            System.out.println("Path: " + file.getAbsolutePath());

            if (file.exists()) {
                Scanner scanner = new Scanner(file);
                int lineNum = 1;
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    System.out.println(lineNum + ": " + line);
                    lineNum++;
                }
                scanner.close();
            }
        } catch (Exception e) {
            System.err.println("Debug error: " + e.getMessage());
        }
        System.out.println("=== End Debug ===\n");
    }

    // NEW: Test method to verify everything works
    public static void testFileOperations() {
        System.out.println("\n=== TESTING FILE OPERATIONS ===");

        // Create test customers
        List<Customer> testCustomers = new ArrayList<>();
        testCustomers.add(new Customer(1, "Test Customer 1", "111-111-1111"));
        testCustomers.add(new Customer(2, "Test Customer 2", "222-222-2222"));

        // Save test data
        System.out.println("Saving " + testCustomers.size() + " test customers...");
        saveCustomers(testCustomers);

        // Read back
        System.out.println("Reading customers back...");
        List<Customer> loaded = getCustomers();

        // Compare
        if (testCustomers.size() == loaded.size()) {
            System.out.println("✓ SUCCESS: Data saved and loaded correctly!");
        } else {
            System.out.println("✗ FAILURE: Expected " + testCustomers.size() +
                    " but got " + loaded.size());
        }

        // Show debug info
        debugFileContents(CUSTOMERS_FILE);
    }
}