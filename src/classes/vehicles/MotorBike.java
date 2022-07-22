package classes.vehicles;

import java.util.Scanner;

public class MotorBike extends Vehicle {
	private final double REQUIRED_PARKING_SPACES = 1 / 3;

	private String engineSize;

	public MotorBike(Scanner scanner) {
		super(scanner);
		System.out.println("Enter the Engine Size : ");
		String engineSize = scanner.next();
		this.engineSize = engineSize;
	}

	public MotorBike(String idPlate, String brand, String model, String engineSize) {
		super(idPlate, brand, model);
		this.engineSize = engineSize;
	}

	public String getEngineSize() {
		return engineSize;
	}

	public void setEngineSize(String engineSize) {
		this.engineSize = engineSize;
	}

	@Override
	public double getRequiredParkingSpaces() {
		return REQUIRED_PARKING_SPACES;
	}

	@Override
	public String toString() {
		return "Vehicle Type: MotorBike\n" + super.toString() + "\nengineSize:" + this.engineSize;
	}

	@Override
	public void printSummary() {
		System.out.println("Vehicle Type: Motor Bike");
		super.printSummary();
		System.out.println();
	}
}
