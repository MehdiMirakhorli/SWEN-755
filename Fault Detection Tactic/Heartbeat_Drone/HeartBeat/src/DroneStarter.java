/**
 * Drone starter
 * 
 * @author rebaz
 *
 */
public class DroneStarter {

	static int HEARTBEAT_PORT = 7475;
	static int PROCESS_PORT = 7576;

	public static void main(String[] args) throws Exception {

		SpaceUI frame = new SpaceUI();
		ZoneModel zm = new ZoneModel(frame.getWidth(), frame.getHeight());

		final DroneMoveModel dmm = new DroneMoveModel(zm);
		Drone tr = new Drone(dmm);

		frame.addKeyListener(tr.new RobotKeyHandler());
		frame.add(tr);

		frame.setVisible(true);

		ObstacleDetector obstacleDetector = new ObstacleDetector(tr.obstacle, dmm, "Second Process");
		

		MainProcessManipulator mainProcess = new MainProcessManipulator("localhost", PROCESS_PORT,
				2000, obstacleDetector, dmm, "Main Process");
		
		Thread mainProcessThread = new Thread(mainProcess);
		mainProcessThread.start();
		
		
		ObstacleDetector secondProcess = obstacleDetector;
		secondProcess.start();

		ProcessController pc = new ProcessController(mainProcess, secondProcess, dmm);
		pc.start();

		HeartBeatSender heartBeatSender = new HeartBeatSender("localhost", HEARTBEAT_PORT, 0, pc);
		Thread hbSenderThread = new Thread(heartBeatSender);
		hbSenderThread.start();

	}

}
