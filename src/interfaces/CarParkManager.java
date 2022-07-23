package interfaces;

import classes.*;
import classes.vehicles.Vehicle;

public interface CarParkManager {

	public void printCurrentVehicles();

	public void printVehiclePercentage();

	public void printLongestPark();

	public void printLatestPark();

	public void printVehicleByDay(DateTime entryTime);

	public void displayChargesForAllVehicles();

	public void admitVehicleToPark(Vehicle vehicle);

	public void addVehicleToInGateQueue(Vehicle vehicle);

	public void removeVehicleFromPark(Vehicle vehicle);

	public void addVehicleToOutGateQueue(Vehicle vehicle);

	public Vehicle getVehicleById(String vehicleId);
}
