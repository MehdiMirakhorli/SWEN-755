
interface ObstacleDetectImpl {
	public boolean detectedObstacle();

	public boolean isRunning();

	public void print();
}

public class ObstacleDetector extends Thread implements ObstacleDetectImpl {

	Obstacle obstacle;
	DroneMoveModel dmm;
	SafeArea sa;
	boolean isRunning = true;
	ObstacleDetectModel obsModel;
	String processName;

	int processDeath;

	public ObstacleDetector(Obstacle obstacle, DroneMoveModel dmm, String processName) {
		this.obstacle = obstacle;
		this.sa = obstacle.sa;
		this.dmm = dmm;
		obsModel = new ObstacleDetectModel();
		this.processName = processName;
	}

	public boolean detectedObstacle() {

		obsModel.setDrMaxX(dmm.getX() + dmm.DRONE_W);
		obsModel.setDrMaxY(dmm.getY() + dmm.DRONE_H);
		obsModel.setDrMinX(dmm.getX());
		obsModel.setDrMinY(dmm.getY());

		obsModel.setSaMaxX(sa.maxX);
		obsModel.setSaMaxY(sa.maxY);
		obsModel.setSaMinX(sa.minX);
		obsModel.setSaMinY(sa.minY);

		boolean isApproaching = false;
		if (ObstacleDetectorCalculator.rectOverlap(obsModel.getSaMinX(), obsModel.getSaMinY(), obsModel.getSaMaxX(),
				obsModel.getSaMaxY(), obsModel.getDrMinX(), obsModel.getDrMinY(), obsModel.getDrMaxX(),
				obsModel.getDrMaxY())) {
			isApproaching = true;
		}
		return isApproaching;
	}

	@Override
	public boolean isRunning() {
		return this.isRunning;
	}

	public void run() {
		while (this.isRunning) {
			detectedObstacle();
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void print() {
		// TODO Auto-generated method stub
		System.out.println("Current running process is " + processName);
	}

}

class ObstacleDetectorCalculator {

	public static boolean valueInRange(int value, int min, int max) {
		return (value >= min) && (value <= max);
	}

	public static boolean rectOverlap(int saMinX, int saMinY, int saMaxX, int saMaxY, int drMinX, int drMinY,
			int drMaxX, int drMaxY) {

		boolean xOverlap = valueInRange(saMinX, drMinX, drMaxX) || valueInRange(drMinX, saMinX, saMaxX);

		boolean yOverlap = valueInRange(saMinY, drMinY, drMaxY) || valueInRange(drMinY, saMinY, saMaxY);

		return xOverlap && yOverlap;
	}

}

class ObstacleDetectModel {

	int saMinX, saMinY, saMaxX, saMaxY, drMinX, drMinY, drMaxX, drMaxY;

	ObstacleDetectModel() {

	}

	ObstacleDetectModel(Obstacle obstacle, DroneMoveModel dmm) {
		super();
		setDrMaxX(dmm.getX() + dmm.DRONE_W);
		setDrMaxY(dmm.getY() + dmm.DRONE_H);
		setDrMinX(dmm.getX());
		setDrMinY(dmm.getY());

		setSaMaxX(obstacle.sa.maxX);
		setSaMaxY(obstacle.sa.maxY);
		setSaMinX(obstacle.sa.minX);
		setSaMinY(obstacle.sa.minY);
	}

	public int getSaMinX() {
		return saMinX;
	}

	public void setSaMinX(int saMinX) {
		this.saMinX = saMinX;
	}

	public int getSaMinY() {
		return saMinY;
	}

	public void setSaMinY(int saMinY) {
		this.saMinY = saMinY;
	}

	public int getSaMaxX() {
		return saMaxX;
	}

	public void setSaMaxX(int saMaxX) {
		this.saMaxX = saMaxX;
	}

	public int getSaMaxY() {
		return saMaxY;
	}

	public void setSaMaxY(int saMaxY) {
		this.saMaxY = saMaxY;
	}

	public int getDrMinX() {
		return drMinX;
	}

	public void setDrMinX(int drMinX) {
		this.drMinX = drMinX;
	}

	public int getDrMinY() {
		return drMinY;
	}

	public void setDrMinY(int drMinY) {
		this.drMinY = drMinY;
	}

	public int getDrMaxX() {
		return drMaxX;
	}

	public void setDrMaxX(int drMaxX) {
		this.drMaxX = drMaxX;
	}

	public int getDrMaxY() {
		return drMaxY;
	}

	public void setDrMaxY(int drMaxY) {
		this.drMaxY = drMaxY;
	}

	public String serializeData() {

		// saMinX - saMinY - saMaxX - saMaxY - drMinX - drMinY - drMaxX - drMaxY

		String message = this.saMinX + "," + this.saMinY + "," + this.saMaxX + "," + this.saMaxY + "," + this.drMinX
				+ "," + this.drMinY + "," + this.drMaxX + "," + this.drMaxY;

		return message;
	}

	public void deSerializeData(String data) {
		// saMinX - saMinY - saMaxX - saMaxY - drMinX - drMinY - drMaxX - drMaxY

		if (data.length() > 0) {
			String[] values = data.split(",", -1);
			if (values.length == 8) {
				setSaMinX(Integer.parseInt(values[0]));
				setSaMinY(Integer.parseInt(values[1]));
				setSaMaxX(Integer.parseInt(values[2]));
				setSaMaxY(Integer.parseInt(values[3]));
				setDrMinX(Integer.parseInt(values[4]));
				setDrMinY(Integer.parseInt(values[5]));
				setDrMaxX(Integer.parseInt(values[6]));
				setDrMaxY(Integer.parseInt(values[7]));
			}
		}
	}
}
