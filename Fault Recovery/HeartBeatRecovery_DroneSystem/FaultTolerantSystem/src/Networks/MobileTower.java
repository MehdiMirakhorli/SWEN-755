package Networks;

import java.util.Date;

import Drone.Drone;

public class MobileTower extends Thread {
	String name;
	boolean primary;
	Drone drone;
	static int count=0;
	SatelliteTower satellite;
	
	
	public MobileTower(Drone drone, SatelliteTower satellite){
		this.start();
		this.name = "Mobile Tower";
		this.primary = false;
		this.drone = drone;
		this.satellite = satellite;

	}
	
	public void run() {
		
		for(int i =0;i<=3;i++){
			try {
				sendHeartBeat(drone);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		try {
			sendHeartBeat(drone);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		}
		
	}
	@SuppressWarnings("deprecation")
	public void sendHeartBeat(Drone drone) throws InterruptedException {
		boolean primary = true;
		count++;
		System.out.println("Mobile tower sends heart beat (count " +count+")"+" at " + new Date());
		String msg = drone.receiveHeartBeat(primary,count,name);
		System.out.println(msg);
		if(count>3){
			System.out.println("Software failure or issue in mobile tower");
			System.out.println("Till Mobile tower recovers from failure, make satellite to be primary source of communication----");
			satellite.notifyTowerOfFailure(primary);
			this.stop();		
		}
		
	}

	
//	public String getName() {
//		return name;
//	}
	
}


