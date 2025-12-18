package app;

import java.io.*;
import java.util.*;
import java.time.LocalDate;

public class Reservation {
    private Customer customer;
    private Room room;
    private int days;
    private LocalDate checkInDate;
    private List<Service> services;

    public Reservation(Customer customer, Room room, int days) {
        this.customer = customer;
        this.room = room;
        this.days = days;
        this.checkInDate = LocalDate.now();
        this.services = new ArrayList<>();
    }

    public void addService(Service service) {
        services.add(service);
    }

    public Room getRoom() { return room; }
    public Customer getCustomer() { return customer; }

    public LocalDate getCheckInDate() { return checkInDate; }

    public LocalDate getCheckOutDate() {
        return checkInDate.plusDays(days);
    }

    public double calculateTotal() {
        double roomCost = room.getPrice() * days;
        double serviceCost = 0;
        for (Service s : services) {
            serviceCost += s.getPrice();
        }
        return roomCost + serviceCost;
    }

    public String generateBill() {
        StringBuilder sb = new StringBuilder();
        sb.append("---------- HOTEL INVOICE ----------\n");
        sb.append("Guest: ").append(customer.getName()).append("\n");
        sb.append("Room: ").append(room.getId()).append(" (").append(room.getType()).append(")\n");
        sb.append("Check-In: ").append(checkInDate).append("\n");
        sb.append("Nights: ").append(days).append("\n");
        sb.append("Room Total: $").append(room.getPrice() * days).append("\n");
        sb.append("--- Services ---\n");
        for (Service s : services) {
            sb.append(s.getName()).append(": $").append(s.getPrice()).append("\n");
        }
        sb.append("-----------------------------------\n");
        sb.append("TOTAL DUE: $").append(calculateTotal()).append("\n");
        sb.append("-----------------------------------");
        return sb.toString();
    }

    @Override
    public String toString() {
        return customer.getName() + " - Room " + room.getId();
    }
}