package classes.carPark;

import java.util.List;
import enums.VehicleType;

public class VehicleFloorPriority {
    private VehicleType vehicleType;
    private List<Floor> floors;

    public VehicleFloorPriority(VehicleType vehicleType, List<Floor> floors) {
        this.vehicleType = vehicleType;
        this.floors = floors;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public void setFloors(List<Floor> floors) {
        this.floors = floors;
    }

    public VehicleType getVehicleType() {
        return this.vehicleType;
    }

    public List<Floor> getFloors() {
        return this.floors;
    }
}
