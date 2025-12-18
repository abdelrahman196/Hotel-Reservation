package app;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Employee {
    private int id;
    private String name;
    private String phone;
    private String jobTitle;

    // Constructor
    public Employee(int id, String name, String phone, String jobTitle) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.jobTitle = jobTitle;
    }

    // CORRECT toString() override - NO PARAMETERS!
    @Override
    public String toString() {
        return name + " (" + jobTitle + ")";
    }

    // If you want a method that takes parameters, don't use @Override
    public String formatEmployeeInfo(String name, String jobTitle) {
        return name + " (" + jobTitle + ")";
    }

    // Getters and setters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getJobTitle() { return jobTitle; }

    public void setName(String name) { this.name = name; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }
}