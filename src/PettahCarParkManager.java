
import classes.*;
import classes.carPark.Floor;
import classes.carPark.InGate;
import classes.carPark.FloorManager;
// import classes.carPark.Lift;
import classes.carPark.OutGate;
import classes.carPark.Space;
import classes.carPark.VehicleFloorPriority;
import classes.carPark.VehiclePriorityManager;
import classes.vehicles.Vehicle;
import enums.VehicleType;
import interfaces.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PettahCarParkManager implements CarParkManager {

	Lock lock = new ReentrantLock(true);
	Condition bufferFull = lock.newCondition();
	Condition bufferEmpty = lock.newCondition();

	private final int spacesPerFloor = 60;

	private final List<VehicleType> groundFloorFloorVehicleTypes = Arrays.asList(VehicleType.Car, VehicleType.Van,
			VehicleType.Minibus, VehicleType.MiniLorry, VehicleType.Bus, VehicleType.Lorry, VehicleType.MotorBike);

	private final List<VehicleType> firstToSixthFloorVehicleTypes = Arrays.asList(VehicleType.Car, VehicleType.Van,
			VehicleType.MotorBike);

	private final List<VehicleType> seventhFloorOnwardsVehicleTypes = Arrays.asList(VehicleType.Car, VehicleType.Van,
			VehicleType.MotorBike);

	// Lifts available to floors 6, 7 & 7
	// private Lift eastLift1 = new Lift("East Lift 1");
	// private Lift eastLift2 = new Lift("East Lift 2");
	// private Lift westLift1 = new Lift("West Lift 1");
	// private Lift westLift2 = new Lift("West Lift 2");

	// There are two entries located in northern gates and two exits located in
	// southern gates
	// of the ground floor for vehicles to enter and exit the car park.
	private static Runnable northernInGate1;
	private static Runnable northernInGate2;
	private static Runnable southerOutGate1;
	private static Runnable southerOutGate2;

	private static PriorityBlockingQueue<Vehicle> northernInGate1VehicleQueue = new PriorityBlockingQueue<Vehicle>(20);
	private static PriorityBlockingQueue<Vehicle> northernInGate2VehicleQueue = new PriorityBlockingQueue<Vehicle>(20);

	private static Queue<Vehicle> southernOutGate1VehicleQueue = new PriorityQueue<Vehicle>();
	private static Queue<Vehicle> southernOutGate2VehicleQueue = new PriorityQueue<Vehicle>();

	// 1st and 2nd floor is accessible using 2 entries in the west and 2 exits
	// through east gates
	// 3rd to 6th floor is accessible using 1 entry in the west and 1 exit through
	// the east gates
	// Since northern gate is connected to main road, it is given highest priority
	// to enter the car park

	// If lots are available only, lift will let you to enter to get into upper
	// floor; otherwise, will be blocked until a lot becomes available

	private Floor groundFloor = new Floor(0, "Ground floor", groundFloorFloorVehicleTypes,
			Space.generateSpaces(spacesPerFloor));
	private Floor firstFloor = new Floor(1, "First floor", firstToSixthFloorVehicleTypes,
			Space.generateSpaces(spacesPerFloor));
	private Floor secondFloor = new Floor(2, "Second floor", firstToSixthFloorVehicleTypes,
			Space.generateSpaces(spacesPerFloor));
	private Floor thirdFloor = new Floor(3, "Third floor", firstToSixthFloorVehicleTypes,
			Space.generateSpaces(spacesPerFloor));
	private Floor forthFloor = new Floor(4, "Forth floor", firstToSixthFloorVehicleTypes,
			Space.generateSpaces(spacesPerFloor));
	private Floor fifthFloor = new Floor(5, "Fifth floor", firstToSixthFloorVehicleTypes,
			Space.generateSpaces(spacesPerFloor));
	private Floor sixthFloor = new Floor(6, "Sixth floor", firstToSixthFloorVehicleTypes,
			Space.generateSpaces(spacesPerFloor));
	private Floor seventhFloor = new Floor(7, "Seventh floor", seventhFloorOnwardsVehicleTypes,
			Space.generateSpaces(spacesPerFloor));
	private Floor eighthFloor = new Floor(8, "Eighth floor", seventhFloorOnwardsVehicleTypes,
			Space.generateSpaces(spacesPerFloor));

	private VehiclePriorityManager vehiclePriorityManager = new VehiclePriorityManager(Arrays
			.asList(new VehicleFloorPriority(VehicleType.Car, Arrays.asList(firstFloor, secondFloor, thirdFloor,
					forthFloor, fifthFloor, sixthFloor, seventhFloor, eighthFloor, groundFloor)),
					new VehicleFloorPriority(VehicleType.Van, Arrays.asList(firstFloor, secondFloor, thirdFloor,
							forthFloor, fifthFloor, sixthFloor, seventhFloor, eighthFloor, groundFloor)),
					new VehicleFloorPriority(VehicleType.MotorBike,
							Arrays.asList(groundFloor, firstFloor, secondFloor, thirdFloor,
									forthFloor, fifthFloor, sixthFloor, seventhFloor, eighthFloor)),
					new VehicleFloorPriority(VehicleType.Bus,
							Arrays.asList(groundFloor)),
					new VehicleFloorPriority(VehicleType.Minibus,
							Arrays.asList(groundFloor)),
					new VehicleFloorPriority(VehicleType.Lorry,
							Arrays.asList(groundFloor)),
					new VehicleFloorPriority(VehicleType.MiniLorry,
							Arrays.asList(groundFloor))));

	private FloorManager floorManager = new FloorManager(Arrays.asList(groundFloor, firstFloor, secondFloor, thirdFloor,
			forthFloor, fifthFloor, sixthFloor, seventhFloor, eighthFloor));

	private static PettahCarParkManager instance = null;

	private PettahCarParkManager() {
		northernInGate1 = new InGate(this, northernInGate1VehicleQueue);
		northernInGate2 = new InGate(this, northernInGate2VehicleQueue);
		southerOutGate1 = new OutGate(this, southernOutGate1VehicleQueue);
		southerOutGate2 = new OutGate(this, southernOutGate2VehicleQueue);

		Thread northernEntrance1Thread = new Thread(northernInGate1, "Northern Entrance 1 thread");
		Thread northernEntrance2Thread = new Thread(northernInGate2, "Northern Entrance 2 thread");
		Thread southernExit1Thread = new Thread(southerOutGate1, "Souther Exit 1 thread");
		Thread southernExit2Thread = new Thread(southerOutGate2, "Souther Exit 2 thread");

		northernEntrance1Thread.setPriority(Thread.MAX_PRIORITY);

		northernEntrance1Thread.start();
		northernEntrance2Thread.start();
		southernExit1Thread.start();
		southernExit2Thread.start();
	}

	// method which returns an object of same type
	public static PettahCarParkManager getInstance() {
		if (instance == null) {
			synchronized (PettahCarParkManager.class) {
				if (instance == null) {
					instance = new PettahCarParkManager();
				}
			}
		}
		return instance;
	}

	@Override
	public void printCurrentVehicles() {
		floorManager.printAllVehicles();
	}

	/**
	 * The last vehicle that was parked, show the ID plate, the type and the entry
	 * time and date
	 */
	@Override
	public void printLongestPark() {
		floorManager.printLongestPark();
	}

	/**
	 * The vehicle that is parked for the longest time
	 * show the ID plate, the type and the entry time and date
	 */
	@Override
	public void printLatestPark() {
		floorManager.printLatestPark();
	}

	/**
	 * Print the list of vehicles, which enter in the parking on a specified day
	 * The user must enter the day, month and year from the console and the list of
	 * The vehicles that entered that day will be printed.
	 * A message will notify if no vehicles were parked on that day.
	 */
	@Override
	public void printVehicleByDay(DateTime givenDate) {
		floorManager.printVehicleByDay(givenDate);
	}

	/** The percentage of different types of vehicle currently parked. */
	@Override
	public void printVehiclePercentage() {
		floorManager.printVehiclePercentage();
	}

	/**
	 * The car park charges LKR 50 per space per hour for the first three hours.
	 * The car park charges an additional LKR 75 per hour per space after the first
	 * three hours
	 * The maximum charge for any 24 hours period is LKR 1200.
	 * Display on the screen the parking charges for each customer who parked in the
	 * car park
	 */
	@Override
	public void displayChargesForAllVehicles() {

		List<Vehicle> allVehicles = floorManager.getAllVehicles();
		if (allVehicles.size() == 0) {
			System.out.println("No Vehicles parked");
		} else {
			for (Vehicle item : allVehicles) {
				item.printSummary();

				DateTime entryDateTime = item.getEntryDate();
				int differenceInSeconds = new DateTime().compareTo(entryDateTime);
				int differenceInHours = (int) Math.ceil(differenceInSeconds / (60.0 * 60.0));

				double dayCharge = 0;
				double hourCharge = 0;
				double totalCost = 0;

				int days = differenceInHours / 24;
				int hours = differenceInHours % 24;

				if (days > 0) {
					dayCharge = days * 1200;
				}
				if (hours > 0) {
					if (hours <= 3) {
						hourCharge = hours * 50;
					} else {
						hourCharge = (3 * 50) + ((hours - 3) * (50 + 75));
					}

					if (hourCharge > 1200) {
						hourCharge = 1200;
					}
				}
				totalCost = hourCharge + dayCharge;
				System.out.printf("Total charge for the vehicle is LKR " + totalCost);
				System.out.println();
			}
		}
	}

	private Floor getVehicleParkFloor(Vehicle vehicle) {
		List<Floor> floors = vehiclePriorityManager.getFloorsOfVehicle(vehicle.getVehicleType()).getFloors();
		List<Space> spaces = new ArrayList<>();
		if (vehicle.getVehicleType() == VehicleType.MotorBike) {
			for (Floor floor : floors) {
				spaces = floor.getAllocatedSpaces(vehicle.getRequiredParkingSpaces());
				if (spaces.size() > 0) {
					return floor;
				}
			}
		}

		for (Floor floor : floors) {
			List<Space> freeSpaces = floorManager.findAvailableSpaces(vehicle, floor.getFloorNo());
			if (freeSpaces.size() >= 1) {
				return floor;
			}
		}

		throw new Error("No space left");
	}

	private List<Space> getVehicleParkSpace(Vehicle vehicle) {
		List<Floor> floors = vehiclePriorityManager.getFloorsOfVehicle(vehicle.getVehicleType()).getFloors();
		List<Space> spaces = new ArrayList<>();
		if (vehicle.getVehicleType() == VehicleType.MotorBike) {
			for (Floor floor : floors) {
				spaces = floor.getAllocatedSpaces(vehicle.getRequiredParkingSpaces());
				if (spaces.size() > 0) {
					return spaces;
				}
			}
		}

		for (Floor floor : floors) {
			List<Space> freeSpaces = floorManager.findAvailableSpaces(vehicle, floor.getFloorNo());
			if (freeSpaces.size() >= 1) {
				return freeSpaces;
			}
		}

		throw new Error("No space left");
	}

	@Override
	public Vehicle getVehicleById(String idPlate) {
		return floorManager.findVehicleByPlate(idPlate);
	}

	public static void killThreadStates() {
		try {
			((InGate) northernInGate1).KillThreadStatus();
			((InGate) northernInGate2).KillThreadStatus();
			((OutGate) southerOutGate1).KillThreadStatus();
			((OutGate) southerOutGate2).KillThreadStatus();
		} catch (Exception e) {

		}

	}

	private boolean isParkSpaceAvailable(Vehicle vehicle) {
		List<Floor> floors = vehiclePriorityManager.getFloorsOfVehicle(vehicle.getVehicleType()).getFloors();
		List<Space> spaces = new ArrayList<>();
		if (vehicle.getVehicleType() == VehicleType.MotorBike) {
			for (Floor floor : floors) {
				spaces = floor.getAllocatedSpaces(vehicle.getRequiredParkingSpaces());
				if (spaces.size() > 0) {
					return true;
				}
			}
		}

		for (Floor floor : floors) {
			List<Space> freeSpaces = floorManager.findAvailableSpaces(vehicle, floor.getFloorNo());
			if (freeSpaces.size() >= 1) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void addVehicleToInGateQueue(Vehicle vehicle) {
		if (northernInGate1VehicleQueue.size() < northernInGate2VehicleQueue.size()) {
			northernInGate1VehicleQueue.add(vehicle);
		} else {
			northernInGate2VehicleQueue.add(vehicle);
		}
	}

	@Override
	public void addVehicleToOutGateQueue(Vehicle vehicle) {
		if (southernOutGate1VehicleQueue.size() < southernOutGate2VehicleQueue.size()) {
			southernOutGate1VehicleQueue.add(vehicle);
		} else {
			southernOutGate2VehicleQueue.add(vehicle);
		}
	}

	@Override
	public void admitVehicleToPark(Vehicle obj) {
		lock.lock();
		try {

			while (!isParkSpaceAvailable(obj)) {
				try {
					System.out.println("There are no slots available");
					// We await as we cannot move forwards
					// avoids spurious wake ups
					bufferFull.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			List<Space> spaces = getVehicleParkSpace(obj);
			Floor floor = getVehicleParkFloor(obj);
			floorManager.addVehicle(obj, floor.getFloorNo(), spaces);
			obj.setSpaces(spaces);
			obj.setFloor(floor);

			floorManager.printAllAvailableSpaces();

			bufferEmpty.signalAll();
		} finally {
			lock.unlock();
		}
	}

	@Override
	public void removeVehicleFromPark(Vehicle obj) {
		try {
			lock.lock();

			while (floorManager.getAllVehicles().size() == 0) {
				try {
					System.out.println("There are no vehicles in the car park");
					// We await as we cannot move forwards
					// avoids spurious wake ups
					bufferEmpty.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			floorManager.removeVehicle(obj.getIdPlate());

			floorManager.printAllAvailableSpaces();

			// wake all threads that are waiting for this condition
			bufferFull.signalAll();
		} finally {
			lock.unlock();
		}
	}
}
