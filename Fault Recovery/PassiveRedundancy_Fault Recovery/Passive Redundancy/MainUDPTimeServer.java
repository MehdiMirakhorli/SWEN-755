import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.Scanner;




public class MainUDPTimeServer {

	public static boolean handleRequests = false;
	int portNumber;
	String IPAddress;
	DatagramSocket serverSocket;
	
	public MainUDPTimeServer(int portNumber, String IPAddress){

		this.portNumber = portNumber;
		this.IPAddress = IPAddress;

	}

	class Failure extends Thread{
		public void run(){
			Random r = new Random();
			int timer = r.nextInt(20000);
			timer  = timer/1000;
			for(int i = 0; i <= timer; i++){
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(i == timer){
					System.exit(0);
				}
			}
		}
	}
	class Listener extends Thread{
		
		public void run(){
		    while(true){
				if(handleRequests == true){
					System.out.println("now handling reqs.");
					int port = 0;
					InetAddress IPAddress = null;
					Scanner inputFileContent = null;
					String data[] = null;
					int i = 0;
					try{
						inputFileContent  = new Scanner(new File("log.txt"));
						//Scanner outputFileContent  = new Scanner(new File("out01.txt"));
						/*while(inputFileContent.hasNext()){
							data[i] = inputFileContent.nextLine();
							i++;
						}*/
						
						if(inputFileContent.hasNext()){
							data = inputFileContent.nextLine().split("/");
						}
						
						
						port = Integer.parseInt(data[0]);
						IPAddress = InetAddress.getByName(data[1]);
						
					}catch(FileNotFoundException e){
						System.err.println("File not found!");
					}catch(Exception e){
						System.err.println("Something went wrong!");
					}finally{
						inputFileContent.close();
					}
					ServerHandler handler = new ServerHandler(port, IPAddress, serverSocket, null, "getDateAndTime");
					handler.start();
					
					break;
				}
			}
		}
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
					String logData[] = new String[2];
					logData[0] = Integer.toString(port);
					logData[1] = IPAddress.toString();
					
					File output = new File("log.txt");
		            PrintWriter printer = new PrintWriter(output);
		            
		            for(int i = 0; i < logData.length; i++){
		            	printer.write(logData[i]);
		            }
		            printer.close();
		            System.out.println("Sending current data and time.");
		            Thread.sleep(6000);
					serverSocket.send(sendPacket); 
					System.out.println("Sent!");
					
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

				new Listener().start();
				
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
					Thread.sleep(8500);
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
		new Failure().start();
		new Heartbeat().start();
	}
}
