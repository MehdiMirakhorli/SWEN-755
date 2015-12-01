package edu.rit.swen755.judge;

import edu.rit.swen755.communication.ISynchronizerReceiver;
import edu.rit.swen755.communication.IHeartbeatReceiver;
import java.io.IOException;
import java.net.Socket;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Timer;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * This is the entry point for the Judge Process.
 *
 * @author Joanna Cecilia
 */
public class JudgeMain {

    // constant for the address of the RMI registry
    private static final String REGISTRY_ADDRESS = "localhost";
    // constant for the Server's TCP port to accept connections from client
    private static final int SERVER_PORT = 5109;

    // constant for the Heartbeat's sending interval
    private static final int SENDING_INTERVAL = 5000;

    // constant for the sync interval
    private static final int SYNC_INTERVAL = 10000;
    // thread responsible for processing submissions and sending a response back to the client
    private static Thread judgeThread;
    // a TimerTask that periodically sends HB messages (sender role)
    private static HeartbeatSender sender;
    // a thread for receiving incoming submissions and adding to the queue
    private static Thread submissionManagerThread;

    private static Registry rmiRegistry;

    /**
     * Initializes the heartbeat-related classes.
     *
     * @throws RemoteException - when communication to RMI failed
     * @throws NotBoundException - when the remote receiver object was not found
     * in the RMI registry
     * @throws AccessException - when RMI registry deny access to the stub
     */
    private static void initHeartbeat() throws RemoteException, NotBoundException, AccessException {
        //gets a stub to the receiver (a RMI remote object)
        IHeartbeatReceiver receiver = (IHeartbeatReceiver) rmiRegistry.lookup(IHeartbeatReceiver.HB_RECEIVER_NAME);
        sender = new HeartbeatSender(receiver);

        // creates a timer and schedules the sender as timer task
        Timer senderTimer = new Timer();
        senderTimer.scheduleAtFixedRate(sender, 0, SENDING_INTERVAL);

        System.out.println("[JUDGE-ACTIVE] HeartbeatSender started!");
    }

    private static void initSubmissionManager(ConcurrentLinkedQueue<Socket> queue) throws IOException {
        SubmissionManager manager = new SubmissionManager(SERVER_PORT, queue);
        submissionManagerThread = new Thread(manager);
        submissionManagerThread.start();
        // capture failures that happened during processing incoming submissions 
        submissionManagerThread.setUncaughtExceptionHandler(sender);
    }

    private static void initJudgeThread(ConcurrentLinkedQueue<Socket> queue) {
        CodeEvaluator judge = new CodeEvaluator(queue);
        judgeThread = new Thread(judge);
        judgeThread.start();
        // capture failures that happened during the judgment 
        judgeThread.setUncaughtExceptionHandler(sender);

    }

    public static void initActiveInstance(ConcurrentLinkedQueue<Socket> queue) throws RemoteException, IOException, NotBoundException {
        initHeartbeat();
        initSubmissionManager(queue);
        initJudgeThread(queue);
        initUpdaterSender();
        System.out.println("[JUDGE-ACTIVE] Instance initialized");
    }

    private static void initUpdaterSender() throws RemoteException, NotBoundException {
        //gets a stub to the receiver (a RMI remote object)
        ISynchronizerReceiver syncReceiver = (ISynchronizerReceiver) rmiRegistry.lookup(ISynchronizerReceiver.SYNC_RECEIVER_NAME);
        SynchronizerSender syncSender = new SynchronizerSender(syncReceiver);

        // creates a timer and schedules the sender as timer task
        Timer syncTimer = new Timer();
        syncTimer.scheduleAtFixedRate(syncSender, 0, SYNC_INTERVAL);
        System.out.println("[JUDGE-ACTIVE] UpdaterSender started!");
    }

    public static void initPassiveInstance() throws RemoteException, AlreadyBoundException {
        // Bind the remote object's stub in the registry
        UpdaterReceiverImpl updater = new UpdaterReceiverImpl();
        ISynchronizerReceiver stub = (ISynchronizerReceiver) UnicastRemoteObject.exportObject(updater, 0);
        rmiRegistry.bind(ISynchronizerReceiver.SYNC_RECEIVER_NAME, stub);
        System.out.println("[JUDGE-PASSIVE] Instance initialized");
    }

    public static void main(String[] args) throws Exception {
        String state = args[0];
        rmiRegistry = LocateRegistry.getRegistry(REGISTRY_ADDRESS);
        if (state.equalsIgnoreCase("RUN")) {

            // queue of connections 
            ConcurrentLinkedQueue<Socket> queue = new ConcurrentLinkedQueue<Socket>();
            initActiveInstance(queue);
        } else if (state.equalsIgnoreCase("SLEEP")) {
            initPassiveInstance();
        }
    }
}
