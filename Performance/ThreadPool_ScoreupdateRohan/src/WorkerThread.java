import java.util.concurrent.ExecutorService;  
import java.util.concurrent.Executors;
class WorkerThread implements Runnable {
	private int message;
	
    public WorkerThread(int i){  
        this.message=i;  
    }  
    
    public void run() {  
        System.out.println(Thread.currentThread().getName()+" Thread starts");
       
        UpdateStatistics(message);
        processmessage();       // To make thread sleep for 2000 milliseconds or 2 seconds.
        System.out.println(Thread.currentThread().getName()+" Terminated");  //tells which thread ended or terminated.  
    }  
    
    private void processmessage() {  
        try {  Thread.sleep(2000);  } catch (InterruptedException e) { e.printStackTrace(); }  
    }  
    
    private void UpdateStatistics(int j){   //method for threads to execute sub tasks of task update statistics
    	if(j>=0 && j<=6){
    		System.out.println(Thread.currentThread().getName()+ "Updating goals scored");
    	}
    	else if(j>6 && j<=10){
    		System.out.println(Thread.currentThread().getName()+ "Updating team stats");
    	}
    	else if(j>10 && j<=16){
    		System.out.println(Thread.currentThread().getName()+ "Update player miles ran");
    	}
    	else{
    		System.out.println(Thread.currentThread().getName()+ "Update possession percentage");
    	}
    }
}
