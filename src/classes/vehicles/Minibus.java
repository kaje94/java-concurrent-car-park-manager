package classes.vehicles;

import java.util.Scanner;

public class Minibus extends Vehicle {
	private final double REQUIRED_PARKING_SPACES = 3;

	private String seatingCapacity;

	public Minibus(Scanner scanner) {
		super(scanner);
		System.out.println("Enter the seating capacity size : ");
		String seatingCapacity = scanner.next();
		this.seatingCapacity = seatingCapacity;
	}

	public Minibus(String idPlate, String brand, String model, String seatingCapacity) {
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
	public String toString() {
		return "Vehicle Type: Mini bus\n" + super.toString() + "\nseatingCapacity:" + this.seatingCapacity;
	}

	@Override
	public void printSummary() {
		System.out.println("Vehicle Type: Mini Bus");
		super.printSummary();
		System.out.println();
	}
}
