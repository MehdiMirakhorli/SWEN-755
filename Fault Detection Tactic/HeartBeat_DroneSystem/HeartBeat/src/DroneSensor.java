import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class DroneSensor  {
	String message;
	

	public void receiveHeartBeat(String message) {
		this.message = message;
		// TODO Auto-generated method stub
		System.out.println("Drone sensor received message: " + message + " : "
				+ new Date());
	}

	public boolean checkHeartBeat() {
		// TODO Auto-generated method stub
		return false;
	}

	
}
