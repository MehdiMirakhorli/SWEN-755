import java.util.Observer;
import java.util.Observable;

public class MonitoringModule extends Thread implements Observer{

    private boolean[] iAmAlive = new boolean[] {false, false};
    public static final int BLOOD_PUMP_INDEX = 1;
    public static final int OXYGENATOR_INDEX = 0;

    @Override
    public void run(){
        System.out.println("Monitoring Module running!");
        while(true){
            this.check();
            try{
                Thread.sleep(3000);
            } catch(InterruptedException e){ }
        }
    }

    @Override
    public void update(Observable o, Object arg){
        int index = (int) arg;
        this.iAmAlive[index] = true;
    }

    private void check(){
        if(this.iAmAlive[BLOOD_PUMP_INDEX] == false){
            FailureHandler.notifyDoctor("Blood Pump");
        }

        if(this.iAmAlive[OXYGENATOR_INDEX] == false){
            FailureHandler.notifyDoctor("Oxygenator");
        }
        this.iAmAlive[BLOOD_PUMP_INDEX] = false;
        this.iAmAlive[OXYGENATOR_INDEX] = false;
    }
}
