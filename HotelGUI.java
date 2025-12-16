import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

class GUIStarter implements Runnable {
    @Override
    public void run() {
        HotelGUI gui = new HotelGUI();
        gui.setVisible(true);
    }
}

public class HotelGUI extends JFrame {
    // *** Data Lists ***
    /*
    private List<Customer> customers;
    private List<Room> rooms;
    private List<Service> services;
    private List<Reservation> reservations;
    private List<Employee> employees;
    */
    // *** Global Components ***
    private DefaultTableModel custModel;    private JTable custTable;
    private DefaultTableModel roomModel;    private JTable roomTable;
    private DefaultTableModel servModel;    private JTable servTable;
    private DefaultTableModel empModel;     private JTable empTable;

    private JCheckBox filterBox;                // Checkbox for "Available Only"
    private JComboBox<String> filterTypeBox;    // Dropdown for Room Type filtering
    private JComboBox<String> roomBox;
    private JComboBox<String> custBox;
    private JComboBox<String> activeResBox;
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
        systemComponents();

        // *** Load Data From Files ***
        // rooms = FileManager.loadRooms();
        // customers = FileManager.loadCustomers();
        // services = FileManager.loadServices();
        // reservations = new ArrayList<>();
        // employees = FileManager.loadEmployees();
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
        custModel = new DefaultTableModel(columns, 0);
        custTable = new JTable(custModel);
        // refreshCustTable();

        JPanel controls = new JPanel();
        addCustBtn = new JButton("Add Customer");
        updateCustBtn = new JButton("Update Customer");
        deleteCustBtn = new JButton("Delete Customer");

//        addCustBtn.addActionListener(this);
//        updateCustBtn.addActionListener(this);
//        deleteCustBtn.addActionListener(this);

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
        roomModel = new DefaultTableModel(columns, 0);
        roomTable = new JTable(roomModel);
        // refreshRoomTable();

        JPanel controls = new JPanel();
        addRoomBtn = new JButton("Add Room");
        updateRoomBtn = new JButton("Update Room");
        deleteRoomBtn = new JButton("Delete Room");

        JLabel typeLabel = new JLabel("Type:");
        String[] filterTypes = {"All", "Single", "Double", "Suite"};
        filterTypeBox = new JComboBox<>(filterTypes);

        filterBox = new JCheckBox("Available Only");
        filterBtn = new JButton("Refresh");

//        addRoomBtn.addActionListener(this);
//        updateRoomBtn.addActionListener(this);
//        deleteRoomBtn.addActionListener(this);
//        filterBtn.addActionListener(this);

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
        servModel = new DefaultTableModel(columns, 0);
        servTable = new JTable(servModel);
        // refreshServTable();

        JPanel controls = new JPanel();
        addServiceBtn = new JButton("Add Service");
        updateServiceBtn = new JButton("Update Service");
        deleteServiceBtn = new JButton("Delete Service");
        reportServiceBtn = new JButton("Generate Report");
//        addServiceBtn.addActionListener(this);
//        updateServiceBtn.addActionListener(this);
//        deleteServiceBtn.addActionListener(this);
//        reportServiceBtn.addActionListener(this);

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

        JPanel controls = new JPanel(new GridLayout(3, 2, 10, 10));

        custBox = new JComboBox<>();
        roomBox = new JComboBox<>();
        activeResBox = new JComboBox<>();

        refreshListsBtn = new JButton("Refresh Data");
        bookBtn = new JButton("Assign Room to Customer");
        addResServiceBtn = new JButton("Add Service to Reservation");
        checkoutBtn = new JButton("Checkout");
        viewCheckoutBtn = new JButton("View Near Checkout (2 Days)");

//        refreshListsBtn.addActionListener(this);
//        bookBtn.addActionListener(this);
//        addResServiceBtn.addActionListener(this);
//        checkoutBtn.addActionListener(this);
//        viewCheckoutBtn.addActionListener(this);

        controls.add(refreshListsBtn);
        controls.add(new JLabel(" <- Click here first to load data"));
        controls.add(bookBtn);
        controls.add(addResServiceBtn);
        controls.add(viewCheckoutBtn);
        controls.add(checkoutBtn);

