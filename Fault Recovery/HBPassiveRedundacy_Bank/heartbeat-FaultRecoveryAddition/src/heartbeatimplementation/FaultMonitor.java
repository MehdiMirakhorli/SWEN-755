/*
 * This class monitors the hearbeat and is used to display failure messages and in
real life log the failures in a database, or other tasks.
 */
package heartbeatimplementation;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Leonardo Matos & Ivan Taktuk
 */
public class FaultMonitor extends UnicastRemoteObject implements RmiFaultMonitorIntf{
    
    public FaultMonitor() throws RemoteException{
        super(0);    // required to avoid the 'rmic' step
    }
    
    public static void main(String[] args) throws RemoteException, MalformedURLException {
        try { 
            LocateRegistry.createRegistry(1099); 
            System.out.println("java RMI registry created.");
        } catch (RemoteException e) {
            System.out.println("java RMI registry already exists.");
        }
           
        //Instantiate RmiServer
        FaultMonitor obj = new FaultMonitor();

        // Bind this object instance to the name 
        Naming.rebind("//localhost/RmiMonitor", obj);
        System.out.println("Fault Monitor started");
        
    }
    
    //Logs the error message, in this case it just displays it on the console
    void logMessage(String message){
        System.out.println(message);
    }

    @Override
    //Method that manages what to do when the Transaction Processor is not alive
    public void NotAlive(int id) throws RemoteException {
        logMessage("Transaction Processor "+id+" has failed at "+System.currentTimeMillis());
        logMessage("Fault Monitor is trying to restart spare Transaction Processor");
        try {
            RmiSparingInt obj = (RmiSparingInt)Naming.lookup("//localhost/RmiSparing");
            obj.activate();
        } catch (NotBoundException | MalformedURLException | RemoteException ex) {
            logMessage("Fault Monitor was not able to restart sparing Transaction Processor");
        }
        logMessage("Done trying restarting");
    }

    @Override
    public void WasReplaced(int p_id, int n_id) throws RemoteException {
        logMessage("Transaction Processor "+n_id+" took over Transaction Processor "+p_id+" at "+System.currentTimeMillis());
    }
}
