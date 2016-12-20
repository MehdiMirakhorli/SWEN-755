import java.io.*;
import java.net.*;
import java.util.*;
import java.util.*;
public class ProducerThread extends Thread {
Socket socket;
	ProducerThread(Socket socket){
		this.socket = socket;
	}
public void run(){
	try{
		
		String message= null;
	
		BufferedReader buf= new BufferedReader( new InputStreamReader(socket.getInputStream()));
		//for (i=0; i<4;i++){
		while((message =buf.readLine())!=null){
			System.out.println("Incoming client message"+ "\t" +  message);
			
		}
		//}
		socket.close();
	}
	catch(IOException e){
		e.printStackTrace();
	}
}
}