        JPanel Data = new JPanel(new GridLayout(1, 2));
        Data.add(custBox);
        Data.add(roomBox);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(Data, BorderLayout.NORTH);
        topPanel.add(controls, BorderLayout.CENTER);
        topPanel.add(activeResBox, BorderLayout.SOUTH);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(billArea), BorderLayout.CENTER);

        return panel;
    }

    private JPanel createEmployeePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        empModel = new DefaultTableModel(new String[]{"ID", "Name", "Phone", "Job Title"}, 0);
        empTable = new JTable(empModel);
        // refreshEmpTable();

        JPanel controls = new JPanel();
        addEmpBtn = new JButton("Add Employee");
        updateEmpBtn = new JButton("Update Employee");
        deleteEmpBtn = new JButton("Delete Employee");

//        addEmpBtn.addActionListener(this);
//        updateEmpBtn.addActionListener(this);
//        deleteEmpBtn.addActionListener(this);

        controls.add(addEmpBtn);
        controls.add(updateEmpBtn);
        controls.add(deleteEmpBtn);

        panel.add(new JScrollPane(empTable), BorderLayout.CENTER);
        panel.add(controls, BorderLayout.NORTH);

        return panel;
    }

    /*

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
        if (JOptionPane.showConfirmDialog(this, msg, "New Customer", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            int id = customers.isEmpty() ? 1 : customers.get(customers.size()-1).getId() + 1;
            customers.add(new Customer(id, nameF.getText(), phoneF.getText()));
            FileManager.saveCustomers(customers);
            refreshCustTable();
        }
    }

    private void updateCustomer() {
        int selectedRow = custTable.getSelectedRow();
        if(selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a customer");
            return;
        }
        int id = (int) custModel.getValueAt(selectedRow, 0);
        Customer c = customers.stream().filter(cust -> cust.getId() == id).findFirst().orElse(null);

        JTextField nameF = new JTextField(c.getName());
        JTextField phoneF = new JTextField(c.getPhone());
        Object[] msg = {"Name:", nameF, "Phone:", phoneF};

        if (JOptionPane.showConfirmDialog(this, msg, "Update Customer", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            c.setName(nameF.getText());
            c.setPhone(phoneF.getText());
            FileManager.saveCustomers(customers);
            refreshCustTable();
        }
    }

    private void deleteCustomer() {
        int selectedRow = custTable.getSelectedRow();
        if(selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a customer");
            return;
        }
        int id = (int) custModel.getValueAt(selectedRow, 0);
        customers.removeIf(c -> c.getId() == id);
        FileManager.saveCustomers(customers);
        refreshCustTable();
    }

    // Room Logic
    private void addNewRoom() {
        JTextField idF = new JTextField();
        JTextField priceF = new JTextField();
        String[] types = {"Single", "Double", "Suite"};
        JComboBox<String> typeBox = new JComboBox<>(types);
        Object[] msg = {"ID:", idF, "Type:", typeBox, "Price:", priceF};

        if (JOptionPane.showConfirmDialog(this, msg, "New Room", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(idF.getText());
                String type = (String) typeBox.getSelectedItem();
                double price = Double.parseDouble(priceF.getText());
                rooms.add(new Room(id, type, price, false));
                FileManager.saveRooms(rooms);
                refreshRoomTable();
            } catch (Exception e) { JOptionPane.showMessageDialog(this, "Invalid Input"); }
        }
    }

    private void updateRoom() {
        int selectedRow = roomTable.getSelectedRow();
        if(selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a room first");
            return;
        }

        int id = (int) roomModel.getValueAt(selectedRow, 0);
        Room r = rooms.stream().filter(rm -> rm.getId() == id).findFirst().orElse(null);
        if(r == null) return;

        JTextField priceF = new JTextField(String.valueOf(r.getPrice()));
        JComboBox<String> typeBox = new JComboBox<>(new String[]{"Single", "Double", "Suite"});
        typeBox.setSelectedItem(r.getType());

        Object[] msg = {"Update Type:", typeBox, "Update Price:", priceF};
        if(JOptionPane.showConfirmDialog(this, msg, "Update Room", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                r.setPrice(Double.parseDouble(priceF.getText()));
                r.setType((String) typeBox.getSelectedItem());
                FileManager.saveRooms(rooms);
                refreshRoomTable();
            } catch(Exception e) { JOptionPane.showMessageDialog(this, "Invalid Price"); }
        }
    }

    private void deleteRoom() {
        int selectedRow = roomTable.getSelectedRow();
        if(selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a room first");
            return;
        }
        int id = (int) roomModel.getValueAt(selectedRow, 0);
        rooms.removeIf(r -> r.getId() == id);
        FileManager.saveRooms(rooms);
        refreshRoomTable();
    }

    private void filterRooms() {
        roomModel.setRowCount(0);
        boolean onlyFree = filterBox.isSelected();
        for (Room r : rooms) {
            if (!onlyFree || !r.isOccupied()) {
                roomModel.addRow(new Object[]{r.getId(), r.getType(), r.getPrice(), r.isOccupied() ? "Occupied" : "Free"});
            }
        }
    }

    // Service Logic
    private void addNewService() {
        JTextField nameF = new JTextField();
        JTextField priceF = new JTextField();
        Object[] msg = {"Service Name:", nameF, "Price:", priceF};
        if (JOptionPane.showConfirmDialog(this, msg, "New Service", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                int id = services.isEmpty() ? 1 : services.get(services.size()-1).getId() + 1;
                services.add(new Service(id, nameF.getText(), Double.parseDouble(priceF.getText())));
                FileManager.saveServices(services);
                refreshServTable();
            } catch (Exception e) { JOptionPane.showMessageDialog(this, "Invalid Price"); }
        }
    }

    private void updateService() {
        int selectedRow = servTable.getSelectedRow();
        if(selectedRow == -1) { JOptionPane.showMessageDialog(this, "Select a service"); return; }
        int id = (int) servModel.getValueAt(selectedRow, 0);
        Service s = services.stream().filter(serv -> serv.getId() == id).findFirst().orElse(null);

        JTextField nameF = new JTextField(s.getName());
        JTextField priceF = new JTextField(String.valueOf(s.getPrice()));
        Object[] msg = {"Service Name:", nameF, "Price:", priceF};

        if (JOptionPane.showConfirmDialog(this, msg, "Update Service", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                s.setName(nameF.getText());
                s.setPrice(Double.parseDouble(priceF.getText()));
                FileManager.saveServices(services);
                refreshServTable();
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Invalid Price"); }
        }
    }

    private void deleteService() {
        int selectedRow = servTable.getSelectedRow();
        if(selectedRow == -1) { JOptionPane.showMessageDialog(this, "Select a service"); return; }
        int id = (int) servModel.getValueAt(selectedRow, 0);
        services.removeIf(s -> s.getId() == id);
        FileManager.saveServices(services);
        refreshServTable();
    }

    private void generateServiceReport() {
        StringBuilder report = new StringBuilder();
        report.append("     --- Service Usage Statistics ---\n\n");
        report.append(String.format("%-20s %-10s %-10s\n", "Service Name", "Price", "Times Used"));
        report.append("------------------------------------------\n");
        int totalUsage = 0;
        double totalRevenue = 0;
        for(Service s : services) {
            report.append(String.format("%-20s $%-9.2f %-10d\n", s.getName(), s.getPrice(), s.getUsageCount()));
            totalUsage += s.getUsageCount();
            totalRevenue += (s.getPrice() * s.getUsageCount());
        }
        report.append("------------------------------------------\n");
        report.append("Total Services Provided: " + totalUsage + "\n");
        report.append("Total Revenue from Services: $" + String.format("%.2f", totalRevenue));
        JTextArea textArea = new JTextArea(report.toString());
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setEditable(false);
        JOptionPane.showMessageDialog(this, new JScrollPane(textArea), "Statistical Report", JOptionPane.INFORMATION_MESSAGE);
    }

    // Reservation Logic
    private void refreshResData() {
        custBox.removeAllItems();
        roomBox.removeAllItems();
        activeResBox.removeAllItems();
        for (Customer c : customers) custBox.addItem(c);
        for (Room r : rooms) roomBox.addItem(r);
        for (Reservation res : reservations) activeResBox.addItem(res);
    }

    private void bookRoom() {
        Customer c = (Customer) custBox.getSelectedItem();
        Room r = (Room) roomBox.getSelectedItem();
        if (c == null || r == null) return;
        if (r.isOccupied()) {
            JOptionPane.showMessageDialog(this, "Room is occupied!");
            return;
        }

        String d = JOptionPane.showInputDialog("Days:", "1");
        if (d != null) {
            try {
                Reservation res = new Reservation(c, r, Integer.parseInt(d));
                reservations.add(res);
                activeResBox.addItem(res);
                r.setOccupied(true);
                FileManager.saveRooms(rooms);
                JOptionPane.showMessageDialog(this, "Booked!");
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Invalid days"); }
        }
    }

    private void addServiceToReservation() {
        Reservation res = (Reservation) activeResBox.getSelectedItem();
        if (res == null) return;
        JComboBox<Service> sBox = new JComboBox<>(services.toArray(new Service[0]));
        if (JOptionPane.showConfirmDialog(this, sBox, "Pick Service", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            Service s = (Service) sBox.getSelectedItem();
            res.addService(s);
            s.incrementUsage();
            FileManager.saveServices(services);
            JOptionPane.showMessageDialog(this, "Service Added!");
        }
    }

    private void checkoutGuest() {
        Reservation res = (Reservation) activeResBox.getSelectedItem();
        if (res == null) return;
        billArea.setText(res.generateBill());
        res.getRoom().setOccupied(false);
        reservations.remove(res);
        activeResBox.removeItem(res);
        FileManager.saveRooms(rooms);
    }

    private void viewNearCheckout() {
        StringBuilder sb = new StringBuilder("Guests checking out within 2 days:\n\n");
        LocalDate today = LocalDate.now();
        boolean isFound = false;
        for (Reservation res : reservations) {
            LocalDate checkout = res.getCheckOutDate();
            long daysUntil = ChronoUnit.DAYS.between(today, checkout);
            if (daysUntil >= 0 && daysUntil <= 2) {
                sb.append(res.getCustomer().getName())
                        .append(" (Room ").append(res.getRoom().getId()).append(")")
                        .append(" - Checkout: ").append(checkout)
                        .append(" (").append(daysUntil).append(" days left)\n");
                isFound = true;
            }
        }
        if (!isFound) sb.append("No checkouts approaching in the next 2 days.");
        JOptionPane.showMessageDialog(this, sb.toString(), "Near Checkout", JOptionPane.INFORMATION_MESSAGE);
    }

    // Employee Logic
    private void addNewEmployee() {
        JTextField nameF = new JTextField();
        JTextField phoneF = new JTextField();
        JTextField jobF = new JTextField();
        Object[] msg = {"Name:", nameF, "Phone:", phoneF, "Job Title:", jobF};

        if (JOptionPane.showConfirmDialog(this, msg, "New Employee", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            int id = employees.isEmpty() ? 1 : employees.get(employees.size()-1).getId() + 1;
            employees.add(new Employee(id, nameF.getText(), phoneF.getText(), jobF.getText()));
            FileManager.saveEmployees(employees);
            refreshEmpTable();
        }
    }

    private void updateEmployee() {
        int selectedRow = empTable.getSelectedRow();
        if(selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select an employee");
            return;
        }
        int id = (int) empModel.getValueAt(selectedRow, 0);
        Employee e = employees.stream().filter(emp -> emp.getId() == id).findFirst().orElse(null);

        JTextField nameF = new JTextField(e.getName());
        JTextField phoneF = new JTextField(e.getPhone());
        JTextField jobF = new JTextField(e.getJobTitle());
        Object[] msg = {"Name:", nameF, "Phone:", phoneF, "Job Title:", jobF};

        if (JOptionPane.showConfirmDialog(this, msg, "Update Employee", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            e.setName(nameF.getText());
            e.setPhone(phoneF.getText());
            e.setJobTitle(jobF.getText());
            FileManager.saveEmployees(employees);
            refreshEmpTable();
        }
    }

    private void deleteEmployee() {
        int selectedRow = empTable.getSelectedRow();
        if(selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select an employee");
            return;
        }
        int id = (int) empModel.getValueAt(selectedRow, 0);
        employees.removeIf(e -> e.getId() == id);
        FileManager.saveEmployees(employees);
        refreshEmpTable();
    }

    // Refresh Tables Logic
    private void refreshRoomTable() {
        roomModel.setRowCount(0);
        for (Room r : rooms) roomModel.addRow(new Object[]{r.getId(), r.getType(), r.getPrice(), r.isOccupied() ? "Occupied" : "Free"});
    }
    private void refreshCustTable() {
        custModel.setRowCount(0);
        for (Customer c : customers) custModel.addRow(new Object[]{c.getId(), c.getName(), c.getPhone()});
    }
    private void refreshServTable() {
        servModel.setRowCount(0);
        for (Service s : services) servModel.addRow(new Object[]{s.getId(), s.getName(), s.getPrice()});
    }
    private void refreshEmpTable() {
        empModel.setRowCount(0);
        for (Employee e : employees) empModel.addRow(new Object[]{e.getId(), e.getName(), e.getPhone(), e.getJobTitle()});
    }
    */
}
