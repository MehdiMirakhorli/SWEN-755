import java.net.DatagramPacket;
import java.util.concurrent.ThreadLocalRandom;

class BackupProcess implements ProcessInput {

	ObstacleDetectModel obsModel;
	boolean isRunning = true;

	BackupProcess() {
		obsModel = new ObstacleDetectModel();
	}

	@Override
	public String ProcessInput(DatagramPacket packet) {

		String packetMessage = new String(packet.getData());
		String message = packetMessage.substring(packetMessage.indexOf(':') + 1, packetMessage.indexOf(0));
		String result = "";
		if (message.equals("setup")) {
			System.out.println("Connection established with the drone...");
			result = "Message:connected";
		} else {
			obsModel.deSerializeData(message);

			if (detectedObstacle())
				result = "ApproachingDrone";
			else
				result = "NotApproachingDrone";

		}

		return result;
	}

	public boolean detectedObstacle() {
		boolean isDetected = false;
		if (ObstacleDetectorCalculator.rectOverlap(obsModel.getSaMinX(), obsModel.getSaMinY(), obsModel.getSaMaxX(),
				obsModel.getSaMaxY(), obsModel.getDrMinX(), obsModel.getDrMinY(), obsModel.getDrMaxX(),
				obsModel.getDrMaxY())) {
			isDetected = true;
		}

		return isDetected;
	}

}

public class MainObstacleDetector {
	static int PROCESS_PORT = 7576;

	public static void main(String[] args) {

		Reciever secondProcess = new Reciever(PROCESS_PORT, new BackupProcess());
		secondProcess.start();

		int randomCrashTime = ThreadLocalRandom.current().nextInt(30, 40);
		System.out.println("The main detector crashes after " + randomCrashTime + " seconds");
		Thread crash = new Thread() {
			public void run() {
				int count = 0;
				try {
					while (true) {
						if (count == randomCrashTime) {
							main(args);
						}
						count++;
						sleep(1000);
					}
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		
		crash.start();
	}
}
