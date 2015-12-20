/*
 * This class is the Domain class, it simulates a Scheduled Transaction Processor
for a bank.
We simulate processing scheduled transactions by dividing a random number by another random number
that can sometimes be 0 and produce an unhandled exception
 */
package heartbeatimplementation;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Leonardo Matos & Ivan Taktuk
 */
public class TransactionProcessor implements Runnable{
    static int sendingInverval = 1000; //How often the beat is sent
    boolean isActive; //Used for the active redundancy to be implemented
    boolean isSpare; //Used for the passive redundancy to be implemented
    Integer id = null; //The id of the TransactionProcessor
   
    ArrayList<Integer> transactions = new ArrayList<>(); //Represents an array of account transactions that are 
    //scheduled to be processed, we emulate this by dividing a random number by another random number, the 
    //divisor can sometimes be 0 and thus produce an exception
    
    public static void main(String[] args) throws InterruptedException {
        TransactionProcessor transProcessor=new TransactionProcessor();
        Thread t = new Thread(transProcessor);
        t.setUncaughtExceptionHandler(transProcessor.h);
        t.start();
        while(true){
            transProcessor.sendAliveSignal();
            Thread.sleep(transProcessor.sendingInverval);
        }
        
    }
    
    Thread.UncaughtExceptionHandler h= new Thread.UncaughtExceptionHandler(){
        public void uncaughtException(Thread th, Throwable ex) {
         System.exit(1);
        }
    };
       
    TransactionProcessor(){
        //The TransactionProcessor transactions arrayList is filled with random numbers
        Random rand = new Random();
        rand.setSeed(System.currentTimeMillis());
        for (int i=0; i<1000; i++){
            transactions.add(rand.nextInt() % 256);
        }
    }
    
    void processTransaction() throws InterruptedException{
        Random r = new Random();
        r.setSeed(System.currentTimeMillis());

        for(Integer transactionID: transactions){
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(TransactionProcessor.class.getName()).log(Level.SEVERE, null, ex);
            }
            Integer divisor = r.nextInt(100);
            //Here we simulate processing a transaction by dividing a random number with another that can
            //sometimes be 0
            int result= transactionID/divisor;
             System.out.println("Transaction Processed: Result="+result+", Divisor="+ divisor );
        }
        
    }
    
    void sendAliveSignal(){
        //InvokePitAPat in Fault detector
        try {
            RmiServerIntf obj = (RmiServerIntf)Naming.lookup("//localhost/RmiServer");

            if (id==null){
                id=obj.getId();
            }
            obj.pitAPat(id);
            System.out.println("Beat");

        } catch (NotBoundException | MalformedURLException | RemoteException ex) {
            Logger.getLogger(TransactionProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    //Start the thread
    public void run() {
        try {
            processTransaction();
        } catch (InterruptedException ex) {
            Logger.getLogger(TransactionProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
