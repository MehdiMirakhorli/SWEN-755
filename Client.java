import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.Socket;
import java.net.InetSocketAddress;
import java.net.BindException;

public class Client {
    static int localPort;
    static Socket s;

    public static BufferedReader connect() throws Exception {
        if(s != null) s.close();
        s = new Socket();
        s.bind(new InetSocketAddress("127.0.0.1", localPort));
        s.connect(new InetSocketAddress("127.0.0.1", 3000));
        BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
        return input;
    }

    public static void main(String[] args) throws Exception {
        localPort = Integer.parseInt(args[0]);

        BufferedReader input = connect();
        String stream = null;
        int count = 0;

        while (count<10) {
          stream = input.readLine();
          if(stream == null){
            System.out.println("Reconnecting...");
            Thread.sleep(1000);

            try{
              input = connect();
              count = 0;
            }catch(ConnectException ce){
              count += 1;
            }
          }else{
            System.out.println(stream);
          }
        }
    }
}