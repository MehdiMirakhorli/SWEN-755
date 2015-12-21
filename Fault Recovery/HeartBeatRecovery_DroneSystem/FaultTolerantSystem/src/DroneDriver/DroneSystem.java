package DroneDriver;
import Drone.Drone;
import Networks.MobileTower;
import Networks.SatelliteTower;

public class DroneSystem {

	public static void main(String[] args) {
		Drone drone = new Drone();
	
		
		SatelliteTower satellite = new SatelliteTower(drone);		
		new MobileTower(drone,satellite);
	}

}
