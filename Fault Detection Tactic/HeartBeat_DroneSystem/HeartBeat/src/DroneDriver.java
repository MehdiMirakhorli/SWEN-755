import java.util.Timer;
import java.util.TimerTask;

public class DroneDriver {

	public static void main(String[] args) {
		DroneSensor drone = new DroneSensor();
		boolean HeartBeat = true;
		TimerTask timertask = new SatelliteDriver();
		Timer timer = new Timer(true);

		
		TimerTask timertask1 = new MobileTowerDriver();
		Timer timer1 = new Timer(true);

		SatelliteDriver satellite = new SatelliteDriver();
		satellite.sendHeartBeat(timer, timertask, drone);
		
		MobileTowerDriver mobileTower = new MobileTowerDriver();
		mobileTower.sendHeartBeat(timer1, timertask1, drone);

	

		if (HeartBeat == false) {
			System.out
					.println("Mobile tower is primary source of communication");
		}
	}
}
