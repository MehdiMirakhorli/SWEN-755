import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
public class ReplicaProducer {

	/*public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		ReplicaProducer replicaserver = new ReplicaProducer(); 
		replicaserver.irun();
			
	}*/
	
	public void irun() throws IOException, Exception{
		ServerSocket rsocket = new ServerSocket(50000);
		System.out.println("ReplicaProducer Started");
		System.out.println("You are now connected successfully");
		
		while(true){
		Socket clsocket = rsocket.accept();
		new ProducerThread(clsocket).start();
		System.out.println("Hello Client now I can serve you");
			
		}
	}
	
	
}



/*public class Producer {
  
		// TODO Auto-generated method stub
   Producer Server = new Producer(); 
   Server.run();
	
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
		   System.out.println("OH NO SERVER NOT RESPONDING");
	   }

	   }
	   

 }
*/