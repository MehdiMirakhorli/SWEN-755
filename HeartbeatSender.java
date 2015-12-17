import java.net.*;

public class HeartbeatSender extends Thread {
  
  int sendingInterval;
  String ip;
  int port;
  final String SIGNAL = ".";

  public HeartbeatSender(String ip, int port, int sendingInterval) {
    this.ip = ip;
    this.port = port;
    this.sendingInterval = sendingInterval;
  }

  public void run() {
    DatagramSocket ds = null;
    DatagramPacket dp = null;
    try {
      ds = createDatagramSocket();
      dp = createDatagramPacket();

      startPulse(ds, dp);

    } catch (Exception e) {
      System.out.println("HeartbeatSender dead");
    } finally {
      if (ds != null) ds.close();
    }
  }

  private DatagramSocket createDatagramSocket() throws Exception {
    return (new DatagramSocket());
  }

  private DatagramPacket createDatagramPacket() throws Exception {
    InetAddress ipAddress = InetAddress.getByName(ip);
    return (new DatagramPacket(SIGNAL.getBytes(), SIGNAL.length(), ipAddress, this.port));
  }

  private void startPulse(DatagramSocket ds, DatagramPacket dp) throws Exception {
    while (true) {
      ds.send(dp);
      System.out.print(SIGNAL);
      Thread.sleep(sendingInterval);
    }
  }
}
