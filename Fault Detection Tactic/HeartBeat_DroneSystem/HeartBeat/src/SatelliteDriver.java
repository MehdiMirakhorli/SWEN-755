import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class SatelliteDriver extends TimerTask {
	String message = "Heartbeat sending from Satellite";
	String errorMsg = "Unable to send heartbeat";



	@Override
	public void run() {
		
		System.out.println("Satellite is primary source of communication");
		System.out.println("Sending heart beat from satellite: " + new Date());
		
		doSomeWork();

	}

	public void doSomeWork() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void sendHeartBeat(Timer timer, TimerTask timertask,
			DroneSensor drone) {

		timer.scheduleAtFixedRate(timertask, 0, 1500);
		drone.receiveHeartBeat(message);
		try {
			Thread.sleep(5000);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		System.out.println("Satellite software failure	");
		timer.cancel();

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
