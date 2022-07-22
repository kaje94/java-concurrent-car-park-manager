package classes.carPark;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import classes.vehicles.Vehicle;

public class Space {
    private String id;
    private double occupiedPercentage;
    private List<Vehicle> occupiedVehicles;

    public Space() {
        this.id = UUID.randomUUID().toString();
        this.occupiedPercentage = 0;
        this.occupiedVehicles = new ArrayList<>();
    }

    public String getId() {
        return this.id;
    }

    public void setOccupiedPercentage(double percentage) {
        this.occupiedPercentage = percentage;
    }

    public double getOccupiedPercentage() {
        return occupiedPercentage;
    }

    public double getRemainingPercentage() {
        return 1 - occupiedPercentage;
    }

    public boolean isOccupied() {
        return occupiedPercentage >= 0.95;
    }

    public void addVehicle(Vehicle vehicle) {
        if (vehicle.getRequiredParkingSpaces() >= 1 && occupiedVehicles.size() == 0) {
            setOccupiedPercentage(1);
            occupiedVehicles.add(vehicle);
        } else if (vehicle.getRequiredParkingSpaces() < 1
                && getRemainingPercentage() >= vehicle.getRequiredParkingSpaces()) {
            setOccupiedPercentage(this.getOccupiedPercentage() + vehicle.getRequiredParkingSpaces());
            occupiedVehicles.add(vehicle);
        } else {
            System.out.println("No space in " + this.id);
        }
    }

    public void removeVehicle(Vehicle vehicle) {
        boolean removed = this.occupiedVehicles.remove(vehicle);
        if (removed == true) {
            if (vehicle.getRequiredParkingSpaces() >= 1) {
                setOccupiedPercentage(0);
            } else {
                setOccupiedPercentage(getOccupiedPercentage() - vehicle.getRequiredParkingSpaces());
            }
        }
    }

    public static List<Space> generateSpaces(int count) {
        List<Space> spaces = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            spaces.add(new Space());
        }
        return spaces;
    }
}