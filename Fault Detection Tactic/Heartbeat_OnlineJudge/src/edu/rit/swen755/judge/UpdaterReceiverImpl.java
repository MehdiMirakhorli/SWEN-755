package edu.rit.swen755.judge;

import edu.rit.swen755.communication.ISynchronizerReceiver;
import java.io.IOException;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Joanna
 */
public class UpdaterReceiverImpl implements ISynchronizerReceiver {

    @Override
    public void synchronize() throws RemoteException {
        System.out.println("[JUDGE-PASSIVE] Received synch data at " + (new SimpleDateFormat("hh:mm:ss a")).format(new Date()));
    }

    @Override
    public void wakeUp() throws RemoteException {
        System.out.println("[JUDGE-PASSIVE] Waking Up");
        //TODO initialize from previous queue
        try {
            JudgeMain.initActiveInstance(new ConcurrentLinkedQueue<Socket>());
        } catch (IOException ex) {
            Logger.getLogger(UpdaterReceiverImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(UpdaterReceiverImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
