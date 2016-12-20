import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class MobileTowerDriver extends TimerTask {
	String message = "Heartbeat sending from Mobile Tower";

	@Override
	public void run() {
		
		System.out.println("Sending heart beat from mobile tower: " + new Date());
		doSomeWork();

	}

	public void doSomeWork() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

public void sendHeartBeat(Timer timer1, TimerTask timertask1, DroneSensor drone) {
	// TODO Auto-generated method stub
	timer1.scheduleAtFixedRate(timertask1, 0, 1500);
	drone.receiveHeartBeat(message);
	try {
		Thread.sleep(5000);

	} catch (InterruptedException e) {

		e.printStackTrace();
	}
	timer1.cancel();
	System.out.println("Functionality implmented: closing the system");

	try {
		Thread.sleep(1000);
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
}


}
