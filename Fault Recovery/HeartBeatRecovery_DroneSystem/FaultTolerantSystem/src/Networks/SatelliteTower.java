package Networks;

import java.util.Date;

import Drone.Drone;

public class SatelliteTower extends Thread {
	String name;
	boolean primary;
	Drone drone;
	static int count = 0;

	public SatelliteTower(Drone drone) {
		this.start();
		this.name = "Satellite";
		this.primary = false;
		this.drone = drone;
	}

	public void run() {
		for (int i = 0; i <= 5; i++) {
			sendHeartBeat(drone);
			try {
				Thread.sleep(3000);
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			sendHeartBeat(drone);
		}
	}

	private void sendHeartBeat(Drone drone2) {
		boolean primary = false;
		count++;
		System.out.println("Satellite sends heart beat (count " + count +")"+ " at "
				+ new Date());
		String msg = drone.receiveHeartBeat(primary, count,name);
		System.out.println(msg);
		
	}
	
	@SuppressWarnings("deprecation")
	public void notifyTowerOfFailure(Boolean primary) {
		System.out.println("Satellite is primary source of communication");
		System.out.println("Functionality Implemented successfully...Exiting program");

		this.stop();

	}
	

}
