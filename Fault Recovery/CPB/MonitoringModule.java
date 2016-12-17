import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.rmi.Naming;

public class MonitoringModule extends UnicastRemoteObject implements MonitorI {

    private boolean[] iAmAlive = new boolean[] {false, false, false, false};
    private boolean didSwitchBloodPump = false;
    private boolean didSwitchOxygenator = false;

    public MonitoringModule() throws RemoteException { super(); }

    public void receiveHeartBeat(byte process) throws RemoteException {
		
        if(process == BLOODPUMP_MAIN){
            this.iAmAlive[0] = true;
            System.out.println("### HEARTBEAT RECEIVED BloodPump  ###");
        }

        if(process == BLOOBPUMP_BK){
            this.iAmAlive[1] = true;
            System.out.println("### HEARTBEAT RECEIVED BloodPumpR ###");
        }

        if(process == OXYGENATOR_MAIN){
            this.iAmAlive[2] = true;
            System.out.println("### HEARTBEAT RECEIVED Oxygenator  ###");
        }

        if(process == OXYGENATOR_BK){
            this.iAmAlive[3] = true;
            System.out.println("### HEARTBEAT RECEIVED OxygenatorR ###");
        }
		
    }

    public void checkHeartBeats() throws RemoteException{
        while(true){
			System.out.println("");
            if(this.iAmAlive[0]){
                System.out.println("### MAIN BLOODPUMP BEATING ###");
                this.iAmAlive[0] = false;
				this.didSwitchBloodPump = false;
            } else if(!this.iAmAlive[0] && this.iAmAlive[1]) {
				this.iAmAlive[1] = false;
				
				if (!this.didSwitchBloodPump) {
					this.didSwitchBloodPump = true;
                    System.out.println("### MAIN BLOODPUMP DOWN, SWITCHING TO BACKUP ###");
				}
            } else if (!this.iAmAlive[0] && !this.iAmAlive[1]) {
                System.out.println("### BLOODPUMP AND BACKUP ARE DOWN ###");                	
            }
			
            if(this.iAmAlive[2]){
                System.out.println("### MAIN OXYGENATOR BEATING ###");
                this.iAmAlive[2] = false;
				this.didSwitchOxygenator = false;
            } else if(!this.iAmAlive[2] && this.iAmAlive[3]) {
				this.iAmAlive[3] = false;
				
				if (!this.didSwitchOxygenator) {
					this.didSwitchOxygenator = true;
                    System.out.println("### MAIN OXYGENATOR DOWN, SWITCHING TO BACKUP ###");
				}
            } else if (!this.iAmAlive[2] && !this.iAmAlive[3]) {
                System.out.println("### OXYGENATOR AND BACKUP ARE DOWN ###");                	
            }
			
            try {
                Thread.sleep(3000);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main (String [] args){
        try {
            MonitorI monitoringModule = new MonitoringModule();
            Naming.rebind("rmi://localhost:5000/monitor", monitoringModule);
			 System.out.println("Monitoring Module is RUNNING!");
            monitoringModule.checkHeartBeats();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
