import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class threadpool {

	public static void main(String[] args) {
		
ExecutorService executor = Executors.newFixedThreadPool(10); // A pool of 10 threads
for (int i = 0; i < 20; i++) { // No. of sub tasks or requests to be executed is 20. 
    Runnable worker = new WorkerThread((i+1));  
    executor.execute(worker);//calling execute method of ExecutorService  
  }  
executor.shutdown();  
while (!executor.isTerminated()) {   }  

System.out.println("All threads are done working"); 
	}

}
