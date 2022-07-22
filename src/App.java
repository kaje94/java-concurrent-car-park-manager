import classes.*;
import classes.vehicles.Vehicle;
import enums.*;

import java.util.Scanner;

public class App {
    private static PettahCarParkManager pettahCarParkManager = PettahCarParkManager.getInstance();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        // getting the choice from the input
        while (true) {
            try {

                System.out.println("\nSelect your Choice : ");
                System.out.println("1. Add Vehicle");
                System.out.println("2. Delete Vehicle");
                System.out.println("3. Print the current available vehicle");
                System.out.println("4. Print statistics");
                System.out.println("5. Vehicles parked in a day.");
                System.out.println("6. Display charge for all parked cars");
                System.out.println("7. Percentage of vehicles");
                System.out.println("8. Hit '0' to Exit");
                System.out.println(">>>>");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        addVehicle();
                        break;
                    case 2:
                        deleteVehicle();
                        break;
                    case 3:
                        printVehicle();
                        break;
                    case 4:
                        printStatistics();
                        break;
                    case 5:
                        parkedByDay();
                        break;
                    case 6:
                        displayChargesForAllVehicles();
                        break;
                    case 7:
                        vehiclePercentage();
                        break;
                    case 0:
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a valid choice");
                }
            } catch (Exception e) {
                System.out.println("Oops, something went wrong:" + e.getMessage());
            }

        }
    }

    private static void vehiclePercentage() {
        pettahCarParkManager.printVehiclePercentage();
    }

    private static void parkedByDay() {
        try {
            // getting Date from the user
            System.out.println("Enter a Date to Find (DD/MM/YYYY-HH:mm:ss) Enter 0 to use current time : ");
            String checkThisTime = scanner.next();
            DateTime givenDate = new DateTime(checkThisTime);
            pettahCarParkManager.printVehicleByDay(givenDate);
        } catch (Exception e) {
            System.out.println("Something went wrong when getting vehicles parked by date:" + e.getMessage());
        }

    }

    private static void displayChargesForAllVehicles() {
        pettahCarParkManager.displayChargesForAllVehicles();
    }

    private static void printVehicle() {
        pettahCarParkManager.printCurrentVehicles();
    }

    private static void printStatistics() {
        pettahCarParkManager.printLongestPark();
        System.out.println("\n");
        pettahCarParkManager.printLatestPark();
    }

    private static void addVehicle() {
        try {
            // getting choice from the user
            System.out.println("Select your choice : ");
            System.out.println("******************");
            System.out.println("1. To add a Car.");
            System.out.println("2. To add a Motor Bike.");
            System.out.println("3. To add a Van.");
            System.out.println("4. To add a Mini Bus.");
            System.out.println("5. To add a Mini Lorry.");
            System.out.println("6. To add a Bus.");
            System.out.println("7. To add a Lorry.");
            System.out.println(">>>>");

            int choice = scanner.nextInt();

            System.out.println("Enter floor number : ");
            int floorNo = scanner.nextInt();
            VehicleType type;
            switch (choice) {
                case 1:
                    type = VehicleType.Car;
                    break;
                case 2:
                    type = VehicleType.MotorBike;
                    break;
                case 3:
                    type = VehicleType.Van;
                    break;
                case 4:
                    type = VehicleType.Minibus;
                    break;
                case 5:
                    type = VehicleType.MiniLorry;
                    break;
                case 6:
                    type = VehicleType.Bus;
                    break;
                case 7:
                    type = VehicleType.Lorry;
                    break;
                default:
                    System.out.println("Invalid vehicle type");
                    return;
            }
            Vehicle vehicle = Vehicle.createVehicle(type, scanner);
            pettahCarParkManager.addVehicle(vehicle, floorNo, type);
        } catch (Exception e) {
            System.out.println("Something went wrong when adding vehicle:" + e.getMessage());
        }
    }

    private static void deleteVehicle() {
        try {
            System.out.println("Enter the Plate ID :");
            String plateID = scanner.next();
            pettahCarParkManager.deleteVehicle(plateID);
        } catch (Exception e) {
            System.out.println("Something went wrong when removing vehicle:" + e.getMessage());
        }

    }
}
