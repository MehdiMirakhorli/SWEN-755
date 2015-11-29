package edu.rit.swen755.communication;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Remote interface for a Heartbeat Receiver The Fault Monitor process provides
 * a concrete implementation for this class.
 *
 * @author Joanna
 */
public interface IHeartbeatReceiver extends Remote {

    // name used to register the remote object in the RMI registry
    public static final String HB_RECEIVER_NAME = "HBReceiver";

    /**
     * Operation periodically invoked by the Heartbeat sender to report that it
     * is alive.
     *
     * @throws RemoteException if an error happened during the remote procedure
     * call.
     */
    public void processStatusReport() throws RemoteException;
}
