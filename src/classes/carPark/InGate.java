package classes.carPark;

import java.util.concurrent.PriorityBlockingQueue;

import classes.vehicles.Vehicle;
import interfaces.CarParkManager;

public class InGate implements Runnable {

	private CarParkManager carParkManager;
	private PriorityBlockingQueue<Vehicle> vehicleQueue;
	private boolean isThreadRunning = true;

	public InGate(CarParkManager carParkManager, PriorityBlockingQueue<Vehicle> vehicleQueue) {
		super();
		this.carParkManager = carParkManager;
		this.vehicleQueue = vehicleQueue;
	}

	public void KillThreadStatus() {
		this.isThreadRunning = false;
	}

	@Override
	public void run() {
		while (isThreadRunning) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (!vehicleQueue.isEmpty()) {
				System.out.print("InGate Running in " + Thread.currentThread().getName() + "\n");
				Vehicle item = vehicleQueue.poll();

				if (item != null) {
					{
						System.out.println(
								"\n" + item.getVehicleType() + " " + item.getIdPlate() + " entered the car park\n");
						carParkManager.admitVehicleToPark(item);

					}
				}
			}
		}
	}

}
