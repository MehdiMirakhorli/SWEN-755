/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heartbeatimplementation;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author ivantactukmercado
 */
public class ThreadPool {
    private final Thread[] workerThreads;
    private volatile boolean shutdown;
    private final BlockingQueue<Executable> workerQueue;
    
    
    public ThreadPool(int N){
        this.workerThreads=new Thread[N];
        this.workerQueue=new LinkedBlockingQueue<>();
        for (int i = 0; i < N; i++) {
            this.workerThreads[i]=new Worker("Thread "+i);
            this.workerThreads[i].setUncaughtExceptionHandler(h);
            this.workerThreads[i].start();
        }
    }
    
    Thread.UncaughtExceptionHandler h= new Thread.UncaughtExceptionHandler(){
        public void uncaughtException(Thread th, Throwable ex) {
         System.exit(1);
        }
    };
    
    public void shutdown()
    {
        while (!workerQueue.isEmpty()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {                
            }
        }
        shutdown = true;
        for (Thread workerThread : workerThreads) {
            workerThread.interrupt();
        }
    }
    
    public void addTask(Executable ex)
    {
        try {
            workerQueue.put(ex);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    private class Worker extends Thread{
        
        public Worker(String name){
            super(name);         
        }
        
        public void run(){
            while(!shutdown){
                try{
                    Executable ex=workerQueue.take();
                    ex.execute();
                }catch(InterruptedException e){
                    
                }
            }
        }
    }
    
}
