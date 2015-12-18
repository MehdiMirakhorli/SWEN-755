/*
 * FaultDetector class that listens to the beat comming from the TransactionProcessor 
 */
package heartbeatimplementation;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Leonardo Matos & Ivan Taktuk
 */
public class FaultDetector extends UnicastRemoteObject implements RmiServerIntf, Runnable {
    static int checkingInterval = 15000; //in miliseconds
    static long expireTime; //The time before the next beat must be recieved
    static boolean isLinked =false;  // We use this to determine when the conection to the TransactionProcessor has been 
    //started for the first time, otherwise the Fault Detector would start sending messages to the Fault Monitor
    //as soon as it starts
    static long lastUpdatedTime;
    static boolean isAlive  = true; //if the client is alive
    static int lastId=0;
    static int lastUpdatedId=0;
    HashMap<Integer, Long> lastUpdatedTimeMap=new HashMap<>();
    
    public FaultDetector() throws RemoteException {
        super(0);    // required to avoid the 'rmic' step
    }
    
    void checkAlive(){
        //If the current time is higher that the expire time, then the Procesor has died
        if(System.currentTimeMillis()  > expireTime){
            isAlive = false;
            System.out.println("isAlive=False");
        }
            
        
        if(!isAlive && isLinked){
             //Notify the monitor if it is not alive
            try {
                RmiFaultMonitorIntf obj = (RmiFaultMonitorIntf)Naming.lookup("//localhost/RmiMonitor");
                obj.NotAlive(lastUpdatedId);
                System.out.println("Fault Monitor was notified");
            } catch (NotBoundException | MalformedURLException | RemoteException ex) {
                Logger.getLogger(TransactionProcessor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
            
    }
    
    public static void main(String[] args) throws RemoteException, MalformedURLException {
        FaultDetector fd=new FaultDetector();
        new Thread(fd).start();
        try { //special exception handler for registry creation
            LocateRegistry.createRegistry(1099); 
            System.out.println("java RMI registry created.");
        } catch (RemoteException e) {
            //do nothing, error means registry already exists
            System.out.println("java RMI registry already exists.");
        }
           
        //Instantiate RmiServer
        FaultDetector obj = new FaultDetector();

        // Bind this object instance to the name "RmiServer"
        Naming.rebind("//localhost/RmiServer", obj);
        System.out.println("Fault Detector started");
        
        
    }

    @Override
    //Keeps the TransactionProessor alive and indicates it 
    public void pitAPat(int id){
        System.out.println("Beat Received from Trasaction Processor "+id);
        isAlive=true;
        this.isLinked = true;
        updateTime(id);
    }

    //Updates the lastUpdatedTime and the expireTime
    void updateTime(int id){
        this.lastUpdatedTime = System.currentTimeMillis() ;
        this.expireTime = lastUpdatedTime + checkingInterval;
        this.lastUpdatedId=id;
    }

    @Override
    //Get the ID of the current TransactionProcessor
    public int getId() throws RemoteException {
        lastId++;
        return lastId;
    }

    @Override
    //Run the thread
    public void run() {
        try {
            while(true){
                checkAlive();
                Thread.sleep(checkingInterval/10);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(FaultDetector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}