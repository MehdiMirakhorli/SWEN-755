import java.util.Random;

public class FailureGenerator{

    public void generateFailure(){
        Random rdm = new Random();
        int number = 0;
         while(true){
             number = rdm.nextInt(100);
             if(number > 75 && number < 85){
                 BloodPump.crash();
             } else if(number > 33 && number < 43){
                 Oxygenator.crash();
             }
             try{
                 Thread.sleep(5000);
             } catch(InterruptedException e){ }
         }
    }
}
