package classes.vehicles;

import java.util.Scanner;

public class Bus extends Vehicle {
	private final double REQUIRED_PARKING_SPACES = 5;

	private String seatingCapacity;

	public Bus(Scanner scanner) {
		super(scanner);
		System.out.println("Enter the seating capacity size : ");
		String seatingCapacity = scanner.next();
		this.seatingCapacity = seatingCapacity;
	}

	public Bus(String idPlate, String brand, String model, String seatingCapacity) {
		super(idPlate, brand, model);
		this.seatingCapacity = seatingCapacity;
	}

	public String getSeatingCapacity() {
		return seatingCapacity;
	}

	public void setSeatingCapacity(String seatingCapacity) {
		this.seatingCapacity = seatingCapacity;
	}

	@Override
	public double getRequiredParkingSpaces() {
		return REQUIRED_PARKING_SPACES;
	}

	@Override
	public void printSummary() {
		System.out.println("Vehicle Type: Bus");
		super.printSummary();
		System.out.println();
	}
}
