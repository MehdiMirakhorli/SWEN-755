package Tasks;

import java.util.concurrent.TimeUnit;

public class PizzaOrder implements Runnable {
	private static int count=0;
	private int id;
	
	
	public void run() {
		System.out.println("#### [Thread: "+Thread.currentThread().getName()+"] Order: "+id +" Start to work as order placed ####");

			for(int i=1;i<4;i++){
				if(i==1){
					System.out.println("[Thread: "+Thread.currentThread().getName()+"] Order: "+id+" Preparation ");

				}else if(i==2){
					System.out.println("[Thread: "+Thread.currentThread().getName()+"] Order: "+id+" Bake " );

				}else{
					System.out.println("[Thread: "+Thread.currentThread().getName()+"] Order: "+id+" Delivery ");
				}
				try {
					TimeUnit.MILLISECONDS.sleep((long) (Math.random()*1000));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
			System.out.println("#### [Thread: "+Thread.currentThread().getName()+"] Order: "+id+" Done with work ####");

	}
		public PizzaOrder(){
			this.id = ++count;
			
		}
	

}
