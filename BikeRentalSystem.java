package BikeRentalSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Bike Class
class Bike {
    private String bikeId;
    private String brand;
    private String model;
    private double basePricePerDay;
    private boolean isAvailable;

    public Bike(String bikeId, String brand, String model, double basePricePerDay) {
        this.bikeId = bikeId;
        this.brand = brand;
        this.model = model;
        this.basePricePerDay = basePricePerDay;
        this.isAvailable = true;
    }

    public String getBikeId() {
        return bikeId;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public double calculatePrice(int rentalDays) {
        return basePricePerDay * rentalDays;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void rent() {
        isAvailable = false;
    }

    public void returnBike() {
        isAvailable = true;
    }
}

// Customer Class
class Customer {
    private String customerId;
    private String name;

    public Customer(String customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }
}

// Rental Class
class Rental {
    private Bike bike;
    private Customer customer;
    private int days;

    public Rental(Bike bike, Customer customer, int days) {
        this.bike = bike;
        this.customer = customer;
        this.days = days;
    }

    public Bike getBike() {
        return bike;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getDays() {
        return days;
    }
}

// BikeRentalSystem Core Class
public class BikeRentalSystem {
    private List<Bike> bikes;
    private List<Customer> customers;
    private List<Rental> rentals;

    public BikeRentalSystem() {
        bikes = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void addBike(Bike bike) {
        bikes.add(bike);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void rentBike(Bike bike, Customer customer, int days) {
        if (bike.isAvailable()) {
            bike.rent();
            rentals.add(new Rental(bike, customer, days));
            System.out.println("\nBike rented successfully!");
        } else {
            System.out.println("Bike is not available for rent.");
        }
    }

    public void returnBike(Bike bikeToReturn) {
        if (bikeToReturn == null) {
            System.out.println("Invalid bike.");
            return;
        }

        Rental rentalToRemove = null;
        Customer customer = null;

        for (Rental rental : rentals) {
            if (bikeToReturn.equals(rental.getBike())) {
                rentalToRemove = rental;
                customer = rental.getCustomer();
                break;
            }
        }

        if (rentalToRemove != null) {
            bikeToReturn.returnBike();
            rentals.remove(rentalToRemove);
            System.out.println("Bike returned successfully to " + customer.getName());
        } else {
            System.out.println("Bike was not rented or rental record not found.");
        }
    }

    public void menu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== Bike Rental System =====");
            System.out.println("1. Rent a bike");
            System.out.println("2. Return a bike");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter your name: ");
                    String customerName = scanner.nextLine();

                    System.out.println("\nAvailable bikes:");
                    for (Bike bike : bikes) {
                        if (bike.isAvailable()) {
                            System.out.println(bike.getBikeId() + " - " + bike.getBrand() + " " + bike.getModel());
                        }
                    }

                    System.out.print("\nEnter the bike ID you want to rent: ");
                    String bikeId = scanner.nextLine();

                    System.out.print("Enter number of rental days: ");
                    int rentalDays = scanner.nextInt();
                    scanner.nextLine(); // consume newline

                    Bike selectedBike = null;
                    for (Bike bike : bikes) {
                        if (bike.getBikeId().equals(bikeId) && bike.isAvailable()) {
                            selectedBike = bike;
                            break;
                        }
                    }

                    if (selectedBike != null) {
                        Customer newCustomer = new Customer("CUS" + (customers.size() + 1), customerName);
                        addCustomer(newCustomer);

                        double totalPrice = selectedBike.calculatePrice(rentalDays);
                        System.out.println("\n=== Rental Summary ===");
                        System.out.println("Customer ID: " + newCustomer.getCustomerId());
                        System.out.println("Name: " + newCustomer.getName());
                        System.out.println("Bike: " + selectedBike.getBrand() + " " + selectedBike.getModel());
                        System.out.println("Rental Days: " + rentalDays);
                        System.out.println("Total Price: ₹" + totalPrice);
                        System.out.print("Confirm rental? (Y/N): ");

                        String confirm = scanner.nextLine();
                        if ("Y".equalsIgnoreCase(confirm)) {
                            rentBike(selectedBike, newCustomer, rentalDays);
                        } else {
                            System.out.println("Rental cancelled.");
                        }
                    } else {
                        System.out.println("Invalid selection or bike not available.");
                    }
                    break;

                case 2:
                    System.out.print("Enter the bike ID to return: ");
                    String returnId = scanner.nextLine();

                    Bike bikeToReturn = null;
                    for (Bike bike : bikes) {
                        if (bike.getBikeId().equals(returnId) && !bike.isAvailable()) {
                            bikeToReturn = bike;
                            break;
                        }
                    }

                    returnBike(bikeToReturn);
                    break;

                case 3:
                    System.out.println("Thank you for using the Bike Rental System!");
                    return;

                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    // Entry Point
    public static void main(String[] args) {
        BikeRentalSystem rentalSystem = new BikeRentalSystem();

        rentalSystem.addBike(new Bike("B001", "Hero", "Splendor", 100));
        rentalSystem.addBike(new Bike("B002", "Honda", "Shine", 150));
        rentalSystem.addBike(new Bike("B003", "Royal Enfield", "Classic 350", 300));

        rentalSystem.menu();
    }
}















