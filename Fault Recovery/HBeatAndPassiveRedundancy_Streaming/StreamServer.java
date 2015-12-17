import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Random;
import java.net.*;

public class StreamServer {

    int port;
    HeartbeatSender hbSender;
    long startTime;
    Checkpointer checkpointer;

    public StreamServer(int port, HeartbeatSender hbSender) {
        this.port = port;
        this.hbSender = hbSender;
        checkpointer = new Checkpointer();
    }

    public void run() throws Exception {
        startTime = System.currentTimeMillis();
        this.hbSender.start();
        ServerSocket serverSocket = new ServerSocket(port);
        checkpointer.read();
        try {
            new Thread(new UnkonwRandomFault(this)).start();
            while(true){
                Socket socket = serverSocket.accept();
                System.out.println("Client connected: " + socket.getInetAddress());
                new Thread(new ConnectionHandler(socket)).start();
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            System.err.println("Server uptime: " + uptime());
            hbSender.interrupt();
        } finally {
            serverSocket.close();
        }
    }

    public void runOnDemand() throws Exception{
        int packetSize = 2;
        DatagramSocket socket = new DatagramSocket(3001);
        byte[] buf = new byte[packetSize];
        DatagramPacket dp = new DatagramPacket(buf, packetSize);
        socket.receive(dp);
        run();
    }

    public long uptime() {
        return System.currentTimeMillis() - this.startTime;
    }

    public static void main(String[] args) throws Exception {
        StreamServer ss = new StreamServer(3000, new HeartbeatSender("127.0.0.1", 1234, 1000));
        if(args[0].equals("passive")) {
            System.out.print("Passive server is on");
            ss.runOnDemand();
        }
        else{
            ss.run();
        }
    }

    class UnkonwRandomFault implements Runnable {
        long initialTime;
        long finalTime;
        StreamServer ss;

        public UnkonwRandomFault(StreamServer ss){
            this.initialTime = System.currentTimeMillis();
            this.finalTime = randInt(8000, 15000);
            System.out.println("Unkonw failure will happen in " + this.finalTime + " milliseconds");
        }
        public void run(){
            while (true) {
                if(uptime() > this.finalTime){
                    System.err.println("A failure in the server occurred");
                    System.exit(0);
                }
            }
        }
        public long uptime() {
            return System.currentTimeMillis() - this.initialTime;
        }
        private long randInt(int min, int max) {
            Random rand = new Random();
            int randomNum = rand.nextInt((max - min) + 1) + min;
            return randomNum;
        }
    }

    class ConnectionHandler implements Runnable {
        Socket socket;
        long clientStartTime;
        public ConnectionHandler(Socket socket){
            this.socket = socket;
            System.out.println(socket);
            checkpointer.addClient(socket.toString());
        }
        public void run() {
            clientStartTime = System.currentTimeMillis();
            StreamGenerator sg = new StreamGenerator(checkpointer.getClientLastState(socket.toString()));
            try {
                PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);

                while (true) {
                    out.println(sg.nextData());
                    checkpointer.updateState(socket.toString(),sg.getCurrentData());
                    checkpointer.persist();
                    Thread.sleep(1000);
                }
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
                System.err.println("Connection [" + socket.getInetAddress() + "] failled.");
                System.err.println("    Uptime: " + uptime());
                System.err.println("    Stream current state: " + sg.getCurrentData());
            }
        }
        public long uptime() {
            return System.currentTimeMillis() - this.clientStartTime;
        }
    }
}
