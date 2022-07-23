package classes.carPark;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import classes.DateTime;
import classes.vehicles.Vehicle;

public class FloorManager {
    private List<Floor> floors;
    private List<Vehicle> allVehicles;
    HashMap<String, Integer> vehicleTypeCount = new HashMap<String, Integer>();

    public FloorManager(List<Floor> floors) {
        this.floors = floors;
        allVehicles = new ArrayList<>();
    }

    public List<Vehicle> getAllVehicles() {
        return this.allVehicles;
    }

    public List<Floor> getAllFloors() {
        return this.floors;
    }

    public void printAllVehicles() {
        Collections.sort(allVehicles, Collections.reverseOrder());
        if (this.allVehicles.size() == 0) {
            System.out.println("No Vehicles parked");
        } else {
            for (Vehicle vehicle : this.allVehicles) {
                vehicle.printSummary();
                System.out.println();
            }
        }
    }

    public List<Space> findAvailableSpaces(Vehicle vehicle, int floorNo) {
        try {
            isVehicleExistsInFloor(vehicle, floorNo);

            Floor floor = findFloorByNo(floorNo);
            List<Space> freeSpaces = floor.getFreeSpace(vehicle.getRequiredParkingSpaces());
            return freeSpaces;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ArrayList<Space>();
        }

    }

    public void addVehicle(Vehicle vehicle, int floorNo, List<Space> spaces) {
        try {
            isVehicleExistsInFloor(vehicle, floorNo);

            Floor floor = findFloorByNo(floorNo);

            if (!floor.getAllowedVehicleTypes().contains(vehicle.getVehicleType())) {
                System.out.println(vehicle.getVehicleType() + " not allowed in " + floor.getFloorName());
                return;
            }

            allVehicles.add(vehicle);
            floor.addVehicle(vehicle, spaces);

            String vehicleType = vehicle.getClass().getSimpleName();
            vehicleTypeCount.put(vehicleType, vehicleTypeCount.getOrDefault(vehicleType, 0).intValue() + 1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public Floor findFloorByNo(int floorNo) {
        for (Floor floorItem : this.floors) {
            if (floorItem.getFloorNo() == floorNo) {
                return floorItem;
            }
        }
        System.out.println("Floor not found, Falling back to " + this.floors.get(0).getFloorName());
        return this.floors.get(0);
    }

    public void isVehicleExistsInFloor(Vehicle vehicle, int floorNo) {
        for (Vehicle item : allVehicles) {
            if (item.equals(vehicle)) {
                throw new Error("This vehicle is already parked.");
            }
        }
    }

    public Vehicle findVehicleByPlate(String idPlate) {
        for (Vehicle item : allVehicles) {
            if (item.getIdPlate().equalsIgnoreCase(idPlate)) {
                return item;
            }
        }
        throw new Error("Vehicle not found");
    }

    public void removeVehicle(String idPlate) {
        try {
            Vehicle vehicle = findVehicleByPlate(idPlate);
            Floor vehicleFloor = vehicle.getFloor();

            System.out.println("Removing " + vehicle.getVehicleType() + " from floor " + vehicleFloor.getFloorName());
            vehicleFloor.getVehicles().remove(vehicle);
            allVehicles.remove(vehicle);
            for (Space space : vehicle.getSpaces()) {
                System.out.println("space : " + space.getId());
                space.removeVehicle(vehicle);
            }

            String vehicleType = vehicle.getClass().getSimpleName();
            vehicleTypeCount.put(vehicleType, vehicleTypeCount.getOrDefault(vehicleType, 1).intValue() - 1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void printVehiclePercentage() {
        if (this.allVehicles.size() == 0) {
            System.out.println("No Vehicles parked");
        } else {
            for (String vehicleType : vehicleTypeCount.keySet()) {
                if (vehicleTypeCount.getOrDefault(vehicleType, 0).intValue() != 0) {
                    double percentage = (vehicleTypeCount.get(vehicleType).intValue() / allVehicles.size()) * 100;
                    System.out.println(vehicleType + " Percentage is : " + percentage);
                }
            }
        }
    }

    public void printLongestPark() {
        if (this.allVehicles.size() == 0) {
            System.out.println("No Vehicles parked");
        } else {
            Collections.sort(allVehicles);
            Vehicle longestParkedVehicle = allVehicles.get(0);
            System.out.println("The longest parked vehicle is : ");
            longestParkedVehicle.printSummary();
        }
    }

    public void printLatestPark() {
        if (this.allVehicles.size() == 0) {
            System.out.println("No Vehicles parked");
        } else {
            Collections.sort(allVehicles, Collections.reverseOrder());
            Vehicle lastParkedVehicle = allVehicles.get(0);

            System.out.println("The latest parked vehicle is : ");
            lastParkedVehicle.printSummary();
        }
    }

    public void printVehicleByDay(DateTime givenDate) {
        List<Vehicle> parkedVehicles = new ArrayList<>();
        for (Vehicle item : allVehicles) {
            if (DateTime.isSameDate(item.getEntryDate(), givenDate)) {
                parkedVehicles.add(item);
            }
        }

        if (parkedVehicles.size() == 0) {
            System.out.println("No Vehicles parked on given date");
        } else {
            for (Vehicle item : parkedVehicles) {
                item.printSummary();
            }
        }
    }

    public void printAllAvailableSpaces() {
        for (Floor floor : floors) {
            double space = floor.getAllFreeSpaces();
            System.out.println(floor.getFloorName() + " has " + space + " spaces");
        }
    }
}
