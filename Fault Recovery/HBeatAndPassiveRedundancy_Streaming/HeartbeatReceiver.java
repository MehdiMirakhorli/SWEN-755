import java.net.*;

public class HeartbeatReceiver extends Thread {

  int listenPort;
  int checkingInterval;
  int checkingTime;
  int expireTime;
  long lastUpdatedTime;
  final int DATAGRAM_PACKET_SIZE = 2;

  public HeartbeatReceiver(int listenPort, int checkingInterval, int expireTime){
    this.checkingInterval = checkingInterval;
    this.expireTime = expireTime;
    this.listenPort = listenPort;
  }

  public void run() {
    DatagramSocket ds = null;
    DatagramPacket dp = null;
    try {
      ds = createDatagramSocket();
      dp = createDatagramPacket();

      listenPulse(ds, dp);

    } catch (Exception e) {
      System.out.println("Server is dead");
    } finally {
      if (ds != null) ds.close();
    }
  }

  private DatagramSocket createDatagramSocket() throws Exception {
    DatagramSocket socket = new DatagramSocket(this.listenPort);
    socket.setSoTimeout(expireTime);
    return socket;
  }

  private  DatagramPacket createDatagramPacket(){
    byte[] buf = new byte[DATAGRAM_PACKET_SIZE];
    return (new DatagramPacket(buf, DATAGRAM_PACKET_SIZE));
  }

  private  void listenPulse(DatagramSocket ds, DatagramPacket dp) throws Exception {
    while (true) {
      ds.receive(dp);
      lastUpdatedTime = System.currentTimeMillis();
      Thread.sleep(checkingInterval);
    }
  }

  public static void main(String[] args) throws Exception {
    new HeartbeatReceiver(1234, 1000, 1000).run();
  }
}
