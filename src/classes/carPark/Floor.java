package classes.carPark;

import java.util.ArrayList;
import java.util.List;
import classes.vehicles.Vehicle;
import enums.VehicleType;

public class Floor {
    private int floorNo;
    private String floorName;
    private List<VehicleType> allowedVehicleTypes;
    private List<Space> spaces;
    private List<Vehicle> vehicles;

    public Floor(int floorNo, String floorName, List<VehicleType> allowedVehicleTypes, List<Space> spaces) {
        this.floorNo = floorNo;
        this.floorName = floorName;
        this.allowedVehicleTypes = allowedVehicleTypes;
        this.spaces = spaces;
        this.vehicles = new ArrayList<Vehicle>();
    }

    public void setFloorNo(int floorNo) {
        this.floorNo = floorNo;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public void setAllowedVehicleTypes(List<VehicleType> allowedVehicleTypes) {
        this.allowedVehicleTypes = allowedVehicleTypes;
    }

    public void setSpaces(List<Space> spaces) {
        this.spaces = spaces;
    }

    public void addVehicle(Vehicle vehicle, List<Space> spaces) {
        this.vehicles.add(vehicle);
        System.out.println("Adding " + vehicle.getVehicleType() + " to floor " + this.getFloorName());
        for (Space space : spaces) {
            System.out.println("Space :" + space.getId());
            space.addVehicle(vehicle);
        }
        System.out.println();
    }

    public int getFloorNo() {
        return this.floorNo;
    }

    public String getFloorName() {
        return this.floorName;
    }

    public List<VehicleType> getAllowedVehicleTypes() {
        return this.allowedVehicleTypes;
    }

    public List<Space> getSpaces() {
        return this.spaces;
    }

    public List<Vehicle> getVehicles() {
        return this.vehicles;
    }

    public List<Space> getFreeSpace(double spaceSize) {
        List<Space> freeSpaces = new ArrayList<>();

        for (int i = 0; i < this.spaces.size(); i++) {
            try {
                if (spaceSize < 1 && this.spaces.get(i).getRemainingPercentage() >= spaceSize) {
                    freeSpaces.add(this.spaces.get(i));
                    return freeSpaces;
                } else {
                    for (int j = i; j < (i + spaceSize); j++) {
                        if (this.spaces.get(j).getRemainingPercentage() == 1.0) {
                            freeSpaces.add(this.spaces.get(j));
                        }
                    }
                    if (freeSpaces.size() == spaceSize) {
                        return freeSpaces;
                    } else {
                        freeSpaces.clear();
                    }
                }
            } catch (IndexOutOfBoundsException e) {
            }
        }
        return freeSpaces;
    }

    public List<Space> getAllocatedSpaces(double spaceSize) {
        List<Space> freeSpaces = new ArrayList<>();
        for (int i = 0; i < this.spaces.size(); i++) {
            try {
                if (this.spaces.get(i).getOccupiedPercentage() > 0
                        && this.spaces.get(i).getRemainingPercentage() >= spaceSize) {
                    freeSpaces.add(this.spaces.get(i));
                    return freeSpaces;
                }
            } catch (IndexOutOfBoundsException e) {
            }
        }
        return freeSpaces;
    }

    public double getAllFreeSpaces() {
        double freeSpaces = 0;
        for (int i = 0; i < this.spaces.size(); i++) {
            if (!this.spaces.get(i).isOccupied()) {
                freeSpaces = freeSpaces + (this.spaces.get(i).getRemainingPercentage() * 1);
            }
        }
        return freeSpaces;
    }
}