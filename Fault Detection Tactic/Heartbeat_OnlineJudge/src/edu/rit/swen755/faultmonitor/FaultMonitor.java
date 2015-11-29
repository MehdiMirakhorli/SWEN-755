package edu.rit.swen755.faultmonitor;

import edu.rit.swen755.communication.ISynchronizerReceiver;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This is the class that handles the failure and recover it. Recovery
 * implemented in the future
 *
 * @author Joanna
 */
public class FaultMonitor {

    private ISynchronizerReceiver syncReceiver;

    public FaultMonitor(ISynchronizerReceiver syncReceiver) {
        this.syncReceiver = syncReceiver;

    }

    /**
     * Method invoked by the Heartbeat receiver whenever a failure is detected.
     */
    public void handleFailure() throws RemoteException {
        System.out.println("[MONITOR] Failure detected at " + (new SimpleDateFormat("hh:mm:ss a")).format(new Date()));
        //recover from failure
        syncReceiver.wakeUp();
    }
}
