import java.net.*;

class FaultMonitor {

	final String ACTIVATE_SIGNAL = "activate";

	public void activatePassiveServer(String host, int port) throws Exception {
		DatagramSocket ds = new DatagramSocket();
		InetAddress ipAddress = InetAddress.getByName(host);
		DatagramPacket dp = new DatagramPacket(ACTIVATE_SIGNAL.getBytes(), ACTIVATE_SIGNAL.length(), ipAddress, port);
		ds.send(dp);
	}

	public void run() throws Exception {
		Thread t = new HeartbeatReceiver(1234, 1000, 1000);
		t.run();
		t.join();
		System.out.println("Activating passive server...");
		activatePassiveServer("127.0.0.1", 3001);
	}

	public static void main(String[] args) throws Exception {
		(new FaultMonitor()).run();
	}
}