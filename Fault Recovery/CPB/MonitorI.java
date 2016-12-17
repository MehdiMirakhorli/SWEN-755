import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MonitorI extends Remote {

    public final byte BLOODPUMP_MAIN = 1;
    public final byte BLOOBPUMP_BK = 2;
    public final byte OXYGENATOR_MAIN = 3;
    public final byte OXYGENATOR_BK = 4;

    public void receiveHeartBeat(byte process) throws RemoteException;
    public void checkHeartBeats() throws RemoteException;
}
