package classes.vehicles;

import java.util.Scanner;

public class Lorry extends Vehicle {
	private final double REQUIRED_PARKING_SPACES = 5;

	private String cargoType;

	public Lorry(Scanner scanner) {
		super(scanner);
		System.out.println("Enter the seating cargo type : ");
		String cargoType = scanner.next();
		this.cargoType = cargoType;
	}

	public Lorry(String idPlate, String brand, String model, String cargoType) {
		super(idPlate, brand, model);
		this.cargoType = cargoType;
	}

	public String getCargoType() {
		return cargoType;
	}

	public void setCargoType(String cargoType) {
		this.cargoType = cargoType;
	}

	@Override
	public double getRequiredParkingSpaces() {
		return REQUIRED_PARKING_SPACES;
	}

	@Override
	public void printSummary() {
		System.out.println("Vehicle Type: Lorry");
		super.printSummary();
		System.out.println();
	}
}
