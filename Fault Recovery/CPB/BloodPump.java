import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.rmi.Naming;

public class BloodPump extends UnicastRemoteObject implements HeartBeatI {

    public BloodPump() throws RemoteException { super(); }

    public void sendHeartBeat() throws RemoteException{
        try {
            MonitorI monitor = (MonitorI) Naming.lookup("rmi://localhost:5000/monitor");
            while(true){
                monitor.receiveHeartBeat(monitor.BLOODPUMP_MAIN);
                Thread.sleep(1000);
                if(!ErrorGenerator.generateError()) {
					System.out.println("BloodPump is GOING DOWN");
                    System.exit(0);                	
                } else {
					System.out.println("BloodPump is RUNNING");
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String [] args){
        try {
            BloodPump bloodPump = new BloodPump();
            bloodPump.sendHeartBeat();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
