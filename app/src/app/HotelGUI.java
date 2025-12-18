package app;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.*;
import java.io.*;

class GUIStarter implements Runnable {
    @Override
    public void run() {
        HotelGUI gui = new HotelGUI();
        gui.setVisible(true);
    }
}

public class HotelGUI extends JFrame implements ActionListener{
    // *** Data Lists ***
    private List<Customer> customers = new ArrayList<>();
    private List<Room> rooms = new ArrayList<>();
    private List<Service> services = new ArrayList<>();
    private List<Reservation> reservations = new ArrayList<>();
    private List<Employee> employees = new ArrayList<>();

    // *** Global Components ***
    private DefaultTableModel custModel;    private JTable custTable;
    private DefaultTableModel roomModel;    private JTable roomTable;
    private DefaultTableModel servModel;    private JTable servTable;
    private DefaultTableModel empModel;     private JTable empTable;

    private JCheckBox filterBox;                // Checkbox for "Available Only"
    private JComboBox<String> filterTypeBox;    // Dropdown for Room Type filtering
    private JComboBox<Customer> custBox;        // Changed to Customer type
    private JComboBox<Room> roomBox;            // Changed to Room type
    private JComboBox<Reservation> activeResBox; // Changed to Reservation type
    private JTextArea billArea;

    // *** Buttons ***
    private JButton addCustBtn, updateCustBtn, deleteCustBtn;
    private JButton addRoomBtn, updateRoomBtn, deleteRoomBtn, filterBtn;
    private JButton addServiceBtn, updateServiceBtn, deleteServiceBtn, reportServiceBtn;
    private JButton refreshListsBtn, bookBtn, addResServiceBtn, checkoutBtn, viewCheckoutBtn;
    private JButton addEmpBtn, updateEmpBtn, deleteEmpBtn;

