package classes.carPark;

import java.util.List;
import enums.VehicleType;

public class VehiclePriorityManager {
    private List<VehicleFloorPriority> vehicleFloorPriorities;

    public VehiclePriorityManager(List<VehicleFloorPriority> vehicleFloorPriorities) {
        this.vehicleFloorPriorities = vehicleFloorPriorities;
    }

    public VehicleFloorPriority getFloorsOfVehicle(VehicleType vehicleType) {
        for (VehicleFloorPriority item : vehicleFloorPriorities) {
            if (item.getVehicleType().equals(vehicleType)) {
                return item;
            }
        }
        return this.vehicleFloorPriorities.get(0);
    }
}
