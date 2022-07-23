
package classes.vehicles;

import java.util.Scanner;

public class Car extends Vehicle {
	private final double REQUIRED_PARKING_SPACES = 1;

	private int doors;
	private String color;

	public Car(Scanner scanner) {
		super(scanner);
		System.out.println("Enter number of Doors : ");
		int numDoors = scanner.nextInt();
		this.doors = numDoors;

		System.out.println("Enter the color of the Car : ");
		String colorString = scanner.next();
		this.color = colorString;

	}

	public Car(String idPlate, String brand, String model, int doors, String color) {
		super(idPlate, brand, model);
		this.doors = doors;
		this.color = color;
	}

	public int getDoors() {
		return doors;
	}

	public void setDoors(int doors) {
		this.doors = doors;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Override
	public double getRequiredParkingSpaces() {
		return REQUIRED_PARKING_SPACES;
	}

	@Override
	public void printSummary() {
		System.out.println("Vehicle Type: Car");
		super.printSummary();
		System.out.println();
	}

}
