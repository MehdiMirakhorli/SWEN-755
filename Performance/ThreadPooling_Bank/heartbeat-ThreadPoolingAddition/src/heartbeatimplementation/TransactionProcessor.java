/*
 * This class is the Domain class, it simulates a Scheduled Transaction Processor
for a bank.
We simulate processing scheduled transactions by dividing a random number by another random number
that can sometimes be 0 and produce an unhandled exception
 */
package heartbeatimplementation;

import java.io.IOException;
import static java.lang.Thread.sleep;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.MulticastSocket;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author Leonardo Matos & Ivan Taktuk
 */
public class TransactionProcessor implements Runnable{
    static int sendingInverval = 1000; //How often the beat is sent
    boolean isActive; //Used for the active redundancy to be implemented
    boolean isSpare; //Used for the passive redundancy to be implemented
    Integer id = null; //The id of the TransactionProcessor
    static int peersUpdateInterval=500;
    ArrayList<Integer> transactions = new ArrayList<>(); //Represents an array of account transactions that are 
    //scheduled to be processed, we emulate this by dividing a random number by another random number, the 
    //divisor can sometimes be 0 and thus produce an exception
    String multicastAddress="225.0.0.1";
    int multicastPort=4447;
    HashMap<String,String> ProcIdTransIdMap=new HashMap<>();
    HashMap<String,Long> ProcIdLastTimeMap=new HashMap<>();
    int consecutiveReceptions=0;
    int lastTransaction=-1;
    private final BlockingQueue<Integer> transactionQueue=new LinkedBlockingQueue<>();
    
    public static void main(String[] args) throws InterruptedException, IOException {
        System.setProperty("java.net.preferIPv4Stack" , "true");
        TransactionProcessor transProcessor=new TransactionProcessor();
        transProcessor.getId();
        System.out.println("Transaction Processor id "+transProcessor.id+" waiting to be activated");
        new Thread()
        {
            public void run() {
                for(int i=0;i<4;i++){
                    transProcessor.updatePeers(transProcessor.id,0);
                    try {
                        sleep(peersUpdateInterval);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(TransactionProcessor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }.start();
        
        transProcessor.listenMulticast();
        
        
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
            transactions.add(i+1);
        }
    }
    
    void processTransaction() throws InterruptedException{
        Random r = new Random();
        r.setSeed(System.currentTimeMillis());
        
        ThreadPool pool=new ThreadPool(5);

        for(Integer transactionID: transactions){
            if (transactionID<lastTransaction) continue;
            try {
                Thread.sleep(peersUpdateInterval);
            } catch (InterruptedException ex) {
                Logger.getLogger(TransactionProcessor.class.getName()).log(Level.SEVERE, null, ex);
            }
            pool.addTask(new RequestReader());
            //Here we simulate processing a transaction by dividing a random number with another that can
            //sometimes be 0
            pool.addTask(new RequestResponder(this.id, transactionID));
        }
        
    }
    
    void getId(){
        try {
            RmiServerIntf obj = (RmiServerIntf)Naming.lookup("//localhost/RmiServer");
            if (id==null){
                id=obj.getId();
            }            
        } catch (NotBoundException | MalformedURLException | RemoteException ex) {
            Logger.getLogger(TransactionProcessor.class.getName()).log(Level.SEVERE, null, ex);
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

    
    public void listenMulticast() throws IOException, InterruptedException{
        MulticastSocket socket = new MulticastSocket(multicastPort);
        InetAddress group = InetAddress.getByName(multicastAddress);
        socket.joinGroup(group);
        
        DatagramPacket packet;
        while (true) {
            byte[] buf = new byte[256];
            packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);
            String received = new String(packet.getData()).trim();
            String[] data=received.split(":");
            if (Integer.parseInt(data[0])==this.id) {
                consecutiveReceptions++;                  
            }else{
                consecutiveReceptions=0;                 
            }
                            
            if (ProcIdLastTimeMap.containsKey(this.id.toString())){
                if (System.currentTimeMillis()- ProcIdLastTimeMap.get(id.toString())<peersUpdateInterval+1) {               
                    updatePeers(id, 0);
                    sleep(peersUpdateInterval);
                }
            }
            
            
            ProcIdTransIdMap.put(data[0], data[1]);
            ProcIdLastTimeMap.put(data[0], System.currentTimeMillis());
            
            //System.out.println("MulticastReceived: " + received);
            if (shouldBeRunning()){
                break;
            }
        }

        socket.leaveGroup(group);
        socket.close();
    }
    
    public void updatePeers(Integer processId,Integer transactionId){
        MulticastSocket socket = null;
        try {
            socket = new MulticastSocket(multicastPort);
            byte[] buf = new byte[256];
            // don't wait for request...just send a quote

            String dString = processId.toString()+":"+transactionId.toString();
            buf = dString.getBytes();

            InetAddress group = InetAddress.getByName(multicastAddress);
            DatagramPacket packet;
            packet = new DatagramPacket(buf, buf.length, group, multicastPort);
            socket.send(packet);

           
        }
        catch (IOException e) {
            e.printStackTrace();
            
        }
        finally{
            if(socket!=null)
                socket.close();
        }
        
    }
    
    public boolean shouldBeRunning(){
        int lowestId=this.id;
        boolean allAreInactive=true;
        ArrayList<String> expiredProcId=new ArrayList<>();
        for(Entry<String, Long> entry : ProcIdLastTimeMap.entrySet()) {
            Integer idProcess =Integer.parseInt(entry.getKey());
            Long lastTime = entry.getValue();
            int transId=Integer.parseInt(ProcIdTransIdMap.get(entry.getKey()));
            if(lastTransaction<transId){
                lastTransaction=transId;
            }
            if (!ProcIdTransIdMap.get(entry.getKey()).equals("0")){
                allAreInactive=false;
            }
            if (System.currentTimeMillis()-lastTime>peersUpdateInterval*2){
                //has expired
                expiredProcId.add(entry.getKey());
            }
            if (idProcess<lowestId){
                lowestId=idProcess;
            }
        }
        for(String id:expiredProcId){
            ProcIdLastTimeMap.remove(id);
            ProcIdTransIdMap.remove(id);
        }
        
        if (consecutiveReceptions>=4){
            return true;
        }
        if (allAreInactive && ProcIdLastTimeMap.size()>1 && lowestId==id){
            return true;
        }
        
        if (ProcIdLastTimeMap.size()<=0){
            return true;
        }
        
        return false;
    }
   
    private class RequestReader implements Executable{

        @Override
        public void execute() {
            Random rand = new Random();
            rand.setSeed(System.currentTimeMillis());
            Integer divisor = rand.nextInt(100);
            System.out.println("Request Read by Thread id="+Thread.currentThread().getId());
            try{
                transactionQueue.put(divisor);
                Thread.sleep(100);
            }catch(InterruptedException e){
                
            }
        }
        
    }
    private class RequestResponder implements Executable{

        Integer transactionId, processId;
        
        public RequestResponder(int processId,int transId){
            this.processId=processId;
            this.transactionId=transId;
        }
        
        @Override
        public void execute() {
            try {
                Integer divisor = transactionQueue.take();
            //Here we simulate processing a transaction by dividing a random number with another that can
            //sometimes be 0
                int result= this.transactionId/divisor;
                updatePeers(this.processId, this.transactionId);
                lastTransaction=this.transactionId;
                System.out.println("Transaction #"+ this.transactionId.toString()+" Processed: Result="+result+", Divisor="+ divisor+", Thread Id="+Thread.currentThread().getId() );
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
    }
    
}
