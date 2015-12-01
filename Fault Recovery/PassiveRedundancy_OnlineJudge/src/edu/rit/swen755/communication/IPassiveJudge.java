package edu.rit.swen755.communication;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Remote interface implemented by the Passive Instance of the Judge.
 *
 * @author Joanna
 */
public interface IPassiveJudge extends Remote {

    // name used when binding this to the registry
    public static final String PASSIVE_JUDGE_NAME = "IPassiveJudge";

    /**
     * Method to wake up passive instance.
     *
     * @throws RemoteException
     */
    public void wakeUp() throws RemoteException;
}
