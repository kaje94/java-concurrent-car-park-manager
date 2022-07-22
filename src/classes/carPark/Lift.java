package classes.carPark;

import java.util.UUID;

import classes.vehicles.Vehicle;

public class Lift {
    private String id;
    private String name;
    private Vehicle occupiedVehicle;

    public Lift(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.occupiedVehicle = null;
    }

    public String getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setOccupiedVehicle(Vehicle vehicle) {
        this.occupiedVehicle = vehicle;
    }

    public Vehicle getOccupiedVehicle() {
        return occupiedVehicle;
    }

    public boolean isOccupied() {
        return this.occupiedVehicle != null;
    }
}