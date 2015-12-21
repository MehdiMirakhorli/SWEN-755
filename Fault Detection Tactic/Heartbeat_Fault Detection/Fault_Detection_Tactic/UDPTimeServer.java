import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;



public class UDPTimeServer {
	int portNumber;
	String IPAddress;

	public UDPTimeServer(int portNumber, String IPAddress){

		this.portNumber = portNumber;
		this.IPAddress = IPAddress;

	}

	class ServerHandler extends Thread{

		int port;
		DatagramSocket serverSocket; DatagramPacket receivePacket; String query;
		InetAddress IPAddress;
		byte[] sendData  = new byte[1024];
		DatagramPacket sendPacket;

		public ServerHandler(int port, InetAddress IPAddress, DatagramSocket serverSocket, DatagramPacket receivePacket
				, String query){
			this.port = port;
			this.IPAddress = IPAddress;
			this.serverSocket = serverSocket;
			this.receivePacket = receivePacket;
			this.query = query;
		}

		public void run(){

			try{

				if(query.equals("getDateAndTime")){
					System.out.println("Request received for Date and Time");
					DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
					Calendar calobj = Calendar.getInstance();

					sendData = (df.format(calobj.getTime())).getBytes(); 

					sendPacket = 
							new DatagramPacket(sendData, sendData.length
									, IPAddress, 
									port); 

					// send the packet
					serverSocket.send(sendPacket); 

				}else if(query.equals("areYouAlive?")){

					//System.out.println("req");
					sendData = ("Yes,Alive").getBytes(); 

					sendPacket = 
							new DatagramPacket(sendData, sendData.length
									, IPAddress, 
									port); 

					// send the packet
					serverSocket.send(sendPacket); 

				}else{
					System.err.println("Wrong query!");
				}


			}catch(Exception e){
				System.err.println("Error!");
			}
		}
	}
	class Server extends Thread{

		DatagramSocket serverSocket;
		DatagramPacket receivePacket;

		public Server() {

		}

		public void run() 
		{
			try{
				serverSocket = new DatagramSocket(portNumber); 

				byte[] receiveData = new byte[1024]; 
				byte[] sendData  = new byte[1024]; 
				receivePacket = new DatagramPacket(receiveData
						, receiveData.length); 

				System.out.println("UDP Server listening on port: " 
						+ portNumber);
			} catch (Exception e) {
				System.out.println("Could not listen on port " + portNumber);
				System.exit(-1);
			}
			try{

				while(true){

					serverSocket.receive(receivePacket); 

					String query = new String(receivePacket.getData(), 0
							, receivePacket.getLength());

					InetAddress IPAddress = receivePacket.getAddress(); 
					int port = receivePacket.getPort();

					ServerHandler handler = new ServerHandler(port, IPAddress, serverSocket, receivePacket, query);
					handler.start();

				}

			}catch(Exception e){
				System.out.println("Something went wrong.");
			}	
		}
	}

	public class Heartbeat extends Thread{

		int port;
		DatagramPacket receivePacket; String query;
		InetAddress IPAddress;
		byte[] sendData  = new byte[1024];
		DatagramPacket sendPacket;
		DatagramSocket serverSocket; 
		public Heartbeat(){

		}

		public void run(){
			try {
				IPAddress = InetAddress.getByName("localhost");
				serverSocket = new DatagramSocket(); 
			} catch (UnknownHostException | SocketException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			while(true){

				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				sendData = ("IamAlive").getBytes(); 

				System.out.println("Sending Heartbeat...");
				sendPacket = 
						new DatagramPacket(sendData, sendData.length
								, IPAddress, 
								3000); 

				// send the packet
				try {
					serverSocket.send(sendPacket);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 

			}
		}
	}
	public void startServer() {

		Server sThread = new Server();
		// start the server thread
		sThread.start();
		new Heartbeat().start();
	}
}
