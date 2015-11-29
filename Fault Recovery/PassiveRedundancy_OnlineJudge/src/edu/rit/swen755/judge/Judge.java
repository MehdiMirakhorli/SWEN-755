package edu.rit.swen755.judge;

import edu.rit.swen755.communication.IHeartbeatReceiver;
import edu.rit.swen755.communication.IPassiveJudge;
import edu.rit.swen755.communication.SubmissionMessage;
import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Timer;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author Joanna
 */
public class Judge implements IPassiveJudge {

    private Checkpoint checkpoint;
    // constant for the address of the RMI registry
    private static final String REGISTRY_ADDRESS = "localhost";
    // constant for the Server's TCP port to accept connections from client
    private static final int SERVER_PORT = 5109;
    // constant for the Heartbeat's sending interval
    private static final int SENDING_INTERVAL = 5000;

    // thread responsible for processing submissions and sending a response back to the client
    private Thread codeEvaluatorThread;
    // a TimerTask that periodically sends HB messages (sender role)
    private HeartbeatSender hbSender;
    // a thread for receiving incoming submissions and adding to the queue
    private Thread submissionManagerThread;
    // reference to the RMI registry 
    private Registry rmiRegistry;

    // queue of submissions
    private ConcurrentLinkedQueue<SubmissionMessage> queue;

    public Judge() throws RemoteException, NotBoundException, IOException {
        // initializes RMI Registry
        this.rmiRegistry = LocateRegistry.getRegistry(REGISTRY_ADDRESS);
        // initializes checkpoint that saves internal state 
        this.checkpoint = new Checkpoint();
        // initializes empty queue 
        this.queue = new ConcurrentLinkedQueue<SubmissionMessage>();
    }

    private void initActive() throws IOException, RemoteException, NotBoundException {
        // creates thread for processing submissions
        CodeEvaluator codeEvaluator = new CodeEvaluator(queue, checkpoint);
        this.codeEvaluatorThread = new Thread(codeEvaluator, "Code Evaluator");

        // creates submission manager
        SubmissionManager manager = new SubmissionManager(SERVER_PORT, queue, checkpoint);
        this.submissionManagerThread = new Thread(manager, "Submission Manager");

        // starts SubmissionManager and CodeEvaluator threads
        submissionManagerThread.start();
        codeEvaluatorThread.start();

        //gets a stub to the heartbeat receiver (a RMI remote object)
        IHeartbeatReceiver receiver = (IHeartbeatReceiver) rmiRegistry.lookup(IHeartbeatReceiver.HB_RECEIVER_NAME);
        this.hbSender = new HeartbeatSender(receiver);

        // starts HeartbeatSender thread to send HB at SENDING_INTERVAL
        Timer senderTimer = new Timer();
        senderTimer.scheduleAtFixedRate(hbSender, 0, SENDING_INTERVAL);

        // capture failures that happened during processing incoming submissions 
        submissionManagerThread.setUncaughtExceptionHandler(hbSender);
        codeEvaluatorThread.setUncaughtExceptionHandler(hbSender);
    }

    private void initPassive() throws RemoteException, AlreadyBoundException {
        IPassiveJudge stub = (IPassiveJudge) UnicastRemoteObject.exportObject(this, 0);
        rmiRegistry.bind(IPassiveJudge.PASSIVE_JUDGE_NAME, stub);
    }

    public void init(String mode) throws RemoteException, NotBoundException, AlreadyBoundException, IOException {
        System.out.println("Instance initiated in mode " + mode);
        if (mode.equalsIgnoreCase("RUN")) {
            initActive();
        } else if (mode.equalsIgnoreCase("SLEEP")) {
            initPassive();
        } else {
            throw new IllegalArgumentException("Invalid value for mode. It should be RUN or SLEEP");
        }
    }

    @Override
    public void wakeUp() throws RemoteException {
        System.out.println("Waking Up and rollinback");
        try {
            // Retrieves all the uncompleted submissions from the checkpoint and load
            queue = checkpoint.rollback();
            System.out.println("Rollbacked. Uncompleted submissions " + queue.size());
            initActive();
        } catch (Exception ex) {
            System.out.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
    }

}
