package classes.vehicles;

import java.util.Scanner;

public class Van extends Vehicle {
	private final double REQUIRED_PARKING_SPACES = 2;

	// Properties
	private double cargoVolume;

	// Constructor
	public Van(Scanner scanner) {
		super(scanner);
		System.out.println("Enter the Cargo Capacity : ");
		double cargoCapacity = scanner.nextDouble();
		this.cargoVolume = cargoCapacity;
	}

	public Van(String idPlate, String brand, String model, double cargoVolume) {
		super(idPlate, brand, model);
		this.cargoVolume = cargoVolume;
	}

	public double getCargoVolume() {
		return cargoVolume;
	}

	public void setCargoVolume(double cargoVolume) {
		this.cargoVolume = cargoVolume;
	}

	@Override
	public double getRequiredParkingSpaces() {
		return REQUIRED_PARKING_SPACES;
	}

	@Override
	public String toString() {
		return "Vehicle Type: Van\n" + super.toString() + "\ncargoVolume:" + this.cargoVolume;
	}

	@Override
	public void printSummary() {
		System.out.println("Vehicle Type: Van");
		super.printSummary();
		System.out.println();
	}
}