    public HotelGUI() {
        setTitle("Hotel Reservation System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        // Debug first
        System.out.println("\n=== DEBUG START ===");
        System.out.println("Working Directory: " + System.getProperty("user.dir"));

        // Load data
        loadInitialData();

        systemComponents();
    }

    private void loadInitialData() {
        System.out.println("\n=== LOADING DATA ===");

        System.out.println("\n1. Loading customers...");
        customers = AdminController.getCustomers();
        System.out.println("✓ Customers loaded: " + customers.size());

        System.out.println("\n2. Loading rooms...");
        // Try RoomManagement first, fallback to direct loading
        try {
            rooms = RoomManagement.getRooms();
            if (rooms.isEmpty()) {
                System.out.println("RoomManagement returned empty, trying direct load...");
                rooms = loadRoomsDirectly();
            }
        } catch (Exception e) {
            System.err.println("Error with RoomManagement: " + e.getMessage());
            rooms = loadRoomsDirectly();
        }
        System.out.println("✓ Rooms loaded: " + rooms.size());

        System.out.println("\n3. Loading services...");
        // Try OtherService first, fallback to direct loading
        try {
            services = OtherService.getAvailableServices();
            if (services.isEmpty()) {
                System.out.println("OtherService returned empty, trying direct load...");
                services = loadServicesDirectly();
            }
        } catch (Exception e) {
            System.err.println("Error with OtherService: " + e.getMessage());
            services = loadServicesDirectly();
        }
        System.out.println("✓ Services loaded: " + services.size());

        System.out.println("\n4. Loading employees...");
        employees = AdminController.getEmployees();
        System.out.println("✓ Employees loaded: " + employees.size());

        reservations = new ArrayList<>();

        System.out.println("\n=== DATA SUMMARY ===");
        System.out.println("Total customers: " + customers.size());
        System.out.println("Total rooms: " + rooms.size());
        System.out.println("Total services: " + services.size());
        System.out.println("Total employees: " + employees.size());
    }

    private List<Service> loadServicesDirectly() {
        List<Service> serviceList = new ArrayList<>();
        try {
            // Try multiple possible file locations
            String[] possiblePaths = {
                    "app/src/app/DataBase/Services",
                    "src/app/DataBase/Services",
                    "DataBase/Services",
                    "Services"
            };

            File file = null;
            for (String path : possiblePaths) {
                file = new File(path);
                if (file.exists()) {
                    System.out.println("Found services at: " + file.getAbsolutePath());
                    break;
                }
            }

            if (file != null && file.exists()) {
                if (file.length() == 0) {
                    System.out.println("Service file is empty");
                    return serviceList;
                }

                Scanner scanner = new Scanner(file);
                int lineCount = 0;
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine().trim();
                    lineCount++;

                    if (!line.isEmpty()) {
                        System.out.println("Reading service line " + lineCount + ": " + line);

                        String[] parts = line.split("/");
                        System.out.println("  Parts found: " + parts.length);

                        if (parts.length >= 3) {
                            try {
                                int id = Integer.parseInt(parts[0]);
                                String name = parts[1];
                                double price = Double.parseDouble(parts[2]);

                                Service service = new Service(id, name, price);

                                // Try to get usage count if available
                                if (parts.length >= 4) {
                                    try {
                                        int usageCount = Integer.parseInt(parts[3]);
                                        // Check if Service class has setUsageCount method
                                        try {
                                            service.getClass().getMethod("setUsageCount", int.class);
                                            service.setUsageCount(usageCount);
                                        } catch (NoSuchMethodException e) {
                                            System.out.println("Service class doesn't have setUsageCount method");
                                        }
                                    } catch (NumberFormatException e) {
                                        // usageCount might not be a number, check if it's availability flag
                                        if (parts[3].equalsIgnoreCase("true") || parts[3].equalsIgnoreCase("false")) {
                                            // It's an availability flag, ignore or handle as needed
                                            System.out.println("  Availability flag: " + parts[3]);
                                        }
                                    }
                                }

                                serviceList.add(service);
                                System.out.println("  ✓ Added service: " + name + " ($" + price + ")");

                            } catch (NumberFormatException e) {
                                System.err.println("  ✗ Error parsing numbers in line: " + line);
                                System.err.println("  Error: " + e.getMessage());
                            }
                        } else {
                            System.err.println("  ✗ Line doesn't have enough parts: " + line);
                            System.err.println("  Expected at least 3 parts (id/name/price)");
                        }
                    }
                }
                scanner.close();
                System.out.println("✓ Directly loaded " + serviceList.size() + " services from " + file.getAbsolutePath());

                // If still empty, create some default services
                if (serviceList.isEmpty()) {
                    System.out.println("No services found, creating default services...");
                    serviceList.add(new Service(1, "Room Service", 25.0));
                    serviceList.add(new Service(2, "Laundry", 15.0));
                    serviceList.add(new Service(3, "Spa", 50.0));
                    serviceList.add(new Service(4, "Breakfast", 12.0));

                    // Save defaults to file
                    saveServicesDirectly(serviceList, file.getAbsolutePath());
                }

            } else {
                System.out.println("No service file found at any location, creating default services...");

                // Create default services
                serviceList.add(new Service(1, "Room Service", 25.0));
                serviceList.add(new Service(2, "Laundry", 15.0));
                serviceList.add(new Service(3, "Spa", 50.0));
                serviceList.add(new Service(4, "Breakfast", 12.0));

                // Create file and save defaults
                File defaultFile = new File("app/src/app/DataBase/Services");
                defaultFile.getParentFile().mkdirs();
                saveServicesDirectly(serviceList, defaultFile.getAbsolutePath());
            }

        } catch (Exception e) {
            System.err.println("Error in loadServicesDirectly: " + e.getMessage());
            e.printStackTrace();

            // Return at least some default services
            serviceList.add(new Service(1, "Room Service", 25.0));
            serviceList.add(new Service(2, "Laundry", 15.0));
        }

        return serviceList;
    }

