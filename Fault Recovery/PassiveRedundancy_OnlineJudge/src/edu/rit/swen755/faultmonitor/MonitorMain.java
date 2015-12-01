package edu.rit.swen755.faultmonitor;

/**
 * Main class for the Monitoring process.
 *
 * @author Joanna
 */
public class MonitorMain {
    
    public static ConsoleFrame frame;
    
    public static void main(String[] args) throws Exception {
        frame = new ConsoleFrame();
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                frame.setVisible(true);
            }
        });
        
        FaultMonitor monitor = new FaultMonitor();
        monitor.init();
        frame.getTextAreaMonitor().setText("Monitor initialized\n");
        
    }
    
}
