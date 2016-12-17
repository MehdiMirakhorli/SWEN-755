import java.rmi.Remote;
import java.rmi.RemoteException;

public interface HeartBeatI extends Remote {
    public void sendHeartBeat() throws RemoteException;
}
