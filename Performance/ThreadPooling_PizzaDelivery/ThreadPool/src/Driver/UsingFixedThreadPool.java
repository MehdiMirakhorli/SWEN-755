package Driver;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Tasks.PizzaOrder;
import WorkerThread.WorkerFactoryThread;

/**
 * 
 * @author Omeya & Rohan
 *
 */
public class UsingFixedThreadPool {

	public static void main(String[] args) {
		String threadName = Thread.currentThread().getName();
		System.out.println("Thread name: "+threadName+" [Main thread starts here]");
		/**
		 * We are assigning 10 threads to do the tasks 
		 */
		ExecutorService executorService = Executors.newFixedThreadPool(10, new WorkerFactoryThread());
		executorService.execute(new PizzaOrder());
		executorService.execute(new PizzaOrder());
		executorService.execute(new PizzaOrder());
		executorService.execute(new PizzaOrder());
		executorService.execute(new PizzaOrder());
		executorService.execute(new PizzaOrder());
		executorService.execute(new PizzaOrder());
		executorService.execute(new PizzaOrder());
		executorService.execute(new PizzaOrder());
		executorService.execute(new PizzaOrder());
		executorService.execute(new PizzaOrder());
		executorService.execute(new PizzaOrder());
		executorService.execute(new PizzaOrder());
		executorService.execute(new PizzaOrder());
		executorService.execute(new PizzaOrder());
		executorService.execute(new PizzaOrder());
		executorService.execute(new PizzaOrder());
		executorService.execute(new PizzaOrder());
		executorService.execute(new PizzaOrder());
		executorService.execute(new PizzaOrder());
		executorService.execute(new PizzaOrder());
		executorService.execute(new PizzaOrder());
		executorService.execute(new PizzaOrder());
		executorService.execute(new PizzaOrder());
		executorService.execute(new PizzaOrder());
		executorService.execute(new PizzaOrder());
		executorService.execute(new PizzaOrder());

		executorService.shutdown();
		System.out.println("Thread name: "+threadName+" [Main thread ends here]");
	}

}
