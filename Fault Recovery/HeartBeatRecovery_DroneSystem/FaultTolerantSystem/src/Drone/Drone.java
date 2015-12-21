package Drone;

import java.util.Date;

public class Drone {
	String msg;
	String name;
	public String receiveHeartBeat(boolean primary, int count, String name) {
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		msg = "Heart beat: "+" count: "+count+" received from "+name+ " at: "+new Date();
		return msg;
	}
	

}
