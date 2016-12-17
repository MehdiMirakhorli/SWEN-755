public class CPB{
    public static void main(String [] args){
        MonitoringModule monitoringModule = new MonitoringModule();
        BloodPump bloodPump = new BloodPump();
        Oxygenator oxygenator = new Oxygenator();

        bloodPump.addObserver(monitoringModule);
        oxygenator.addObserver(monitoringModule);

        Thread bloodPumpThread = new Thread(bloodPump);
        Thread oxygenatorThread = new Thread(oxygenator);

        bloodPumpThread.start();
        oxygenatorThread.start();
        try{
            Thread.sleep(500);
        } catch(InterruptedException e){ }

        monitoringModule.start();

        FailureGenerator failureGenerator = new FailureGenerator();
        failureGenerator.generateFailure();
    }
}
