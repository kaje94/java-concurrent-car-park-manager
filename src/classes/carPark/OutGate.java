package classes.carPark;

import java.util.Queue;

import classes.vehicles.Vehicle;
import interfaces.CarParkManager;

public class OutGate implements Runnable {

	private CarParkManager carParkManager;
	private Queue<Vehicle> vehicles;
	private boolean isThreadRunning = true;

	public OutGate(CarParkManager carParkManager, Queue<Vehicle> vehicles) {
		super();
		this.carParkManager = carParkManager;
		this.vehicles = vehicles;
	}

	public void KillThreadStatus() {
		this.isThreadRunning = false;
	}

	@Override
	public void run() {
		while (isThreadRunning) {
			// wait
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (vehicles != null && !vehicles.isEmpty()) {
				System.out.print("OutGate Running in " + Thread.currentThread().getName() + "\n");
				Vehicle item = vehicles.poll();
				carParkManager.removeVehicleFromPark(item);
			}
		}
	}

}
