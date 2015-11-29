package edu.rit.swen755.judge;

import edu.rit.swen755.communication.ISynchronizerReceiver;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

/**
 * SynchronizerSender is a TimerTask class that periodically sends synchronizing
 * message to the remote receiver in the passive instance.
 *
 * @author Joanna
 */
public class SynchronizerSender extends TimerTask {

    // reference to the remote receiver that will receive the HB messages
    private final ISynchronizerReceiver receiver;

    /**
     *
     * @param receiver a stub to the remote SynchronizerReceiver object
     */
    public SynchronizerSender(ISynchronizerReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void run() {
        try {
            receiver.synchronize();
            System.out.println("[JUDGE-ACTIVE] Sending sync data at " + (new SimpleDateFormat("hh:mm:ss a")).format(new Date()));
        } catch (RemoteException ex) {
            System.err.println("[JUDGE-ACTIVE] Exception: " + ex.getMessage());
        }
    }

   

}
