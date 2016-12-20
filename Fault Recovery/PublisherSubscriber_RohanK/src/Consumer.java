import java.io.*;
import java.net.*;

public class Consumer {

	private InetAddress host;
	private InetAddress clhost;
	public static void main(String[] args)throws Exception {
		// TODO Auto-generated method stub
		Consumer Client = new Consumer(); 
	     Client.run();
	}
	public void run() throws Exception{
		host = InetAddress.getLocalHost();
		Socket sock = new Socket(host,60000);
		//clhost = InetAddress.getLocalHost();
		//Socket clsock = new Socket(clhost,50000);
		int i;
		for(i=0;i<3;i++){
		PrintStream Ps = new PrintStream(sock.getOutputStream(),true);
		
		Ps.print("Client:");
		Ps.println("\t" + "Can I get service?");
		}
		
		//PrintStream CPs = new PrintStream(clsock.getOutputStream(),true);
		//CPs.println("Hey are u alive?");
		InputStreamReader Ir = new InputStreamReader(sock.getInputStream());
		BufferedReader Br = new BufferedReader(Ir);
		while(true){
		String message = Br.readLine();
		System.out.println(message + "hello");
		sock.close();
		}
		
	}
}
		
	
	