    private void saveServicesDirectly(List<Service> services, String filePath) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (Service s : services) {
                // Format: id/name/price/usageCount
                writer.println(s.getId() + "/" +
                        s.getName() + "/" +
                        s.getPrice() + "/" +
                        "0"); // Default usage count
            }
            System.out.println("✓ Saved " + services.size() + " services to " + filePath);
        } catch (IOException e) {
            System.err.println("Error saving services: " + e.getMessage());
        }
    }

    // Also add this helper method to check Service class methods
    private void checkServiceClassMethods() {
        System.out.println("\n=== Checking Service Class ===");
        try {
            Service testService = new Service(999, "Test", 99.99);

            // Check what methods are available
            System.out.println("Service class: " + testService.getClass().getName());

            // Try common methods
            System.out.println("ID: " + testService.getId());
            System.out.println("Name: " + testService.getName());
            System.out.println("Price: " + testService.getPrice());

            // Try to get usage count
            try {
                java.lang.reflect.Method method = testService.getClass().getMethod("getUsageCount");
                Object result = method.invoke(testService);
                System.out.println("Usage count method exists: " + result);
            } catch (Exception e) {
                System.out.println("No getUsageCount method found");
            }

            // Try to set usage count
            try {
                java.lang.reflect.Method method = testService.getClass().getMethod("setUsageCount", int.class);
                System.out.println("setUsageCount method exists");
            } catch (Exception e) {
                System.out.println("No setUsageCount method found");
            }

        } catch (Exception e) {
            System.err.println("Error checking Service class: " + e.getMessage());
        }
    }

    // Add these helper methods if not already there
    private List<Room> loadRoomsDirectly() {
        List<Room> roomList = new ArrayList<>();
        try {
            // Try multiple possible paths
            String[] possiblePaths = {
                    "app/src/app/DataBase/Rooms",
                    "src/app/DataBase/Rooms",
                    "DataBase/Rooms"
            };

            File file = null;
            for (String path : possiblePaths) {
                file = new File(path);
                if (file.exists()) {
                    System.out.println("Found rooms at: " + file.getAbsolutePath());
                    break;
                }
            }

            if (file != null && file.exists()) {
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine().trim();
                    if (!line.isEmpty()) {
                        String[] parts = line.split("/");
                        if (parts.length >= 4) {
                            int id = Integer.parseInt(parts[0]);
                            String type = parts[1];
                            double price = Double.parseDouble(parts[2]);
                            boolean available = parts[3].equals("1") || parts[3].equalsIgnoreCase("true");
                            roomList.add(new Room(id, type, (int) price, available));
                        }
                    }
                }
                scanner.close();
                System.out.println("✓ Directly loaded " + roomList.size() + " rooms");
            } else {
                System.out.println("No room file found at any location");
            }
        } catch (Exception e) {
            System.err.println("Error in loadRoomsDirectly: " + e.getMessage());
        }
        return roomList;
    }

    private void systemComponents() {
        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Customers", createCustomerPanel());
        tabbedPane.addTab("Rooms", createRoomPanel());
        tabbedPane.addTab("Services", createServicePanel());
        tabbedPane.addTab("Reservations", createReservationPanel());
        tabbedPane.addTab("Employees", createEmployeePanel());

        add(tabbedPane);
    }

    // *** Creation Methods ***

    private JPanel createCustomerPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columns = {"ID", "Name", "Phone"};
        custModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };
        custTable = new JTable(custModel);
        refreshCustTable();

        JPanel controls = new JPanel();
        addCustBtn = new JButton("Add Customer");
        updateCustBtn = new JButton("Update Customer");
        deleteCustBtn = new JButton("Delete Customer");

        addCustBtn.addActionListener(this);
        updateCustBtn.addActionListener(this);
        deleteCustBtn.addActionListener(this);

        controls.add(addCustBtn);
        controls.add(updateCustBtn);
        controls.add(deleteCustBtn);

        panel.add(new JScrollPane(custTable), BorderLayout.CENTER);
        panel.add(controls, BorderLayout.NORTH);
        return panel;
    }

    private JPanel createRoomPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columns = {"ID", "Type", "Price", "Status"};
        roomModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        roomTable = new JTable(roomModel);
        refreshRoomTable();

        JPanel controls = new JPanel();
        addRoomBtn = new JButton("Add Room");
        updateRoomBtn = new JButton("Update Room");
        deleteRoomBtn = new JButton("Delete Room");

        JLabel typeLabel = new JLabel("Type:");
        String[] filterTypes = {"All", "Single", "Double", "Suite"};
        filterTypeBox = new JComboBox<>(filterTypes);

        filterBox = new JCheckBox("Available Only");
        filterBtn = new JButton("Refresh");

        addRoomBtn.addActionListener(this);
        updateRoomBtn.addActionListener(this);
        deleteRoomBtn.addActionListener(this);
        filterBtn.addActionListener(this);

        controls.add(addRoomBtn);
        controls.add(updateRoomBtn);
        controls.add(deleteRoomBtn);
        controls.add(Box.createHorizontalStrut(20));
        controls.add(typeLabel);
        controls.add(filterTypeBox);
        controls.add(filterBox);
        controls.add(filterBtn);

        panel.add(new JScrollPane(roomTable), BorderLayout.CENTER);
        panel.add(controls, BorderLayout.NORTH);
        return panel;
    }

    private JPanel createServicePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columns ={"ID", "Name", "Price"};
        servModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        servTable = new JTable(servModel);
        refreshServTable();

        JPanel controls = new JPanel();
        addServiceBtn = new JButton("Add Service");
        updateServiceBtn = new JButton("Update Service");
        deleteServiceBtn = new JButton("Delete Service");
        reportServiceBtn = new JButton("Generate Report");
        addServiceBtn.addActionListener(this);
        updateServiceBtn.addActionListener(this);
        deleteServiceBtn.addActionListener(this);
        reportServiceBtn.addActionListener(this);

        controls.add(addServiceBtn);
        controls.add(updateServiceBtn);
        controls.add(deleteServiceBtn);
        controls.add(Box.createHorizontalStrut(20));
        controls.add(reportServiceBtn);

        panel.add(new JScrollPane(servTable), BorderLayout.CENTER);
        panel.add(controls, BorderLayout.NORTH);
        return panel;
    }

    private JPanel createReservationPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        billArea = new JTextArea();
        billArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        billArea.setEditable(false);

        JPanel controls = new JPanel(new GridLayout(3, 2, 10, 10));

        // Initialize combo boxes with proper types
        custBox = new JComboBox<>();
        roomBox = new JComboBox<>();
        activeResBox = new JComboBox<>();

        // Setup renderers for combo boxes
        setupComboBoxRenderers();

        refreshListsBtn = new JButton("Refresh Data");
        bookBtn = new JButton("Assign Room to Customer");
        addResServiceBtn = new JButton("Add Service to Reservation");
        checkoutBtn = new JButton("Checkout");
        viewCheckoutBtn = new JButton("View Near Checkout (2 Days)");

        refreshListsBtn.addActionListener(this);
        bookBtn.addActionListener(this);
        addResServiceBtn.addActionListener(this);
        checkoutBtn.addActionListener(this);
        viewCheckoutBtn.addActionListener(this);

        controls.add(refreshListsBtn);
        controls.add(new JLabel(" <- Click here first to load data"));
        controls.add(bookBtn);
        controls.add(addResServiceBtn);
        controls.add(viewCheckoutBtn);
        controls.add(checkoutBtn);

        JPanel Data = new JPanel(new GridLayout(1, 2));
        Data.add(new JScrollPane(custBox));
        Data.add(new JScrollPane(roomBox));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(Data, BorderLayout.NORTH);
        topPanel.add(controls, BorderLayout.CENTER);
        topPanel.add(new JScrollPane(activeResBox), BorderLayout.SOUTH);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(billArea), BorderLayout.CENTER);

        return panel;
    }

    private void setupComboBoxRenderers() {
        // Customer combo box renderer
        custBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                                                          int index, boolean isSelected, boolean cellHasFocus) {
                String display = "";
                if (value instanceof Customer) {
                    Customer c = (Customer) value;
                    display = c.getName() + " (ID: " + c.getId() + ")";
                } else if (value != null) {
                    display = value.toString();
                }
                return super.getListCellRendererComponent(list, display, index, isSelected, cellHasFocus);
            }
        });

        // Room combo box renderer
        roomBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                                                          int index, boolean isSelected, boolean cellHasFocus) {
                String display = "";
                if (value instanceof Room) {
                    Room r = (Room) value;
                    display = "Room " + r.getId() + " - " + r.getType() + " ($" + r.getPrice() + ")";
                } else if (value != null) {
                    display = value.toString();
                }
                return super.getListCellRendererComponent(list, display, index, isSelected, cellHasFocus);
            }
        });

        // Reservation combo box renderer
        activeResBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                                                          int index, boolean isSelected, boolean cellHasFocus) {
                String display = "";
                if (value instanceof Reservation) {
                    Reservation r = (Reservation) value;
                    display = r.getCustomer().getName() + " - Room " + r.getRoom().getId();
                } else if (value != null) {
                    display = value.toString();
                }
                return super.getListCellRendererComponent(list, display, index, isSelected, cellHasFocus);
            }
        });
    }

    private JPanel createEmployeePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        empModel = new DefaultTableModel(new String[]{"ID", "Name", "Phone", "Job Title"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        empTable = new JTable(empModel);
        refreshEmpTable();

        JPanel controls = new JPanel();
        addEmpBtn = new JButton("Add Employee");
        updateEmpBtn = new JButton("Update Employee");
        deleteEmpBtn = new JButton("Delete Employee");

        addEmpBtn.addActionListener(this);
        updateEmpBtn.addActionListener(this);
        deleteEmpBtn.addActionListener(this);

        controls.add(addEmpBtn);
        controls.add(updateEmpBtn);
        controls.add(deleteEmpBtn);

        panel.add(new JScrollPane(empTable), BorderLayout.CENTER);
        panel.add(controls, BorderLayout.NORTH);

        return panel;
    }

    // *** GUI Actions ***
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        // Customer Actions
        if (source == addCustBtn) addNewCustomer();
        else if (source == updateCustBtn) updateCustomer();
        else if (source == deleteCustBtn) deleteCustomer();

            // Room Actions
        else if (source == addRoomBtn) addNewRoom();
        else if (source == updateRoomBtn) updateRoom();
        else if (source == deleteRoomBtn) deleteRoom();
        else if (source == filterBtn) filterRooms();

            // Service Actions
        else if (source == addServiceBtn) addNewService();
        else if (source == updateServiceBtn) updateService();
        else if (source == deleteServiceBtn) deleteService();
        else if (source == reportServiceBtn) generateServiceReport();

            // Reservation Actions
        else if (source == refreshListsBtn) refreshResData();
        else if (source == bookBtn) bookRoom();
        else if (source == addResServiceBtn) addServiceToReservation();
        else if (source == checkoutBtn) checkoutGuest();
        else if (source == viewCheckoutBtn) viewNearCheckout();

            // Employee Actions
        else if (source == addEmpBtn) addNewEmployee();
        else if (source == updateEmpBtn) updateEmployee();
        else if (source == deleteEmpBtn) deleteEmployee();
    }

    // *** Logic Functions ***

    // Customer Logic
    private void addNewCustomer() {
        JTextField nameF = new JTextField();
        JTextField phoneF = new JTextField();
        Object[] msg = {"Name:", nameF, "Phone:", phoneF};

        if (JOptionPane.showConfirmDialog(this, msg, "New Customer",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION) {

            String name = nameF.getText().trim();
            String phone = phoneF.getText().trim();

            if (name.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name and phone cannot be empty",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int id = customers.isEmpty() ? 1 : customers.get(customers.size()-1).getId() + 1;
            Customer newCustomer = new Customer(id, name, phone);
            customers.add(newCustomer);

            AdminController.saveCustomers(customers);
            refreshCustTable();

            JOptionPane.showMessageDialog(this, "Customer added successfully!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void updateCustomer() {
        int selectedRow = custTable.getSelectedRow();
        if(selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a customer first",
                    "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) custModel.getValueAt(selectedRow, 0);
        Customer c = customers.stream().filter(cust -> cust.getId() == id).findFirst().orElse(null);

        if (c == null) {
            JOptionPane.showMessageDialog(this, "Customer not found",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JTextField nameF = new JTextField(c.getName());
        JTextField phoneF = new JTextField(c.getPhone());
        Object[] msg = {"Name:", nameF, "Phone:", phoneF};

        if (JOptionPane.showConfirmDialog(this, msg, "Update Customer",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION) {

            c.setName(nameF.getText().trim());
            c.setPhone(phoneF.getText().trim());
            AdminController.saveCustomers(customers);
            refreshCustTable();

            JOptionPane.showMessageDialog(this, "Customer updated successfully!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void deleteCustomer() {
        int selectedRow = custTable.getSelectedRow();
        if(selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a customer first",
                    "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) custModel.getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this customer?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            customers.removeIf(c -> c.getId() == id);
            AdminController.saveCustomers(customers);
            refreshCustTable();

            JOptionPane.showMessageDialog(this, "Customer deleted successfully!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Room Logic
    private void addNewRoom() {
        JTextField idF = new JTextField();
        JTextField priceF = new JTextField();
        String[] types = {"Single", "Double", "Suite"};
        JComboBox<String> typeBox = new JComboBox<>(types);
        Object[] msg = {"Room ID:", idF, "Type:", typeBox, "Price per night:", priceF};

        if (JOptionPane.showConfirmDialog(this, msg, "New Room",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(idF.getText().trim());
                String type = (String) typeBox.getSelectedItem();
                double price = Double.parseDouble(priceF.getText().trim());

                // Check if room ID already exists
                if (rooms.stream().anyMatch(r -> r.getId() == id)) {
                    JOptionPane.showMessageDialog(this, "Room ID already exists",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                rooms.add(new Room(id, type, (int) price, true)); // New rooms are available by default
                AdminController.saveRooms(rooms);
                refreshRoomTable();

                JOptionPane.showMessageDialog(this, "Room added successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input. Please enter numbers for ID and price.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "An error occurred",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updateRoom() {
        int selectedRow = roomTable.getSelectedRow();
        if(selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a room first",
                    "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) roomModel.getValueAt(selectedRow, 0);
        Room r = rooms.stream().filter(rm -> rm.getId() == id).findFirst().orElse(null);
        if(r == null) return;

        JTextField priceF = new JTextField(String.valueOf(r.getPrice()));
        JComboBox<String> typeBox = new JComboBox<>(new String[]{"Single", "Double", "Suite"});
        typeBox.setSelectedItem(r.getType());

        Object[] msg = {"Update Type:", typeBox, "Update Price:", priceF};
        if(JOptionPane.showConfirmDialog(this, msg, "Update Room",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION) {
            try {
                r.setPrice(Double.parseDouble(priceF.getText()));
                r.setType((String) typeBox.getSelectedItem());
                AdminController.saveRooms(rooms);
                refreshRoomTable();

                JOptionPane.showMessageDialog(this, "Room updated successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch(NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid price format",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteRoom() {
        int selectedRow = roomTable.getSelectedRow();
        if(selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a room first",
                    "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) roomModel.getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this room?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            rooms.removeIf(r -> r.getId() == id);
            AdminController.saveRooms(rooms);
            refreshRoomTable();

            JOptionPane.showMessageDialog(this, "Room deleted successfully!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void filterRooms() {
        roomModel.setRowCount(0);
        boolean onlyFree = filterBox.isSelected();
        String selectedType = (String) filterTypeBox.getSelectedItem();

        for (Room r : rooms) {
            boolean typeMatches = selectedType.equals("All") || r.getType().equals(selectedType);
            boolean availabilityMatches = !onlyFree || r.isAvailable();

            if (typeMatches && availabilityMatches) {
                roomModel.addRow(new Object[]{
                        r.getId(),
                        r.getType(),
                        r.getPrice(),
                        r.isAvailable() ? "Available" : "Occupied"
                });
            }
        }
    }

    // Service Logic
    private void addNewService() {
        JTextField nameF = new JTextField();
        JTextField priceF = new JTextField();
        Object[] msg = {"Service Name:", nameF, "Price:", priceF};
        if (JOptionPane.showConfirmDialog(this, msg, "New Service",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION) {
            try {
                int id = services.isEmpty() ? 1 : services.get(services.size()-1).getId() + 1;
                services.add(new Service(id, nameF.getText(), Double.parseDouble(priceF.getText())));
                AdminController.saveServices(services);
                refreshServTable();

                JOptionPane.showMessageDialog(this, "Service added successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Invalid price format",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updateService() {
        int selectedRow = servTable.getSelectedRow();
        if(selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a service",
                    "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) servModel.getValueAt(selectedRow, 0);
        Service s = services.stream().filter(serv -> serv.getId() == id).findFirst().orElse(null);

        JTextField nameF = new JTextField(s.getName());
        JTextField priceF = new JTextField(String.valueOf(s.getPrice()));
        Object[] msg = {"Service Name:", nameF, "Price:", priceF};

        if (JOptionPane.showConfirmDialog(this, msg, "Update Service",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION) {
            try {
                s.setName(nameF.getText());
                s.setPrice((int) Double.parseDouble(priceF.getText()));
                AdminController.saveServices(services);
                refreshServTable();

                JOptionPane.showMessageDialog(this, "Service updated successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid price format",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteService() {
        int selectedRow = servTable.getSelectedRow();
        if(selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a service",
                    "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) servModel.getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this service?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            services.removeIf(s -> s.getId() == id);
            AdminController.saveServices(services);
            refreshServTable();

            JOptionPane.showMessageDialog(this, "Service deleted successfully!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void generateServiceReport() {
        if (services.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No services available",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder report = new StringBuilder();
        report.append("     --- Service Usage Statistics ---\n\n");
        report.append(String.format("%-20s %-10s %-10s\n", "Service Name", "Price", "Times Used"));
        report.append("------------------------------------------\n");

        int totalUsage = 0;
        double totalRevenue = 0.0;

        for(Service s : services) {
            // Get values safely
            String name = s.getName();
            double price = 0.0;
            int usageCount = 0;

            try {
                // Make sure price is double
                Object priceObj = s.getPrice();
                if (priceObj instanceof Integer) {
                    price = ((Integer) priceObj).doubleValue();
                } else if (priceObj instanceof Double) {
                    price = (Double) priceObj;
                } else {
                    price = Double.parseDouble(priceObj.toString());
                }
            } catch (Exception e) {
                System.err.println("Error getting price for service: " + name);
                price = 0.0;
            }

            try {
                // Make sure usageCount is integer
                Object usageObj = s.getUsageCount();
                if (usageObj instanceof Integer) {
                    usageCount = (Integer) usageObj;
                } else {
                    usageCount = Integer.parseInt(usageObj.toString());
                }
            } catch (Exception e) {
                System.err.println("Error getting usage count for service: " + name);
                usageCount = 0;
            }

            // Format with proper types
            report.append(String.format("%-20s $%-9.2f %-10d\n", name, price, usageCount));

            totalUsage += usageCount;
            totalRevenue += (price * usageCount);
        }

        report.append("------------------------------------------\n");
        report.append(String.format("Total Services Provided: %d\n", totalUsage));
        report.append(String.format("Total Revenue from Services: $%.2f\n", totalRevenue));

        JTextArea textArea = new JTextArea(report.toString());
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setEditable(false);
        JOptionPane.showMessageDialog(this, new JScrollPane(textArea),
                "Statistical Report", JOptionPane.INFORMATION_MESSAGE);
    }

    // Reservation Logic - FIXED VERSION
    private void refreshResData() {
        custBox.removeAllItems();
        roomBox.removeAllItems();
        activeResBox.removeAllItems();

        // Add Customer objects (not strings)
        for (Customer c : customers) {
            custBox.addItem(c);
        }

        // Add Room objects (not strings)
        for (Room r : rooms) {
            roomBox.addItem(r);
        }

        // Add Reservation objects (not strings)
        for (Reservation res : reservations) {
            activeResBox.addItem(res);
        }

        System.out.println("Refreshed: " + custBox.getItemCount() + " customers, " +
                roomBox.getItemCount() + " rooms, " +
                activeResBox.getItemCount() + " reservations");
    }

    private void bookRoom() {
        if (custBox.getSelectedItem() == null || roomBox.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Please select both a customer and a room",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Customer c = (Customer) custBox.getSelectedItem();
        Room r = (Room) roomBox.getSelectedItem();

        if (c == null || r == null) {
            JOptionPane.showMessageDialog(this, "Invalid selection",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!r.isAvailable()) {
            JOptionPane.showMessageDialog(this, "Room " + r.getId() + " is occupied!",
                    "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String daysInput = JOptionPane.showInputDialog(this,
                "Enter number of days for " + c.getName() + " in Room " + r.getId() + ":",
                "Booking Duration", JOptionPane.QUESTION_MESSAGE);

        if (daysInput != null && !daysInput.trim().isEmpty()) {
            try {
                int days = Integer.parseInt(daysInput.trim());
                if (days <= 0) {
                    JOptionPane.showMessageDialog(this, "Please enter a positive number of days",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Reservation res = new Reservation(c, r, days);
                reservations.add(res);
                activeResBox.addItem(res);
                r.setAvailable(false);

                AdminController.saveRooms(rooms);
                refreshResData();

                JOptionPane.showMessageDialog(this,
                        "Room " + r.getId() + " booked for " + c.getName() + " for " + days + " days!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid number of days",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void addServiceToReservation() {
        if (activeResBox.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Please select a reservation first",
                    "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (services.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No services available",
                    "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Reservation res = (Reservation) activeResBox.getSelectedItem();

        // Create combo box with Service objects
        JComboBox<Service> sBox = new JComboBox<>();

        // Add all services
        for (Service service : services) {
            sBox.addItem(service);
        }

        // Add custom renderer to display service names and prices
        sBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                                                          int index, boolean isSelected, boolean cellHasFocus) {
                String display = "";
                if (value instanceof Service) {
                    Service service = (Service) value;
                    display = service.getName() + " - $" + service.getPrice();
                } else if (value != null) {
                    display = value.toString();
                }
                return super.getListCellRendererComponent(list, display, index, isSelected, cellHasFocus);
            }
        });

        // Create a panel with label and combo box
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Select service to add to reservation:"), BorderLayout.NORTH);
        panel.add(sBox, BorderLayout.CENTER);

        if (JOptionPane.showConfirmDialog(this, panel, "Add Service",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION) {

            Service selectedService = (Service) sBox.getSelectedItem();
            if (selectedService != null) {
                res.addService(selectedService);
                selectedService.incrementUsage();
                AdminController.saveServices(services);

                JOptionPane.showMessageDialog(this,
                        "Service '" + selectedService.getName() + "' added to reservation!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void checkoutGuest() {
        if (activeResBox.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Please select a reservation first",
                    "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Reservation res = (Reservation) activeResBox.getSelectedItem();

        int confirm = JOptionPane.showConfirmDialog(this,
                "Checkout " + res.getCustomer().getName() + " from Room " + res.getRoom().getId() + "?",
                "Confirm Checkout", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            billArea.setText(res.generateBill());
            res.getRoom().setAvailable(true);
            reservations.remove(res);
            activeResBox.removeItem(res);

            AdminController.saveRooms(rooms);
            refreshResData();

            JOptionPane.showMessageDialog(this, "Checkout completed!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void viewNearCheckout() {
        if (reservations.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No active reservations",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder sb = new StringBuilder("Guests checking out within 2 days:\n\n");
        LocalDate today = LocalDate.now();
        boolean isFound = false;

        for (Reservation res : reservations) {
            LocalDate checkout = res.getCheckOutDate();
            long daysUntil = ChronoUnit.DAYS.between(today, checkout);
            if (daysUntil >= 0 && daysUntil <= 2) {
                sb.append("• ").append(res.getCustomer().getName())
                        .append(" (Room ").append(res.getRoom().getId()).append(")")
                        .append(" - Checkout: ").append(checkout)
                        .append(" (").append(daysUntil).append(" days left)\n");
                isFound = true;
            }
        }

        if (!isFound) {
            sb.append("No checkouts approaching in the next 2 days.");
        }

        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setEditable(false);
        JOptionPane.showMessageDialog(this, new JScrollPane(textArea),
                "Near Checkout", JOptionPane.INFORMATION_MESSAGE);
    }

    // Employee Logic
    private void addNewEmployee() {
        JTextField nameF = new JTextField();
        JTextField phoneF = new JTextField();
        JTextField jobF = new JTextField();
        Object[] msg = {"Name:", nameF, "Phone:", phoneF, "Job Title:", jobF};

        if (JOptionPane.showConfirmDialog(this, msg, "New Employee",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION) {
            int id = employees.isEmpty() ? 1 : employees.get(employees.size()-1).getId() + 1;
            employees.add(new Employee(id, nameF.getText(), phoneF.getText(), jobF.getText()));
            AdminController.saveEmployees(employees);
            refreshEmpTable();

            JOptionPane.showMessageDialog(this, "Employee added successfully!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void updateEmployee() {
        int selectedRow = empTable.getSelectedRow();
        if(selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an employee",
                    "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) empModel.getValueAt(selectedRow, 0);
        Employee e = employees.stream().filter(emp -> emp.getId() == id).findFirst().orElse(null);

        JTextField nameF = new JTextField(e.getName());
        JTextField phoneF = new JTextField(e.getPhone());
        JTextField jobF = new JTextField(e.getJobTitle());
        Object[] msg = {"Name:", nameF, "Phone:", phoneF, "Job Title:", jobF};

        if (JOptionPane.showConfirmDialog(this, msg, "Update Employee",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION) {
            e.setName(nameF.getText());
            e.setPhone(phoneF.getText());
            e.setJobTitle(jobF.getText());
            AdminController.saveEmployees(employees);
            refreshEmpTable();

            JOptionPane.showMessageDialog(this, "Employee updated successfully!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void deleteEmployee() {
        int selectedRow = empTable.getSelectedRow();
        if(selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an employee",
                    "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) empModel.getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this employee?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            employees.removeIf(e -> e.getId() == id);
            AdminController.saveEmployees(employees);
            refreshEmpTable();

            JOptionPane.showMessageDialog(this, "Employee deleted successfully!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Refresh Tables Logic
    private void refreshRoomTable() {
        roomModel.setRowCount(0);
        for (Room r : rooms) {
            roomModel.addRow(new Object[]{
                    r.getId(),
                    r.getType(),
                    r.getPrice(),
                    r.isAvailable() ? "Available" : "Occupied"
            });
        }
    }

    private void refreshCustTable() {
        custModel.setRowCount(0);
        for (Customer c : customers) {
            custModel.addRow(new Object[]{c.getId(), c.getName(), c.getPhone()});
        }
    }

    private void refreshServTable() {
        servModel.setRowCount(0);
        for (Service s : services) {
            servModel.addRow(new Object[]{s.getId(), s.getName(), s.getPrice()});
        }
    }

    private void refreshEmpTable() {
        empModel.setRowCount(0);
        for (Employee e : employees) {
            empModel.addRow(new Object[]{e.getId(), e.getName(), e.getPhone(), e.getJobTitle()});
        }
    }
}