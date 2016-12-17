
import java.io.*;

import java.net.*;

/**
 * @author Rebaz
 *
 */

public class RemoteController {

	static int HEARTBEAT_PORT = 7475;
	static int PROCESS_PORT = 7576;

	public static void main(String[] args) {

//		Reciever secondProcess = new Reciever(PROCESS_PORT, new SecondProcess());
//		secondProcess.start();

		ProcessMonitorUI pmu = new ProcessMonitorUI();
		pmu.setVisible(true);
		pmu.pmc.repaint();

		Reciever hbRreciever = new Reciever(HEARTBEAT_PORT, new HearBeatReciver(pmu));
		hbRreciever.start();

	}
}

class Reciever extends Thread {
	ServerNetWorkIO IO;
	ProcessInput pr;

	public Reciever(int port, ProcessInput pr) {
		IO = new ServerNetWorkIO(port);
		this.pr = pr;
	}

	public void run() {
		System.out.println("Connecting to drone ...");
		while (true) {
			DatagramPacket packet = IO.getPacket();
			if (packet != null) {
				ProccessInput(packet);
			}
		}
	}

	/**
	 * Process the input data received from the drone
	 * 
	 * @param packet
	 */

	public void ProccessInput(DatagramPacket packet) {

		String result = pr.ProcessInput(packet);
		IO.sendPacket(
				new DatagramPacket(result.getBytes(), result.getBytes().length, packet.getAddress(), packet.getPort()));
	}
}

/**
 * Perform network operations
 * 
 * @author Rebaz
 *
 */

class ServerNetWorkIO {
	private DatagramSocket serverSocket;
	private DatagramPacket Packet;
	private byte[] Data;

	ServerNetWorkIO(int port) {
		try {
			serverSocket = new DatagramSocket(port);
		} catch (SocketException e) {
			System.err.println("Error: Socket could not be created");
			System.exit(1);
		}
	}

	public DatagramPacket getPacket() {
		Data = new byte[1024];
		Packet = new DatagramPacket(Data, Data.length);
		try {
			serverSocket.receive(Packet);
		} catch (IOException e) {
			System.err.println("Error: error while recieving packet");
			return null;
		}
		return Packet;
	}

	public void sendPacket(DatagramPacket packet) {
		try {
			serverSocket.send(packet);
		} catch (IOException e) {
			System.err.println("Error: error while sending packet");
		}
	}
}

interface ProcessInput {
	public String ProcessInput(DatagramPacket packet);
}

class SecondProcess implements ProcessInput {

	ObstacleDetectModel obsModel;
	boolean isRunning = true;

	SecondProcess() {
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

class HearBeatReciver implements ProcessInput {

	ProcessMonitorUI pmu;

	public HearBeatReciver(ProcessMonitorUI pmu) {
		this.pmu = pmu;
	}

	@Override
	public String ProcessInput(DatagramPacket packet) {
		String packetMessage = new String(packet.getData());
		String message = packetMessage.substring(packetMessage.indexOf(':') + 1, packetMessage.indexOf(0));

		String result = "Message:";
		if (message.equals("setup")) {
			System.out.println("Connection established with the drone...");
			result += "connected";
		} else {

			try {
				int runningProcesses = Integer.parseInt(message);
				pmu.pmc.setRunningProcesses(runningProcesses);
				pmu.pmc.repaint();

			} catch (NumberFormatException e) {
				System.out.println("Not a valid number");
			}

			result += "inzone";
		}

		return result;

	}

}
