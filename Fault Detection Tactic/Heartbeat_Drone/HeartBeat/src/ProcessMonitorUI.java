import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JFrame;

class ProcessMonitorComponent extends JComponent{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8982402238752787014L;
	int runningProcesses = 0;
	String [] processes = {"Backup" , "Main"};
	
	public int getRunningProcesses() {
		return runningProcesses;
	}

	public void setRunningProcesses(int runningProcesses) {
		this.runningProcesses = runningProcesses;
	}

	public void paintComponent(Graphics g) {
		
		for(int i = 0; i < 2; i++) {
			g.setColor(Color.BLACK);
			g.drawString(processes[i], 90, 60 + (i * 40));
			g.setColor(Color.RED);
			g.fillRect(140, 40 + (i * 45), 70, 20);
		}
		
		for(int i = 0; i < getRunningProcesses(); i++) {
			g.setColor(Color.BLACK);
			g.drawString(processes[i], 90, 60 + (i * 40));
			g.setColor(Color.GREEN);
			g.fillRect(140, 40 + (i * 45), 70, 20);
		}
		
	}
}


public class ProcessMonitorUI extends JFrame{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -3525747653312930289L;
	
	public ProcessMonitorComponent pmc;
	
	public ProcessMonitorUI() {
		super("Drone Process Monitor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300,200);
		//setLocationRelativeTo(null);
		ProcessMonitorComponent pmc = new ProcessMonitorComponent();
		this.pmc = pmc;
		add(pmc);
	}
	

}
