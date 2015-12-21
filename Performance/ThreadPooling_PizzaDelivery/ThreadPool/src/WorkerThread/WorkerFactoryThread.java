package WorkerThread;

import java.util.concurrent.ThreadFactory;

public class WorkerFactoryThread implements ThreadFactory{
	private static int count =0;
	private static String NAME  ="Pizza worker";
	
	public Thread newThread(Runnable r) {
		Thread t = new Thread(r,NAME + ++count);
		return t;
	}

}
