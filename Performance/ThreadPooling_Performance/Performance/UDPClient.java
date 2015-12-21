import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class UDPClient {

	public static void main(String ar[])throws Exception{

		byte[] sendData = new byte[1024]; 
		byte[] receiveData = new byte[1024]; 

		InetAddress IPAddress = InetAddress.getByName("localhost"); 
		DatagramSocket clientSocket = new DatagramSocket(); 
		
		sendData = ("getDateAndTime").getBytes(); 
		
		DatagramPacket sendPacket = 
				new DatagramPacket(sendData, sendData.length, IPAddress, 2000); 

		clientSocket.send(sendPacket); 

		DatagramPacket receivePacket = 
				new DatagramPacket(receiveData, receiveData.length);
		
		clientSocket.receive(receivePacket); 
		
		System.out.println("Current date and time is: " + new String(receivePacket.getData(), 0
				, receivePacket.getLength()));
	}
}
