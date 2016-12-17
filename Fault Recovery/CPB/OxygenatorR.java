import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.rmi.Naming;

public class OxygenatorR extends UnicastRemoteObject implements HeartBeatI {

    public OxygenatorR() throws RemoteException { super(); }

    public void sendHeartBeat() throws RemoteException{
        try {
            MonitorI monitor = (MonitorI) Naming.lookup("rmi://localhost:5000/monitor");
            while(true){
                monitor.receiveHeartBeat(monitor.OXYGENATOR_BK);
                Thread.sleep(1000);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String [] args){
        try {
            OxygenatorR oxygenator = new OxygenatorR();
            oxygenator.sendHeartBeat();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}
