import java.util.Observable;

public class BloodPump extends Observable implements Runnable{

    private static boolean loop = true;

    public static void crash(){
        loop = false;
    }

    @Override
    public void run(){
        System.out.println("Blood Pump running!");
        while(loop){
            this.setChanged();
            this.notifyObservers(MonitoringModule.BLOOD_PUMP_INDEX);
            try{
                Thread.sleep(2500);
            } catch(InterruptedException e){ }
        }
    }
}
