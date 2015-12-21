import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;


public class MonitoringService {

	static class Monitornode extends Thread{

		byte[] sendData = new byte[1024]; 
		byte[] receiveData = new byte[1024]; 
		InetAddress IPAddress;
		DatagramSocket clientSocket;
		DatagramSocket serverSocket;
		public Monitornode() {

		}
		public void run(){
			try {
				serverSocket = new DatagramSocket(3000);
				IPAddress = InetAddress.getByName("localhost"); 
				
				System.out.println("Monitoring Service started.");
			} catch (SocketException | UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			while(true){

				try{
					
					byte[] receiveData = new byte[1024]; 
					byte[] sendData  = new byte[1024]; 
					
					DatagramPacket receivePacket = 
							new DatagramPacket(receiveData, receiveData.length);

					serverSocket.setSoTimeout(10000);

					try {
						serverSocket.receive(receivePacket); 
						String received = new String(receivePacket.getData(), 0, receivePacket.getLength());
						
						if((received.equals("IamAlive"))){
							System.out.println("Server is Alive!");
						}else{
							System.out.println("Main Server crashed. \nActivating Backup Server...");
							BackupUDPTimeServer.handleRequests = true;
						}


					}
					catch (SocketTimeoutException e){
						System.out.println ("Server Crashed. Restarting Server...");
						//BackupUDPTimeServer serverObject = new MainUDPTimeServer(2000, "localhost");
						//Launcher.backupServerObject.startServer();
						
						BackupUDPTimeServer backupServerObject = new BackupUDPTimeServer(2000, "localhost");
						BackupUDPTimeServer.handleRequests = true;
						
						backupServerObject.startServer();
					}

				}catch(Exception e){

				}
			}
		}
	}
	public static void main(String ar[]) throws Exception{

		new Monitornode().start();
	}
}
