import java.net.UnknownHostException;

public class Launcher {
	static BackupUDPTimeServer backupServerObject;
	public static void main(String ar[]) throws Exception{

		int portNumber = 2000;
		String IPAddress = "localhost";
		
		
		/*
		 * call the server method to start running for listening on its
		 * assigned port
		 */
		MainUDPTimeServer mainServerObject = new MainUDPTimeServer(portNumber, IPAddress);
		mainServerObject.startServer();

		//new MonitoringService().startMonitoringService();
		
		backupServerObject = new BackupUDPTimeServer(portNumber, IPAddress);
		
	}
}
