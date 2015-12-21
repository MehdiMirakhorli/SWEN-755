import java.net.UnknownHostException;


public class Launcher {
	public static void main(String ar[]) throws InterruptedException, UnknownHostException{

		int portNumber = 2000;
		String IPAddress = "localhost";
		
		
		/*
		 * call the server method to start running for listening on its
		 * assigned port
		 */
		UDPTimeServer serverObject = new UDPTimeServer(portNumber, IPAddress);
		serverObject.startServer();

	}
}
