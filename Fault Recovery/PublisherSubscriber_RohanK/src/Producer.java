import java.io.*;
//import java.io.InputStreamReader;
//import java.io.PrintStream;
import java.net.*;
public class Producer {
	ReplicaProducer rp = new ReplicaProducer();
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
     Producer Server = new Producer(); 
     Server.run();
     
	 

	}
   public void run() throws IOException, Exception{
	   ServerSocket serverSocket = new ServerSocket(60000);
	   System.out.println("Waiting for connections");
	   
	   while(true){
	   Socket sock = serverSocket.accept();
	   new ProducerThread(sock).start();
	   new ProducerThread(sock).sleep(1000);

	   if(new ProducerThread(sock).isAlive()){
	   
	   System.out.println("Server: At your service I am alive");
	   System.out.println("Heartbeat successfull");
	   }
	   else{
		   System.out.println("Heartbeat:");
		   System.out.println("OH NO SERVER NOT RESPONDING" + "\t" + "System crashed");
		   System.out.println("Recovering..................... waiting for server to reconnect");
		   rp.irun();
	   }
	   
	   }
	   

   }
	  // InputStreamReader Ir = new InputStreamReader(sock.getInputStream());
	   //BufferedReader Br = new BufferedReader(Ir);
	   
	   //String message = Br.readLine();
	   //System.out.println(message);
	   
	   //if (message!= null){
		//   PrintStream Ps = new PrintStream(sock.getOutputStream(),true);
		  // Ps.println("Message Received");
	   //}
	   
   }
   
	   //catch(Exception e1){
		 //  System.out.println("No data found for socket" +e1);
	   //}
  
