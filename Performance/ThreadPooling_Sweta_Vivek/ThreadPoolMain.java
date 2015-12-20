
public class ThreadPoolMain implements Runnable {

	//command= 10 jobs to be executed by the thread pool.
	private String command;
	
	public ThreadPoolMain(String s){
		this.command=s;
	}

	@Override
	public void run() {
		//display which thread 
		System.out.println(Thread.currentThread().getName()+ "Start. Command = "+command);
		
		processCommand();
		
		//display that the thread has ended
		System.out.println(Thread.currentThread().getName()+" End.");
	}
	private void processCommand() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	@Override
	public String toString(){
		return this.command;
	}
}
