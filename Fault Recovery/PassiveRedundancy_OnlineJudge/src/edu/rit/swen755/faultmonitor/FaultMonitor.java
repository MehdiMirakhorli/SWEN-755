package edu.rit.swen755.faultmonitor;

import edu.rit.swen755.communication.IHeartbeatReceiver;
import edu.rit.swen755.communication.IPassiveJudge;
import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JTextArea;

/**
 * This is the class that handles the failure and recover from it.
 *
 * @author Joanna
 */
public class FaultMonitor {

    // Port used by the RMI registry
    private static final int RMI_PORT = 1099;
    // Frequency of checking : 15 seconds
    private static final int CHECKING_INTERVAL = 15000;
    // Tolerance (expiration time) : 10 seconds
    private static final int EXPIRE_TIME = 10000;
    // constant with the classpath used when invoking the Judge process 
    private static final String CLASSPATH = System.getProperty("user.dir") + "\\build\\classes;lib\\sqlite-jdbc-3.8.11.2.jar";

    // reference to the passive judge
    private IPassiveJudge syncReceiver;
    // reference to the RMI registry
    private Registry registry;

    // threads used to read output from Passive/Running processes
    private ProcessReader activeReader, passiveReader;
    private Thread activeReaderThread, passiveReaderThread;
    // Passive and Running processes of Judge
    private Process passiveInstance, activeInstance;

    private void initHeartbeatReceiver() throws RemoteException, AlreadyBoundException, NotBoundException {
        // initialize reference to the passive remote interface
        this.syncReceiver = (IPassiveJudge) registry.lookup(IPassiveJudge.PASSIVE_JUDGE_NAME);

        // initialize Heartbeat Receiver and binds it to the Registry
        HeartbeatReceiverImpl receiver = new HeartbeatReceiverImpl(CHECKING_INTERVAL, EXPIRE_TIME, this);
        IHeartbeatReceiver stub = (IHeartbeatReceiver) UnicastRemoteObject.exportObject(receiver, 0);
        this.registry.bind(IHeartbeatReceiver.HB_RECEIVER_NAME, stub);

        // starts the receiver thread
        Thread threadReceiver = new Thread(receiver);
        threadReceiver.setDaemon(true);
        threadReceiver.start();
    }

    /**
     * Starts a new Judge instance.
     *
     * @param mode Indicates whether the instance should be active (RUN) or
     * should be inactive (SLEEP).
     * @return a reference to the process handler
     * @throws IOException problem when starting process
     */
    private static Process startInstance(String mode) throws IOException {
        System.out.println("Starting instance " + mode);
        ProcessBuilder builder = new ProcessBuilder("java", "-cp", CLASSPATH, "edu.rit.swen755.judge.JudgeMain", mode);
        Process process = builder.start();
        return process;
    }

    /**
     * Initializes the monitor. It performs the following actions: - creates the
     * Judge instances (in active and sleeping modes) - starts heartbeat
     * receiver - adds a shutdown hook to shutdown every process when this
     * application finishes
     *
     * @throws Exception
     */
    public void init() throws Exception {
        // gets registry
        this.registry = LocateRegistry.createRegistry(RMI_PORT);

        // starts passive instance and starts reading from its output 
        this.passiveInstance = startInstance("SLEEP");
        this.passiveReader = new ProcessReader(passiveInstance.getInputStream(), MonitorMain.frame.getTextAreaSleepingJudge());
        this.passiveReaderThread = new Thread(passiveReader);
        this.passiveReaderThread.setDaemon(true);
        this.passiveReaderThread.start();
        Thread.sleep(1000);

        // initialize receiver
        initHeartbeatReceiver();
        Thread.sleep(1000);

        // starts active instance and starts reading from its output 
        this.activeInstance = startInstance("RUN");
        this.activeReader = new ProcessReader(activeInstance.getInputStream(), MonitorMain.frame.getTextAreaRunningJudge());
        this.activeReaderThread = new Thread(activeReader);
        this.activeReaderThread.setDaemon(true);
        this.activeReaderThread.start();

        // add a Hook triggered when the application closes
        // this was added just for the sake of avoiding having to close 
        // the external process manually in order to test them again
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                passiveInstance.destroy();
                activeInstance.destroy();
            }
        });

    }

    /**
     * Method invoked by the Heartbeat receiver whenever a failure is detected.
     *
     * @throws java.rmi.RemoteException
     */
    public void handleFailure() throws RemoteException {

        JTextArea textAreaMonitor = MonitorMain.frame.getTextAreaMonitor();
        JTextArea textAreaActive = MonitorMain.frame.getTextAreaRunningJudge();
        JTextArea textAreaPassive = MonitorMain.frame.getTextAreaSleepingJudge();

        textAreaMonitor.append("Failure detected at " + (new SimpleDateFormat("hh:mm:ss a")).format(new Date()) + "\n");
        textAreaMonitor.append("Waking up Passive Copy\n");

        textAreaActive.append("========= NEW INSTANCE =========\n");
        textAreaPassive.append("========= NEW INSTANCE =========\n");

        // wakes up passive instance, now it becomes running
        this.passiveReader.updateTextArea(textAreaActive);
        this.syncReceiver.wakeUp();

        // stops previous readings
        this.activeReaderThread.interrupt();

        // starts a new passive instance
        try {
            this.passiveInstance = startInstance("SLEEP");
            this.passiveReader = new ProcessReader(passiveInstance.getInputStream(), textAreaPassive);
            this.passiveReaderThread = new Thread(passiveReader);
            this.passiveReaderThread.setDaemon(true);
            this.passiveReaderThread.start();
        } catch (IOException ex) {
            textAreaMonitor.append(ex.getClass().getName() + " : " + ex.getMessage());
        }

    }
}
