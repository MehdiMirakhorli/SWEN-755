
public class DroneMoveModel {
	
	public int DRONE_W = 30;
	public int DRONE_H = 40;
	
	private int x = 0;
	private int y = 0;
	private int velX = 0;
	private int velY = 0;

	private int distanceTraveled = 0;
	private boolean robotMoving = true;
	private boolean interruptMove = false;
	private boolean interruptSenderThread = false;

	private int lastX;
	private int lastY;

	boolean connectionLost = false;

	ZoneModel zm;

	DroneMoveModel(ZoneModel zm) {

		x = zm.frameWidth / 2;
		y = zm.frameHeight / 2;
		this.zm = zm;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;

		if (this.zm.isDroneInZone)
			this.lastX = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;

		if (this.zm.isDroneInZone)
			this.lastY = y;
	}

	public int getVelX() {
		return velX;
	}

	public void setVelX() {
		this.velX = -velX;
	}
	
	public void setVelX(int val) {
		this.velX = val;
	}

	public int getVelY() {
		return velY;
	}

	public void setVelY() {
		this.velY = -velY;
	}
	
	public void setVelY(int val) {
		this.velY = val;
	}

	public int getDistanceTraveled() {
		return distanceTraveled;
	}

	public void setDistanceTraveled(int distanceTraveled) {
		this.distanceTraveled = distanceTraveled;
	}

	public boolean isRobotMoving() {
		return robotMoving;
	}

	public void setRobotMoving(boolean robotMoving) {
		this.robotMoving = robotMoving;
	}

	public boolean isInterruptMove() {
		return interruptMove;
	}

	public void setInterruptMove(boolean interruptMove) {
		this.interruptMove = interruptMove;
	}

	public boolean isInterruptSenderThread() {
		return interruptSenderThread;
	}

	public void setInterruptSenderThread(boolean interruptSenderThread) {
		this.interruptSenderThread = interruptSenderThread;
	}

	public void setLastLocation(int x, int y) {
		if (this.zm.checkDroneInZone(x, y)) {
			this.lastX = x;
			this.lastY = y;
			this.zm.setLocation(lastX, lastY);
			zm.setDroneInZone(true);

			if (this.connectionLost == true) {
				this.interruptMove = true;
			}
		} else {
			zm.setDroneInZone(false);
		}
	}

	public void returnDrone() {
		if (!zm.isDroneInZone && connectionLost) {
			this.setVelX();
			this.setVelY();
		} else if (zm.isDroneInZone && connectionLost) {
			this.interruptMove = true;
		}
	}

	public void setConnectionLost(boolean connectionLost) {
		this.connectionLost = connectionLost;
		returnDrone();
	}

}
