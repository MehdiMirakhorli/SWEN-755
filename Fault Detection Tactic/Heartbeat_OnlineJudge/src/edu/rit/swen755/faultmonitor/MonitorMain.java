package edu.rit.swen755.faultmonitor;

import edu.rit.swen755.communication.IHeartbeatReceiver;
import edu.rit.swen755.communication.ISynchronizerReceiver;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main class for the Monitoring process.
 *
 * @author Joanna
 */
public class MonitorMain {

    // Port used by the RMI registry
    private static final int RMI_PORT = 1099;
    // Frequency of checking : 15 seconds
    private static final int CHECKING_INTERVAL = 15000;
    // Tolerance (expiration time) : 10 seconds
    private static final int EXPIRE_TIME = 10000;

    private static void initHeartbeatReceiver(Registry registry) throws RemoteException, AlreadyBoundException, NotBoundException {

        // initialize Heartbeat Receiver
        ISynchronizerReceiver syncReceiver = (ISynchronizerReceiver) registry.lookup(ISynchronizerReceiver.SYNC_RECEIVER_NAME);
        FaultMonitor monitor = new FaultMonitor(syncReceiver);
        HeartbeatReceiverImpl receiver = new HeartbeatReceiverImpl(CHECKING_INTERVAL, EXPIRE_TIME, monitor);

        // Bind the remote object's stub in the registry
        IHeartbeatReceiver stub = (IHeartbeatReceiver) UnicastRemoteObject.exportObject(receiver, 0);
        registry.bind(IHeartbeatReceiver.HB_RECEIVER_NAME, stub);

        // starts the receiver thread
        Thread threadReceiver = new Thread(receiver);
        threadReceiver.setDaemon(true);
        threadReceiver.start();

        System.out.println("[MONITOR] Heartbeat receiver initialized!");
    }

    private static Process startPassiveCopy() throws IOException {
        String classpath = System.getProperty("user.dir") + "\\build\\classes";
        String judgeMainClass = "edu.rit.swen755.judge.JudgeMain";
        ProcessBuilder builder = new ProcessBuilder("java", "-cp", classpath, judgeMainClass, "SLEEP");
        Process p = builder.start();

        final InputStream is = p.getInputStream();
        Thread outReader = new Thread(new Runnable() {

            @Override
            public void run() {
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String line = null;
                try {
                    while ((line = br.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(MonitorMain.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
        outReader.setDaemon(true);
        outReader.start();

        System.out.println("[MONITOR] Passive copy initialized");
        return p;
    }

    public static void main(String[] args) throws Exception {
        Registry registry = LocateRegistry.createRegistry(RMI_PORT);
        final Process passiveInstance = startPassiveCopy();
        Thread.sleep(1000);
        initHeartbeatReceiver(registry);

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.out.println("[MONITOR] Closing passive instance");
                passiveInstance.destroy();
            }
        });

    }

}
