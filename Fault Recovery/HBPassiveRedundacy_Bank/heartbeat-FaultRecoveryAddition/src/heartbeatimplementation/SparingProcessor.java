/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heartbeatimplementation;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ivantactukmercado
 */
public class SparingProcessor extends UnicastRemoteObject implements RmiSparingInt{
    
    boolean activated=false;
    
    public SparingProcessor() throws RemoteException{
        super(0);    // required to avoid the 'rmic' step
    }
    
    public static void main(String[] args) throws RemoteException, MalformedURLException {
        System.setProperty("java.net.preferIPv4Stack" , "true");
        try { 
            LocateRegistry.createRegistry(1099); 
            System.out.println("java RMI registry created.");
        } catch (RemoteException e) {
            System.out.println("java RMI registry already exists.");
        }
           
        //Instantiate RmiServer
        SparingProcessor obj = new SparingProcessor();

        // Bind this object instance to the name 
        Naming.rebind("//localhost/RmiSparing", obj);
        System.out.println("Sparing started");
        
    }

    @Override
    public void activate() throws RemoteException {
        if (activated) return;
        new Thread(){
            public void run() {
                String[] args=new String[1];
                try {
                    activated=true;
                    System.out.println("trying activating");
                    TransactionProcessor pr=new TransactionProcessor();
                            pr.main(args);
                } catch (InterruptedException | IOException ex) {
                    Logger.getLogger(SparingProcessor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }.start();
        
    }
}
