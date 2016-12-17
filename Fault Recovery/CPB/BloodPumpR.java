import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.rmi.Naming;

public class BloodPumpR extends UnicastRemoteObject implements HeartBeatI {

    public BloodPumpR() throws RemoteException { super(); }

    public void sendHeartBeat() throws RemoteException{
        try {
            MonitorI monitor = (MonitorI) Naming.lookup("rmi://localhost:5000/monitor");
            while(true){
                monitor.receiveHeartBeat(monitor.BLOOBPUMP_BK);
                Thread.sleep(1000);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String [] args){
        try {
            BloodPumpR bloodPump = new BloodPumpR();
            bloodPump.sendHeartBeat();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}
