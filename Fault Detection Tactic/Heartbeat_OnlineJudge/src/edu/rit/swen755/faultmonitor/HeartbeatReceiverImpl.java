package edu.rit.swen755.faultmonitor;

import edu.rit.swen755.communication.IHeartbeatReceiver;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Concrete implementation of the Heartbeat Receiver remote interface. It
 * periodically checks if the sender is still alive.
 *
 * @author Joanna
 */
public class HeartbeatReceiverImpl implements IHeartbeatReceiver, Runnable {

    // the frequency (in milliseconds) in which the receiver checks the health of the sender
    private int checkingInterval;
    // delta time (in milliseconds) in which the last reported aliveness is considered as still valid
    private int expireTime;
    // the last timestamp (in milliseconds) that a new report from HBSender was received
    private long lastUpdatedTime;
    // the monitor object that will handle the detected failure
    private FaultMonitor monitor;

    /**
     *
     * @param checkingInterval frequency (in milliseconds) that the sender shall
     * be verified
     * @param expireTime time elapsed (in milliseconds) that a message is
     * considered as old
     * @param monitor reference to the FaultMonitor which handles the failure
     * detected
     */
    public HeartbeatReceiverImpl(int checkingInterval, int expireTime, FaultMonitor monitor) {
        super();
        this.checkingInterval = checkingInterval;
        this.expireTime = expireTime;
        this.monitor = monitor;
    }

    /**
     * Check the aliveness of the sender.
     *
     * @return true if the sender has sent a message recently. False, if the
     * sender is dead
     */
    public boolean isComponentAlive() {
        long currentTime = System.currentTimeMillis();
        return (currentTime - lastUpdatedTime) <= expireTime;
    }

    @Override
    public void processStatusReport() throws RemoteException {
        this.lastUpdatedTime = System.currentTimeMillis();
        System.out.println("[MONITOR] Received HB Message at " + (new SimpleDateFormat("hh:mm:ss a")).format(new Date()));
    }

    @Override
    public void run() {
        while (true) {
            try {
                // Wait for the next verification
                Thread.sleep(checkingInterval);
            } catch (InterruptedException ex) {
                System.err.println("[MONITOR] Exception: " + ex.getLocalizedMessage());
            }
            // Checks if component has died
            boolean isAlive = isComponentAlive();
            System.out.println("[MONITOR] Verification: Is Sender Alive? " + isAlive + " at " + (new SimpleDateFormat("hh:mm:ss a")).format(new Date()));
            if (!isAlive) {
                try {
                    monitor.handleFailure();
                } catch (RemoteException ex) {
                    Logger.getLogger(HeartbeatReceiverImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
