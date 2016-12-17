
public class ZoneModel {
	private int minX;
	private int maxX;
	private int minY;
	private int maxY;

	int frameWidth;
	int frameHeight;
	int zoneWidth;
	int zoneHeight;

	int zoneMinX;
	int zoneMaxX;
	int zoneMinY;
	int zoneMaxY;

	boolean stayInZone = true;
	boolean isDroneInZone = true;

	private int lastX;
	private int lastY;

	public ZoneModel(int frameWidth, int frameHeight) {

		this.frameWidth = frameWidth;
		this.frameHeight = frameHeight;

		this.zoneWidth = frameWidth * 3 / 5;
		this.zoneHeight = frameHeight * 3 / 5;

		setCoordinates();

	}

	public void switchBounds() {
		stayInZone = !stayInZone;
		setCoordinates();
	}

	public void switchBounds(boolean outBound) {
		stayInZone = outBound;
		setCoordinates();
	}

	public void setCoordinates() {
		if (stayInZone == true) {
			minX = frameWidth / 5;
			maxX = minX + zoneWidth;
			minY = frameHeight / 6;
			maxY = minY + zoneHeight;

			zoneMinX = minX;
			zoneMaxX = maxX;
			zoneMinY = minY;
			zoneMaxY = maxY;

		} else {
			minX = 0;
			maxX = frameWidth;
			minY = 0;
			maxY = frameHeight;
		}
	}

	public int getMinX() {
		return minX + 2;
	}

	public int getMaxX() {
		return maxX;
	}

	public int getMinY() {
		return minY + 2;
	}

	public int getMaxY() {
		return maxY;
	}

	public int getZoneMinX() {
		return zoneMinX;
	}

	public int getZoneMinY() {
		return zoneMinY;
	}

	public int getLastX() {
		return lastX;
	}

	public int getLastY() {
		return lastY;
	}

	public boolean checkDroneInZone(int x, int y) {
		boolean inZone = (x > zoneMinX && x + 30 < zoneMaxX && y > zoneMinY && y + 40 < zoneMaxY);
		this.isDroneInZone = inZone;
		return (inZone);
	}

	public void setDroneInZone(boolean isInZone) {
		isDroneInZone = isInZone;
	}

	public void setLocation(int x, int y) {
		this.lastX = x;
		this.lastY = y;
	}

}
