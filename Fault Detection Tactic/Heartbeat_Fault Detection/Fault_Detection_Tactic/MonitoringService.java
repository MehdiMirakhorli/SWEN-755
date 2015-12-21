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

							UDPTimeServer serverObject = new UDPTimeServer(2000, "localhost");
							serverObject.startServer();
						}


					}
					catch (SocketTimeoutException e){
						System.out.println ("Server Crashed. Restarting Server...");
						UDPTimeServer serverObject = new UDPTimeServer(2000, "localhost");
						serverObject.startServer();
					}

				}catch(Exception e){

				}
			}
		}
	}
	public static void main(String ar[])throws Exception{

		new Monitornode().start();;
	}
}
