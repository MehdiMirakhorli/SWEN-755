import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.rmi.Naming;

public class Oxygenator extends UnicastRemoteObject implements HeartBeatI {

    public Oxygenator() throws RemoteException { super(); }

    public void sendHeartBeat() throws RemoteException{
        try {
            MonitorI monitor = (MonitorI) Naming.lookup("rmi://localhost:5000/monitor");
            while(true){
                monitor.receiveHeartBeat(monitor.OXYGENATOR_MAIN);
                Thread.sleep(1000);
                if(!ErrorGenerator.generateError()) {
					System.out.println("Oxygenator is GOING DOWN");
                    System.exit(0);                	
                } else {
					System.out.println("Oxygenator is RUNNING");
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String [] args){
        try {
            Oxygenator oxygenator = new Oxygenator();
            oxygenator.sendHeartBeat();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
