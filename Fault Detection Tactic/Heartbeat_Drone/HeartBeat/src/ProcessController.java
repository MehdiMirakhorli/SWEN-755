import java.util.ArrayList;
import java.util.List;

public class ProcessController extends Thread {

	List<ObstacleDetectImpl> processes;
	ObstacleDetectImpl runningProcess;
	DroneMoveModel dmm;

	ProcessController(MainProcessManipulator mainProcess, ObstacleDetector secondProcess, DroneMoveModel dmm) {

		processes = new ArrayList<ObstacleDetectImpl>();
		
		processes.add(0, mainProcess);
		processes.add(1, secondProcess);
		

		this.dmm = dmm;

	}

	public void run() {

		while (processes.size() > 0) {
			runningProcess = null;
			for (int i = 0; i < processes.size(); i++) {
				if (processes.get(i).isRunning()) {
					runningProcess = processes.get(i);
					break;
				}
				else
					processes.remove(i);
			}

			if (runningProcess != null) {
				if (runningProcess.detectedObstacle()) {

					System.err.println("Approaching obstales");
					runningProcess.print();
					this.dmm.setRobotMoving(false);
				}
			}

			try {
				sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public int getRunningProcesses() {

		int runningProcessCount = 0;

		for (int i = 0; i < processes.size(); i++) {
			if (processes.get(i).isRunning())
				runningProcessCount++;
		}
		return runningProcessCount;
	}

}
