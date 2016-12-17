import java.util.Observable;

public class Oxygenator  extends Observable implements Runnable{

    private static boolean loop = true;

    public static void crash(){
        loop = false;
    }

    @Override
    public void run(){
        System.out.println("Oxygenator running!");
        while(loop){
            this.setChanged();
            this.notifyObservers(MonitoringModule.OXYGENATOR_INDEX);
            try{
                Thread.sleep(2500);
            } catch(InterruptedException e){ }
        }
    }
}
