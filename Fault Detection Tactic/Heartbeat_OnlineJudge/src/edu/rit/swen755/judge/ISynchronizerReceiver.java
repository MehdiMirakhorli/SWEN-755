package edu.rit.swen755.communication;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ISynchronizerReceiver extends Remote {

    public static final String SYNC_RECEIVER_NAME = "SynchronizerReceiver";

    public void synchronize() throws RemoteException;

    public void wakeUp() throws RemoteException;
}
