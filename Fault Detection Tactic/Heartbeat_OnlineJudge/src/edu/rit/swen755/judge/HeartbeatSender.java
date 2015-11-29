package edu.rit.swen755.judge;

import edu.rit.swen755.communication.IHeartbeatReceiver;
import java.io.IOException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * HeartbeatSender is a TimerTask class that periodically sends a heartbeat
 * message to the remote receiver.
 *
 * @author Joanna
 */
public class HeartbeatSender extends TimerTask implements Thread.UncaughtExceptionHandler {

    // reference to the remote receiver that will receive the HB messages
    private final IHeartbeatReceiver receiver;

    /**
     *
     * @param receiver a stub to the remote heartbeat receiver object
     */
    public HeartbeatSender(IHeartbeatReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void run() {
        try {
            //send "I am alive" to the Heartbeat Receiver
            receiver.processStatusReport();
            System.out.println("[JUDGE-ACTIVE] Sending 'I am alive' at " + (new SimpleDateFormat("hh:mm:ss a")).format(new Date()));
        } catch (RemoteException ex) {
            System.err.println("[JUDGE-ACTIVE] Exception: " + ex.getMessage());
        }
    }

    @Override
    public void uncaughtException(Thread thread, Throwable exception) {
        try {
            // Unhandled fault happened during the judging thread. HB messages are stopped
            cancel();

            System.out.println("[JUDGE-ACTIVE] Failure happened in thread " + thread.getName());
            System.out.println("[JUDGE-ACTIVE] Failure cause " + exception.getLocalizedMessage());
            System.out.println("[JUDGE-ACTIVE] Press any key to close application.");
            System.in.read();
            System.exit(-1);
        } catch (IOException ex) {
            Logger.getLogger(JudgeMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
