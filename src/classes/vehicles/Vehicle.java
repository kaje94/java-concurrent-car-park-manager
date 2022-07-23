package classes.vehicles;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import classes.DateTime;
import classes.carPark.*;
import enums.VehicleType;

public abstract class Vehicle extends Object implements Comparable<Vehicle> {

	private String idPlate;
	private String brand;
	private String model;
	private DateTime entryTime;
	private Floor floor;
	private List<Space> spaces;

	public Vehicle(Scanner scanner) {
		System.out.println("Enter Plate ID :");
		String plateID = scanner.next();
		this.idPlate = plateID;

		System.out.println("Enter the Brand :");
		String brand = scanner.next();
		this.brand = brand;

		System.out.println("Enter the model :");
		String model = scanner.next();
		this.model = model;

		System.out.println(
				"Enter the date and time (DD/MM/YYYY-HH:mm:ss). Enter 0 to use current time");
		String dateTime = scanner.next();

		this.entryTime = new DateTime(dateTime);

		this.spaces = new ArrayList<>();
		this.floor = null;
	}

	public Vehicle(String idPlate, String brand, String model) {
		this.idPlate = idPlate;
		this.brand = brand;
		this.model = model;
		this.entryTime = new DateTime();
	}

	public String getIdPlate() {
		return idPlate;
	}

	public void setIdPlate(String idPlate) {
		this.idPlate = idPlate;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public void setSpaces(List<Space> spaces) {
		this.spaces = spaces;
	}

	public void setFloor(Floor floor) {
		this.floor = floor;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public DateTime getEntryDate() {
		return entryTime;
	}

	public List<Space> getSpaces() {
		return this.spaces;
	}

	public Floor getFloor() {
		return this.floor;
	}

	public void setEntryDate(DateTime entryTime) {
		this.entryTime = entryTime;
	}

	public boolean equals(Vehicle obj) {
		return obj.getIdPlate().equals(this.idPlate);
	}

	public void printSummary() {
		System.out.println("ID Plate: " + this.idPlate);
		System.out.println("Entry Time: " + this.entryTime.toString());
	}

	public VehicleType getVehicleType() {
		if (this instanceof Car) {
			return VehicleType.Car;
		} else if (this instanceof Van) {
			return VehicleType.Van;
		} else if (this instanceof MotorBike) {
			return VehicleType.MotorBike;
		} else if (this instanceof Minibus) {
			return VehicleType.Minibus;
		} else if (this instanceof MiniLorry) {
			return VehicleType.MiniLorry;
		} else if (this instanceof Bus) {
			return VehicleType.Bus;
		} else if (this instanceof Lorry) {
			return VehicleType.Lorry;
		}
		throw new Error("Invalid vehicle type");
	}

	public static Vehicle createVehicle(VehicleType type, Scanner scanner) {

		Vehicle obj = null;

		switch (type) {
			case Car:
				obj = new Car(scanner);
				break;

			case Van:
				obj = new Van(scanner);
				break;

			case MotorBike:
				obj = new MotorBike(scanner);
				break;

			case Minibus:
				obj = new Minibus(scanner);
				break;

			case MiniLorry:
				obj = new MiniLorry(scanner);
				break;

			case Bus:
				obj = new Bus(scanner);
				break;
			case Lorry:
				obj = new Lorry(scanner);
				break;
			default:
				System.out.println("Invalid Vehicle Choice");

		}
		return obj;
	}

	public abstract double getRequiredParkingSpaces();

	@Override
	public int compareTo(Vehicle o) {
		return this.entryTime.compareTo(o.entryTime);
	}

}